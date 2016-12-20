package model;

/**
 * @author apomosov
 */
public interface GameConstants {
  /**FIELD SIZE*/
  int FIELD_WIDTH = 10000;
  int FIELD_HEIGHT = 10000;

  /**MASS*/
  int FOOD_MASS = 20;
  int EJECTED_MASS = 40;
  int DEFAULT_PLAYER_CELL_MASS = 40;
  int VIRUS_MASS = 80;

  /**NUMBER OF OBJECTS*/
  int MAX_PLAYERS_IN_SESSION = 10;
  int NUMBER_OF_VIRUSES = 100;
  int FOOD_COUNT = 1000;
  float PORTION_OF_FOODMASS_EATEN = 0.05f;
  //float PORTION_OF_FOODMASS_EATEN = 1.2f;

  /**SPEED*/
  int SPEED_SCALE_FACTOR = 2000;
  int SPLIT_SPEED_SCALE_FACTOR = 3000;

  /**EJECT SPLIT DISTANCE*/
  float EJECT_DISTANCE_SCALE = 0.5f;
  float  SPLIT_DISTANCE_SCALE = 0.5f;
}
