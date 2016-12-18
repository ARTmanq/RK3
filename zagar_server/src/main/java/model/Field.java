package model;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public class Field {
  private final int width;
  private final int height;
  @NotNull
  private final ConcurrentHashSet<Virus> viruses = new ConcurrentHashSet<>();
  @NotNull
  private final ConcurrentHashSet<Food> foods = new ConcurrentHashSet<>();
  public Field() {
    this.width = GameConstants.FIELD_WIDTH;
    this.height = GameConstants.FIELD_HEIGHT;
  }

  @NotNull
  public ConcurrentHashSet<Virus> getViruses() {
    return viruses;
  }

  @NotNull
  public ConcurrentHashSet<Food> getFoods() {
    return foods;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
