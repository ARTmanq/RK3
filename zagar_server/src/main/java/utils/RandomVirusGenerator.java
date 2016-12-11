package utils;

import model.Field;
import model.GameConstants;
import model.Virus;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author apomosov
 */
public class RandomVirusGenerator implements VirusGenerator {
  private Field field;
  private final int numberOfViruses;


  public RandomVirusGenerator(int numberOfViruses) {
    this.numberOfViruses = numberOfViruses;
  }

  @Override
  public void setField(Field field){
    this.field = field;
  }

  @Override
  public void generate() {
    Random random = new Random();
    int virusRadius = (int) Math.sqrt(GameConstants.VIRUS_MASS / Math.PI);
    ArrayList<Virus> viruses = field.getViruses();
    for (int i = 0; i < numberOfViruses; i++) {
      viruses.add(new Virus(
          virusRadius + random.nextInt(field.getWidth() - 2 * virusRadius),
          virusRadius + random.nextInt(field.getHeight() - 2 * virusRadius)
      ));
    }
  }
}
