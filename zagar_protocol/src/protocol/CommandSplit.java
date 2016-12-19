package protocol;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandSplit extends Command {
  @NotNull
  public static final String NAME = "split";

  private final float mouseX;
  private final float mouseY;

  public CommandSplit(float mouseX, float mouseY) {
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
