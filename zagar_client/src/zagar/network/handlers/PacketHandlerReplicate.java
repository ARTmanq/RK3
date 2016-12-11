package zagar.network.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.CommandReplicate;
import zagar.Game;
import zagar.util.JSONDeserializationException;
import zagar.util.JSONHelper;
import zagar.view.Cell;
import zagar.view.Food;

public class PacketHandlerReplicate {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketHandlerReplicate.class);

  public PacketHandlerReplicate(@NotNull String json) {
    CommandReplicate commandReplicate;
    Game.player.clear();
    try {
      commandReplicate = JSONHelper.fromJSON(json, CommandReplicate.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    Cell[] gameCells = new Cell[commandReplicate.getCells().length];
    for (int i = 0; i < commandReplicate.getCells().length; i++) {
      protocol.model.Cell c = commandReplicate.getCells()[i];
      gameCells[i] = new Cell(c.getX(), c.getY(), c.getSize(), c.getCellId(), c.isVirus());
      if (c.getPlayerId() == Game.playerId){
        Game.player.add(gameCells[i]);
      }
    }
    Food[] gameFood = new Food[commandReplicate.getFood().length];
    for (int i = 0; i < commandReplicate.getFood().length; i++) {
      protocol.model.Food f = commandReplicate.getFood()[i];
      gameFood[i] = new Food(f.getX(), f.getY());
    }

    Game.cells = gameCells;
    Game.food = gameFood;
  }
}
