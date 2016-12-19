package utils;

import model.PlayerCell;

import java.util.Comparator;

/**
 * Created by Artem on 12/19/16.
 */
public class PlayerCellsComparator implements Comparator<PlayerCell> {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
               being compared by this comparator.
     */
    @Override
    public int compare(PlayerCell o1, PlayerCell o2) {
        return (int)(Math.sqrt(o1.getDirectionPointX() * o1.getDirectionPointX() +
                o1.getDirectionPointY() * o1.getDirectionPointY()) -
                Math.sqrt(o2.getDirectionPointX() * o2.getDirectionPointX() +
                o2.getDirectionPointY() * o2.getDirectionPointY()));
    }
}
