package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
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
    }
}
