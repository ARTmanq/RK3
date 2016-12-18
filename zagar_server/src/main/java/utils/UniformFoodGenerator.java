package utils;

import model.Field;
import model.Food;
import model.GameConstants;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.Random;

import static model.GameConstants.FOOD_COUNT;

/**
 * @author apomosov
 */
public class UniformFoodGenerator implements FoodGenerator {
  private Field field;

  @Override
  public void setField(Field field){
    this.field = field;
  }

  @Override
  public void generate() {
    try {
      while (field.getFoods().size() < FOOD_COUNT) {
        Random random = new Random();
        int foodRadius = (int) Math.sqrt(GameConstants.FOOD_MASS / Math.PI);
        ConcurrentHashSet<Food> food = field.getFoods();
          food.add(new Food(
                  foodRadius + random.nextInt(field.getWidth() - 2 * foodRadius),
                  foodRadius + random.nextInt(field.getHeight() - 2 * foodRadius)
          ));
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }
}
