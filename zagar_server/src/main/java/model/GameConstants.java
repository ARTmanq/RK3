package model;

/**
 * @author apomosov
 */
public interface GameConstants {
  /**FIELD SIZE*/
  int FIELD_WIDTH = 2000;
  int FIELD_HEIGHT = 2000;

  /**MASS*/
  int FOOD_MASS = 20;
  int EJECTED_MASS = 40;
  int DEFAULT_PLAYER_CELL_MASS = 20;
  int VIRUS_MASS = 50;

  /**NUMBER OF OBJECTS*/
  int MAX_PLAYERS_IN_SESSION = 10;
  int NUMBER_OF_VIRUSES = 10;
  int FOOD_COUNT = 100;
  //float PORTION_OF_FOODMASS_EATEN = 0.05f;
  float PORTION_OF_FOODMASS_EATEN = 0.2f;

  /**SPEED*/
  int SPEED_SCALE_FACTOR = 2000;
  int SPLIT_SPEED_SCALE_FACTOR = 3000;

  /**EJECT SPLIT DISTANCE*/
  int EJECT_DISTANCE_SCALE = 60;
  int SPLIT_DISTANCE_SCALE = 60;
}
