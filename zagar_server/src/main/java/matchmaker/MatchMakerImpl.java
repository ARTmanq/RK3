package matchmaker;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class MatchMakerImpl implements MatchMaker {
  @NotNull
  private final Logger log = LogManager.getLogger(MatchMakerImpl.class);
  @NotNull
  private final List<GameSession> activeGameSessions = new ArrayList<>();

  /**
   * Creates new GameSession for single player
   *
   * @param player single player
   */
  @Override
  public void joinGame(@NotNull Player player) {
    GameSession newGameSession = createNewGame();
    activeGameSessions.add(newGameSession);
    newGameSession.join(player);
    if (log.isInfoEnabled()) {
      log.info(player + " joined " + newGameSession);
    }
  }

  @Override
  public Player getPlayer(String name){
    for (GameSession session : activeGameSessions){
      for (Player player : session.getPlayers()){
        if (player.getName().equals(name)) {
          return player;
        }
      }
    }
    return null;
  }

  @Override
  public GameSession getGameSession(String name){
    for (GameSession session : activeGameSessions){
      for (Player player : session.getPlayers()){
        if (player.getName().equals(name)) {
          return session;
        }
      }
    }
    return null;
  }

  @NotNull
  public List<GameSession> getActiveGameSessions() {
    return new ArrayList<>(activeGameSessions);
  }

  /**
   * @return new GameSession
   */
  private
  @NotNull
  GameSession createNewGame() {
    Field field = new Field();
    PlayerPlacer playerPlacer = new RandomPlayerPlacer();
    VirusGenerator virusGenerator = new RandomVirusGenerator(GameConstants.NUMBER_OF_VIRUSES);
    //Ticker ticker = ApplicationContext.instance().get(Ticker.class);
    UniformFoodGenerator foodGenerator = new UniformFoodGenerator(GameConstants.FOOD_PER_SECOND_GENERATION, GameConstants.MAX_FOOD_ON_FIELD);
    //ticker.registerTickable(foodGenerator);

    return new GameSessionImpl(foodGenerator, playerPlacer, virusGenerator);
  }
}
