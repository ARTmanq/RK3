package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.*;
import protocol.CommandMove;
import utils.PlayerCellsComparator;

import java.util.List;

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
        List<PlayerCell> sortedPlayerCells = player.getCells();
        sortedPlayerCells.sort(new PlayerCellsComparator());
        /*System.out.println("START SORT");
        for(PlayerCell i : sortedPlayerCells) {
            System.out.println(Math.sqrt(i.getDirectionPointX() * i.getDirectionPointX() +
                    i.getDirectionPointY() * i.getDirectionPointY()));
        }*/
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
            //sortedPlayerCells.get(0).setX((int)(sortedPlayerCells.get(0).getX() + ((SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.sin(angle)))));
        else
            avgX -= (SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.sin(angle));
            //sortedPlayerCells.get(0).setX((int)(sortedPlayerCells.get(0).getX() - ((SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.sin(angle)))));
        if (dy > 0)
            avgY += (SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.cos(angle));
            //sortedPlayerCells.get(0).setY((int)(sortedPlayerCells.get(0).getY() + ((SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.cos(angle)))));
        else
            avgY -= (SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.cos(angle));
            //sortedPlayerCells.get(0).setY((int)(sortedPlayerCells.get(0).getY() - ((SPEED_SCALE_FACTOR / playerMass) * Math.abs(Math.cos(angle)))));
        sortedPlayerCells.get(0).setX(checkCoord(sortedPlayerCells.get(0).getX()));
        sortedPlayerCells.get(0).setY(checkCoord(sortedPlayerCells.get(0).getY()));
        System.out.println("start");
        for(int i = 0; i < sortedPlayerCells.size(); ++i) {
            System.out.println("iteration" + i);
            if (sortedPlayerCells.get(i).getKind() == 0) {
                sortedPlayerCells.get(i).setDirectionPoint(avgX, avgY/*sortedPlayerCells.get(0).getX(), sortedPlayerCells.get(0).getY()*/);
            }
            sortedPlayerCells.get(i).calculateCoords();
            if(i > 0) {
                for(int j = 0; j < i; ++j) {
                    System.out.println("Destination = "+countDestination(sortedPlayerCells.get(i), sortedPlayerCells.get(j)));
                    System.out.println("Mass1 = "+sortedPlayerCells.get(j).getMass()+"  Mass2"+sortedPlayerCells.get(i).getMass());
                    if(countDestination(sortedPlayerCells.get(i), sortedPlayerCells.get(j)) + 1
                            < sortedPlayerCells.get(j).getMass() + sortedPlayerCells.get(i).getMass()) {
                        checkPlayerCellsCollision(sortedPlayerCells.get(j), sortedPlayerCells.get(i));
                    }
                    System.out.println("Destination = "+countDestination(sortedPlayerCells.get(i), sortedPlayerCells.get(j)));
                    System.out.println("Mass1 = "+sortedPlayerCells.get(j).getMass()+"  Mass2"+sortedPlayerCells.get(i).getMass());
                }
            }
        }
    }

    private int countDestination(PlayerCell cell1, PlayerCell cell2) {
        int dx = cell1.getX() - cell2.getX();
        int dy = cell1.getY() - cell2.getY();
        return (int)Math.sqrt(dx * dx + dy * dy);
    }

    private void checkPlayerCellsCollision(PlayerCell playerCell1, PlayerCell playerCell2) {
        float dx = playerCell1.getX() - playerCell2.getX();
        float dy = playerCell1.getY() - playerCell2.getY();
        float angle = (dy != 0)? (float) Math.atan(dx / dy) : (float)Math.PI / 2;
        int dist = countDestination(playerCell1, playerCell2);

        if (dx > 0)
            playerCell2.setX((int)(playerCell2.getX() + (playerCell1.getMass() + playerCell2.getMass() - dist) * Math.abs(Math.sin(angle))));
        else
            playerCell2.setX((int)(playerCell2.getX() - (playerCell1.getMass() + playerCell2.getMass() - dist) * Math.abs(Math.sin(angle))));
            //avgX -= (playerCell1.getMass() + playerCell2.getMass()) * Math.abs(Math.sin(angle));
        if (dy > 0)
            playerCell2.setY((int)(playerCell2.getY() + (playerCell1.getMass() + playerCell2.getMass() - dist) * Math.abs(Math.cos(angle))));
            //avgY += (playerCell1.getMass() + playerCell2.getMass()) * Math.abs(Math.cos(angle));
        else
            playerCell2.setY((int)(playerCell2.getY() + (playerCell1.getMass() + playerCell2.getMass() - dist) * Math.abs(Math.cos(angle))));
            //avgY -= (playerCell1.getMass() + playerCell2.getMass()) * Math.abs(Math.cos(angle));
        playerCell2.setX(checkCoord(playerCell2.getX()));
        playerCell2.setY(checkCoord(playerCell2.getY()));
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
