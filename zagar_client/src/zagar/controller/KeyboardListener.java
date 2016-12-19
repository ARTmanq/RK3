package zagar.controller;

import org.jetbrains.annotations.NotNull;
import zagar.Game;
import zagar.network.packets.PacketEjectMass;
import zagar.network.packets.PacketSplit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class KeyboardListener implements KeyListener {
  private boolean keyWPressed;
  private boolean keySPACEPressed;

  @Override
  public void keyPressed(@NotNull KeyEvent e) {
    try {
      if (Game.socket != null && Game.socket.session != null) {
        if (Game.socket.session.isOpen()) {
          if (e.getKeyCode() == KeyEvent.VK_SPACE && !keySPACEPressed) {
            keySPACEPressed = true;
            new PacketSplit().write();
          }
          if (e.getKeyCode() == KeyEvent.VK_W && !keyWPressed) {
            keyWPressed = true;
            new PacketEjectMass().write();
          }
            /*if (e.getKeyCode() == KeyEvent.VK_T) {
              Game.rapidEject = true;
            }*/
        }
      }
    } catch (IOException ioEx) {
      ioEx.printStackTrace();
    }
  }

  @Override
  public void keyReleased(@NotNull KeyEvent e) {
    /*if (Game.socket != null && Game.socket.session != null) {
      if (Game.socket.session.isOpen()) {
        if (e.getKeyCode() == KeyEvent.VK_T) {
          //Game.rapidEject = false;
        }
      }
    }*/
    if (Game.socket != null && Game.socket.session != null) {
      if (Game.socket.session.isOpen()) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          keySPACEPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
          keyWPressed = false;
        }
        /*if (e.getKeyCode() == KeyEvent.VK_T) {
          Game.rapidEject = true;
        }*/
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

}
