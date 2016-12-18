package protocol;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandEjectMass extends Command {
  @NotNull
  public static final String NAME = "eject";

  private final float mouseX;
  private final float mouseY;

  public CommandEjectMass(float mouseX, float mouseY) {
    super(NAME);
    this.mouseX = mouseX;
    this.mouseY = mouseY;
  }

  public float getMouseX() {
    return mouseX;
  }

  public float getMouseY() {
    return mouseY;
  }
}
