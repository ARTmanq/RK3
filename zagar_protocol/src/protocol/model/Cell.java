package protocol.model;

/**
 * @author apomosov
 */
public final class Cell {
  private final int cellId;
  private final int playerId;
  private final float size;
  private int x;
  private int y;
  private final String name;
  private final int kind;

  public Cell(int cellId, int playerId, int kind, float size, int x, int y) {
    this.cellId = cellId;
    this.playerId = playerId;
    this.kind = kind;
    this.size = size;
    this.x = x;
    this.y = y;
    this.name = "";
  }

  public Cell(int cellId, int playerId, int kind, float size, int x, int y, String name) {
    this.cellId = cellId;
    this.playerId = playerId;
    this.size = size;
    this.x = x;
    this.y = y;
    this.name = name;
    this.kind = kind;
  }

  public int getPlayerId() {
    return playerId;
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

  public int getKind() {return kind; }

  public String getName() {
    return name;
  }

}
