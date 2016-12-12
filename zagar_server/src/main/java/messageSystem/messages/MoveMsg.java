package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.*;
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
        GameSession gameSession = ApplicationContext.instance().get(MatchMaker.class).getGameSession(this.getFrom().getName());
        if (gameSession == null) {
            return;
        }

        Player player = null;
        for (Player player1 : gameSession.getPlayers()){
            if (player1.getName().equals(this.getFrom().getName())){
                player = player1;
                break;
            }
        }

        int new_x = checkCoord((int)commandMove.getDx());
        int new_y = checkCoord((int)commandMove.getDy());

        player.getCells().get(0).setX(new_x);
        player.getCells().get(0).setY(new_y);
        for (Food food : gameSession.getField().getFoods()){
            for (PlayerCell cell : player.getCells()){
                if (Math.abs(food.getX() - cell.getX()) < cell.getMass()
                        && Math.abs(food.getY() - cell.getY()) < cell.getMass()){
                    gameSession.getField().getFoods().remove(food);
                }
            }
        }
    }

    private int checkCoord(int coord){
        int checkedCoord;
        if (coord > GameConstants.FIELD_WIDTH) {
            checkedCoord = GameConstants.FIELD_WIDTH;
        }
        else if ( coord < 0 ){
            checkedCoord = 0;
        }
        else{
            checkedCoord = coord;
        }
        return checkedCoord;
    }
}
