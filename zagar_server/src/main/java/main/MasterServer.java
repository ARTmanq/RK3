package main;

import matchmaker.MatchMaker;
import messageSystem.MessageSystem;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;
import statistic.LeaderBoard;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by apomosov on 14.05.16.
 */
public class MasterServer {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  private void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");

    Properties prop = new Properties();
    InputStream input = null;
    try {

      input = new FileInputStream("config.properties");

      // load a properties file
      prop.load(input);

    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }


    try {
      ApplicationContext.instance().put(MatchMaker.class, Class.forName(prop.getProperty("matchMaker")).newInstance());
      ApplicationContext.instance().put(ClientConnections.class, new ClientConnections());
      ApplicationContext.instance().put(Replicator.class, Class.forName(prop.getProperty("replicator")).newInstance());
      ApplicationContext.instance().put(IDGenerator.class, new SequentialIDGenerator());
      ApplicationContext.instance().put(LeaderBoard.class, Class.forName(prop.getProperty("leaderBoard")).newInstance());
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      if (ApplicationContext.instance().get(MatchMaker.class) == null){
        log.error("Incorrect path to MatchMaker");
        return;
      }
      if (ApplicationContext.instance().get(Replicator.class) == null){
        log.error("Incorrect path to Replicator");
        return;
      }
      if (ApplicationContext.instance().get(LeaderBoard.class) == null){
        log.error("Incorrect path to LeaderBoard");
        return;
      }
    }


    MessageSystem messageSystem = new MessageSystem();
    ApplicationContext.instance().put(MessageSystem.class, messageSystem);



    String[] serviceNames;
    try {
      serviceNames = prop.getProperty("services").split(",");
      messageSystem.registerService(Class.forName(serviceNames[0]), (Service) Class.forName(serviceNames[0]).newInstance());
      messageSystem.registerService(Class.forName(serviceNames[1]), (Service) Class.forName(serviceNames[1]).getConstructor(Integer.class).newInstance(Integer.valueOf(prop.getProperty("accountServerPort"))));
      messageSystem.registerService(Class.forName(serviceNames[2]), (Service) Class.forName(serviceNames[2]).getConstructor(Integer.class).newInstance(Integer.valueOf(prop.getProperty("clientConnectionPort"))));
    } catch (Exception e) {
      switch(messageSystem.getServices().size()){
        case 0:
          log.error("Could not create Mechnics Service");
          return;
        case 1:
          log.error("Could not create AccountServer Service");
          return;
        case 2:
          log.error("Could not create ClientConnectionServer Service");
          return;
      }
    }

    messageSystem.getServices().forEach(Service::start);

    for (Service service : messageSystem.getServices()) {
      service.join();
    }
  }

  public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
    MasterServer server = new MasterServer();
    server.start();
  }
}
