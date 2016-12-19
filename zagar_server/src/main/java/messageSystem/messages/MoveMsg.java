package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.*;
import protocol.CommandMove;

import static model.GameConstants.SPEED_SCALE_FACTOR;

public class MoveMsg extends Message {

    private CommandMove commandMove;

    public MoveMsg(Address from, CommandMove commandMove) {
        super(from, ApplicationContext.instance().get(MessageSystem.class)
                .getService(Mechanics.class).getAddress());
        this.commandMove = commandMove;
    }

    @Override
    public void exec(Abonent abonent) {
        GameSession gameSession = super.getGameSession();
        if (gameSession == null) {
            return;
        }
        Player player = super.getPlayer();

        if (player != null) {
            calculateNewCoords(player);
            for (PlayerCell cell : player.getCells()) {
                eatFood(gameSession, cell);
                eatVirus(gameSession, cell);
                eatPlayer(gameSession, cell);
            }
        }
    }

    private void eatFood(GameSession gameSession, PlayerCell cell){
        for (Food food : gameSession.getField().getFoods()) {
            if (Math.abs(food.getX() - cell.getX()) < cell.getMass()
                    && Math.abs(food.getY() - cell.getY()) < cell.getMass()) {
                gameSession.getField().getFoods().remove(food);
                cell.setMass((int) (cell.getMass() + GameConstants.PORTION_OF_FOODMASS_EATEN * food.getMass()));
            }
        }
    }

    private void eatVirus(GameSession gameSession, PlayerCell cell){
        for (Virus virus : gameSession.getField().getViruses()){
            if (Math.abs(virus.getX() - cell.getX()) < cell.getMass() && Math.abs(virus.getY() - cell.getY()) < cell.getMass()){
                gameSession.getField().getViruses().remove(virus);
                GameSessionImpl.virusGenerator.generate();
            }
        }
    }

    private void eatPlayer(GameSession gameSession, PlayerCell cell){
        if (cell.getKind() == 0) {
            for (Player player : gameSession.getPlayers()) {
                if (!player.equals(getPlayer())) {
                    for (PlayerCell toeatCell : player.getCells()) {
                        if ((Math.abs(toeatCell.getX() - cell.getX()) < cell.getMass()) && (Math.abs(toeatCell.getY() - cell.getY()) < cell.getMass()) && (cell.getMass() > toeatCell.getMass())) {
                            cell.setMass((int) (cell.getMass() + GameConstants.PORTION_OF_FOODMASS_EATEN * toeatCell.getMass()));
                            player.removeCell(toeatCell);
                        }
                    }
                } else {
                    for (PlayerCell toeatCell : player.getCells()) {
                        if ((toeatCell.getKind() == 1) && (toeatCell.getDirectionPointX() == toeatCell.getX()) && (toeatCell.getDirectionPointY() == toeatCell.getY())) {
                            if ((Math.abs(toeatCell.getX() - cell.getX()) < cell.getMass()) && (Math.abs(toeatCell.getY() - cell.getY()) < cell.getMass())) {
                                cell.setMass((int) (cell.getMass() + GameConstants.PORTION_OF_FOODMASS_EATEN * toeatCell.getMass()));
                                player.removeCell(toeatCell);
                            }
                        }
                    }
                }
            }
        }
    }

    private void calculateNewCoords(Player player){
        int avgX = 0, avgY = 0, playerMass = 0, size = 0;
        for (PlayerCell cell : player.getCells()) {
            if (cell.getKind() != 1) {
                avgX += cell.getX();
                avgY += cell.getY();
                playerMass += cell.getMass();
                size++;
            }
        }
        avgX /= size;
        avgY /= size;

        float dx = commandMove.getDx();
        float dy = commandMove.getDy();
        float angle = (dy != 0)? (float) Math.atan(dx / dy) : (float)Math.PI / 2;

        if (dx > 0)
            avgX += (SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.sin(angle));
        else
            avgX -= (SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.sin(angle));
        if (dy > 0)
            avgY += (SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.cos(angle));
        else
            avgY -= (SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.cos(angle));

        for (PlayerCell cell : player.getCells()){
            if (cell.getKind() == 0) {
                cell.setDirectionPoint(avgX, avgY);
            }
            cell.calculateCoords();
        }
    }
}
