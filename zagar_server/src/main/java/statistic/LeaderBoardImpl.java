package statistic;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import utils.JSONHelper;
import utils.PlayerComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Artem on 12/18/16.
 */
public class LeaderBoardImpl implements LeaderBoard {
    private static final @NotNull Logger log = LogManager.getLogger(LeaderBoardImpl.class);

    public void getLeaders(int number) {
        for (Map.Entry<Player, Session> connection : ApplicationContext.instance()
                .get(ClientConnections.class).getConnections()) {
            try {
                List<Player> players = ApplicationContext.instance().get(MatchMaker.class)
                        .getGameSession(connection.getKey().getName()).getPlayers();
                ArrayList<Player> leaderBoard = new ArrayList<>(players);
                ArrayList<String> leaderBoardForJSON = new ArrayList<>();
                leaderBoard.sort(new PlayerComparator());
                leaderBoardForJSON.addAll(leaderBoard.stream().map(Player::getName)
                        .collect(Collectors.toList()));
                String headerJSON = "{" + "\"leaderBoard\":[";
                String bodyJSON = "";
                for(String i : leaderBoardForJSON) {
                    bodyJSON += "\"" + i + "\",";
                }
                bodyJSON += "]," + "\"command\":\"leader_board\"" + "}";
                String forJSON = headerJSON + bodyJSON;
                JSONHelper.fromJSON(forJSON, PacketLeaderBoard.class)
                        .write(connection.getValue());
            } catch (Exception e) {
                //log.error(e.getMessage());
            }
        }
    }
}
