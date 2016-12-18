package messageSystem;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;

/**
 * @author e.shubin
 */
public abstract class Message {
    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Abonent abonent);

    public GameSession getGameSession(){
        return ApplicationContext.instance().get(MatchMaker.class).getGameSession(this.getFrom().getName());
    }

    public Player getPlayer(){
        GameSession gameSession = getGameSession();
        if (gameSession == null) {
            return null;
        }

        Player player = null;
        for (Player player1 : gameSession.getPlayers()){
            if (player1.getName().equals(this.getFrom().getName())){
                player = player1;
                break;
            }
        }
        return player;
    }
}
