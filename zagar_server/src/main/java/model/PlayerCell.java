package model;

import static model.GameConstants.SPEED_SCALE_FACTOR;
import static model.GameConstants.SPLIT_SPEED_SCALE_FACTOR;

/**
 * @author apomosov
 */
public class PlayerCell extends Cell {
  private final int id;
  private int kind = 0; /**0-usual 1-ejected 2-splitted*/
  private int directionPointX, directionPointY;

  public PlayerCell(int id, int x, int y) {
    super(x, y, GameConstants.DEFAULT_PLAYER_CELL_MASS);
    this.id = id;
  }

  public PlayerCell(int id, int x, int y, int mass){
    super(x, y, mass);
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public int getDirectionPointX() {
    return directionPointX;
  }

  public int getDirectionPointY() {
    return directionPointY;
  }

  public int getKind() {
    return kind;
  }

  public void setKind(int kind) {
    this.kind = kind;
  }

  public void setDirectionPoint(int directionPointX, int directionPointY) {
    this.directionPointX = directionPointX;
    this.directionPointY = directionPointY;
  }

  public void calculateCoords(){
    float dx = directionPointX - x;
    float dy = directionPointY - y;
    if (dx == 0 && dy == 0){
      return;
    }
    float angle = (dy != 0)? (float) Math.atan(dx / dy) : (float)Math.PI / 2;

    if (dx > 0)
      x += (((kind == 2) ? SPLIT_SPEED_SCALE_FACTOR : SPEED_SCALE_FACTOR) / mass) * Math.abs(Math.sin(angle));
    else
      x -= (((kind == 2) ? SPLIT_SPEED_SCALE_FACTOR : SPEED_SCALE_FACTOR) / mass) * Math.abs(Math.sin(angle));
    if (dy > 0)
      y += (((kind == 2) ? SPLIT_SPEED_SCALE_FACTOR : SPEED_SCALE_FACTOR) / mass) * Math.abs(Math.cos(angle));
    else
      y -= (((kind == 2) ? SPLIT_SPEED_SCALE_FACTOR : SPEED_SCALE_FACTOR) / mass) * Math.abs(Math.cos(angle));

    x = checkCoord(x);
    y = checkCoord(y);

    if(kind == 1 && ((Math.abs(x - directionPointX) < SPEED_SCALE_FACTOR / mass && Math.abs(y - directionPointY) < SPEED_SCALE_FACTOR / mass)
            || x == GameConstants.FIELD_WIDTH || y == GameConstants.FIELD_HEIGHT || x == 0 || y == 0)){
      directionPointX = x;
      directionPointY = y;
    }

    if (kind == 2 && Math.abs(x - directionPointX) < SPLIT_SPEED_SCALE_FACTOR/mass
            && Math.abs(y - directionPointY) < SPLIT_SPEED_SCALE_FACTOR/mass){
      kind = 0;
    }
  }

  public int calculateEjectSplitX(float pointX, float pointY, boolean isEject){
    float dx = pointX - x;
    float dy = pointY - y;
    float angle = (dy != 0)? (float) Math.atan(dx / dy) : (float)Math.PI / 2;
    int directionX = x;

    if (dx > 0)
      directionX += (SPEED_SCALE_FACTOR * (isEject ? GameConstants.EJECT_DISTANCE_SCALE : GameConstants.SPLIT_DISTANCE_SCALE) / mass) * Math.abs(Math.sin(angle));
    else
      directionX -= (SPEED_SCALE_FACTOR * (isEject ? GameConstants.EJECT_DISTANCE_SCALE : GameConstants.SPLIT_DISTANCE_SCALE) / mass) * Math.abs(Math.sin(angle));

    return directionX;
  }

  public int calculateEjectSplitY(float pointX, float pointY, boolean isEject){
    float dx = pointX - x;
    float dy = pointY - y;
    float angle = (dy != 0)? (float) Math.atan(dx / dy) : (float)Math.PI / 2;
    int directionY = y;

    if (dy > 0)
      directionY += (SPEED_SCALE_FACTOR * (isEject ? GameConstants.EJECT_DISTANCE_SCALE : GameConstants.SPLIT_DISTANCE_SCALE) / mass) * Math.abs(Math.cos(angle));
    else
      directionY -= (SPEED_SCALE_FACTOR * (isEject ? GameConstants.EJECT_DISTANCE_SCALE : GameConstants.SPLIT_DISTANCE_SCALE) / mass) * Math.abs(Math.cos(angle));
    return directionY;
  }

  private int checkCoord(int coord){
    int checkedCoord;
    if (coord > GameConstants.FIELD_WIDTH) {
      checkedCoord = GameConstants.FIELD_WIDTH;
    }
    else if ( coord < 0 ){
      checkedCoord = 0;
    }
    else{
      checkedCoord = coord;
    }
    return checkedCoord;
  }
}
