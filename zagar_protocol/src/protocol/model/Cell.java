package protocol.model;

/**
 * @author apomosov
 */
public final class Cell {
  private final int cellId;
  private final int playerId;
  private final boolean isVirus;
  private final float size;
  private int x;
  private int y;
  private final String name;
  private final boolean ejectedMass;

  public Cell(int cellId, int playerId, boolean isVirus, boolean ejectedMass, float size, int x, int y) {
    this.cellId = cellId;
    this.playerId = playerId;
    this.isVirus = isVirus;
    this.size = size;
    this.x = x;
    this.y = y;
    this.name = "";
    this.ejectedMass = ejectedMass;
  }

  public Cell(int cellId, int playerId, boolean isVirus, boolean ejectedMass, float size, int x, int y, String name) {
    this.cellId = cellId;
    this.playerId = playerId;
    this.isVirus = isVirus;
    this.size = size;
    this.x = x;
    this.y = y;
    this.name = name;
    this.ejectedMass = ejectedMass;
  }

  public int getPlayerId() {
    return playerId;
  }

  public boolean isVirus() {
    return isVirus;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getCellId() {
    return cellId;
  }

  public float getSize() {
    return size;
  }

  public boolean getEjectedMass() {return ejectedMass; }

  public String getName() {
    return name;
  }

}
