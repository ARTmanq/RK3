package utils;

import model.Player;

import java.util.Comparator;

/**
 * Created by Artem on 12/20/16.
 */
public class PlayerComparator implements Comparator<Player> {

    public int compare(Player o1, Player o2) {
        return o2.getTotalMass() - o1.getTotalMass();
    }
}
