package zagar.network.packets;

import protocol.CommandEjectMass;
import zagar.Game;
import zagar.util.JSONHelper;
import zagar.view.Cell;
import zagar.view.GameFrame;

import java.io.IOException;

public class PacketEjectMass {
  public PacketEjectMass() {
  }

  public void write() throws IOException {
    if (Game.player.size() != 0){
      int avgX = 0, avgY = 0;
      for (Cell c : Game.player) {
        if (c != null && c.kind != 1) {
          avgX += c.x;
          avgY += c.y;
        }
        float mouseX = (float)((GameFrame.mouseX - GameFrame.frame_size.width / 2) / Game.zoom + avgX);
        float mouseY = (float)((GameFrame.mouseY - GameFrame.frame_size.height / 2) / Game.zoom + avgY);
        String msg = JSONHelper.toJSON(new CommandEjectMass(mouseX, mouseY));
        Game.socket.session.getRemote().sendString(msg);
      }
    }
  }
}
