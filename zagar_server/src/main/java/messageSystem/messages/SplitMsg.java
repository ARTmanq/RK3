package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.*;
import protocol.CommandSplit;

/**
 * Created by Artem on 11/27/16.
 */
public class SplitMsg extends Message {
    private CommandSplit commandSplit;

    public SplitMsg(Address from, CommandSplit commandSplit) {
        super(from, ApplicationContext.instance().get(MessageSystem.class)
                .getService(Mechanics.class).getAddress());
        this.commandSplit = commandSplit;
    }

    @Override
    public void exec(Abonent abonent){
        GameSession gameSession = super.getGameSession();
        if (gameSession == null) {
            return;
        }
        Player player = super.getPlayer();
        int curSize = player.getCells().size();
        for (int i = 0; i < curSize; i++){
            if ((player.getCells().get(i).getMass() / 2) < GameConstants.DEFAULT_PLAYER_CELL_MASS){
                continue;
            }
            PlayerCell newCell = new PlayerCell(Cell.idGenerator.next(),
                    player.getCells().get(i).getX(), player.getCells().get(i).getY(),
                    player.getCells().get(i).getMass() / 2);

            newCell.setDirectionPoint(player.getCells().get(i).calculateEjectSplitX(commandSplit.getMouseX(), commandSplit.getMouseY(), false),
                    player.getCells().get(i).calculateEjectSplitY(commandSplit.getMouseX(), commandSplit.getMouseY(), false));

            newCell.setKind(2);
            player.getCells().add(newCell);
            player.getCells().get(i).setMass(player.getCells().get(i).getMass() / 2);
        }
    }
}