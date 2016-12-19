package utils;

import model.Field;
import model.GameConstants;
import model.Virus;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.Random;

public class RandomVirusGenerator implements VirusGenerator {
  private Field field;
  private final int numberOfViruses;


  public RandomVirusGenerator(int numberOfViruses) {
    this.numberOfViruses = numberOfViruses;
  }

  @Override
  public void setField(Field field) {
    this.field = field;
  }

  @Override
  public void generate() {
    try {
      while (field.getViruses().size() < GameConstants.NUMBER_OF_VIRUSES) {
        Random random = new Random();
        int foodRadius = (int) Math.sqrt(GameConstants.VIRUS_MASS / Math.PI);
        ConcurrentHashSet<Virus> virus = field.getViruses();
        virus.add(new Virus(
                foodRadius + random.nextInt(field.getWidth() - 2 * foodRadius),
                foodRadius + random.nextInt(field.getHeight() - 2 * foodRadius)
        ));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
