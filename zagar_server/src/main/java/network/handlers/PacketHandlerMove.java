package network.handlers;

import main.ApplicationContext;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.MoveMsg;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import utils.JSONDeserializationException;
import utils.JSONHelper;

public class PacketHandlerMove {
  public PacketHandlerMove(@NotNull Session session, @NotNull String json) {
    CommandMove commandMove;
    try {
      commandMove = JSONHelper.fromJSON(json, CommandMove.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    //TODO
    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new MoveMsg(new Address("client"), commandMove);
    messageSystem.sendMessage(message);
  }
}
