package utils;

import model.Field;
import ticker.Tickable;

/**
 * @author apomosov
 */
public interface FoodGenerator extends Tickable {
    void setField(Field field);
}
