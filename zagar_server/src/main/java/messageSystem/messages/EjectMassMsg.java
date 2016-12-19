package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.*;
import protocol.CommandEjectMass;

/**
 * Created by Artem on 11/27/16.
 */
public class EjectMassMsg extends Message{
    private CommandEjectMass commandEjectMass;

    public EjectMassMsg(Address from, CommandEjectMass commandEjectMass) {
        super(from, ApplicationContext.instance().get(MessageSystem.class)
                .getService(Mechanics.class).getAddress());
        this.commandEjectMass = commandEjectMass;
    }

    @Override
    public void exec(Abonent abonent) {
        GameSession gameSession = super.getGameSession();
        if (gameSession == null) {
            return;
        }
        Player player = super.getPlayer();

        int playerCellSize = player.getCells().size();
        for (int i = 0; i < playerCellSize; i++){
            if (player.getCells().get(i).getMass() - GameConstants.EJECTED_MASS < GameConstants.DEFAULT_PLAYER_CELL_MASS){
                continue;
            }
            PlayerCell newCell = new PlayerCell(Cell.idGenerator.next(),
                    player.getCells().get(i).getX(), player.getCells().get(i).getY(),
                    GameConstants.EJECTED_MASS);

            newCell.setDirectionPoint(player.getCells().get(i).calculateEjectSplitX(commandEjectMass.getMouseX(), commandEjectMass.getMouseY(), true),
                    player.getCells().get(i).calculateEjectSplitY(commandEjectMass.getMouseX(), commandEjectMass.getMouseY(), true));

            newCell.setKind(1);
            player.getCells().add(newCell);
            player.getCells().get(i).setMass(player.getCells().get(i).getMass() - GameConstants.EJECTED_MASS);
        }
    }
}
