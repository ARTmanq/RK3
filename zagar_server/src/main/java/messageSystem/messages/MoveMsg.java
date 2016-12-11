package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CommandMove;

/**
 * Created by Artem on 11/27/16.
 */
public class MoveMsg extends Message {

    private final static Logger log = LogManager.getLogger(MoveMsg.class);
    private CommandMove commandMove;

    public MoveMsg(Address from, CommandMove commandMove) {
        super(from, ApplicationContext.instance().get(MessageSystem.class)
                .getService(Mechanics.class).getAddress());
        this.commandMove = commandMove;
    }

    @Override
    public void exec(Abonent abonent) {
        log.info("CommandMove to (" + commandMove.getDx() + " ; " + commandMove.getDy() + ") was received");
        Player player = ApplicationContext.instance().get(MatchMaker.class).getPlayer(this.getFrom().getName());
        player.getCells().get(0).setX((int)commandMove.getDx());
        player.getCells().get(0).setY((int)commandMove.getDy());
    }
}
