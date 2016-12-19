package model;

import org.jetbrains.annotations.NotNull;
import utils.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author apomosov
 */
public class GameSessionImpl implements GameSession {
  private static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id = idGenerator.next();
  @NotNull
  private final Field field = new Field();
  @NotNull
  private final List<Player> players = new CopyOnWriteArrayList<>();
  @NotNull
  private static FoodGenerator foodGenerator;
  @NotNull
  private final PlayerPlacer playerPlacer;
  @NotNull
  public static VirusGenerator virusGenerator;

  public GameSessionImpl(@NotNull FoodGenerator foodGenerator, @NotNull PlayerPlacer playerPlacer, @NotNull VirusGenerator virusGenerator) {
    GameSessionImpl.foodGenerator = foodGenerator;
    GameSessionImpl.foodGenerator.setField(field);
    this.playerPlacer = playerPlacer;
    this.playerPlacer.setField(field);
    GameSessionImpl.virusGenerator = virusGenerator;
    GameSessionImpl.virusGenerator.setField(field);
    virusGenerator.generate();
  }

  @Override
  public void join(@NotNull Player player) {
    players.add(player);
    this.playerPlacer.place(player);
  }

  @Override
  public void leave(@NotNull Player player) {
    players.remove(player);
  }

  @Override
  public List<Player> getPlayers() {
    return new CopyOnWriteArrayList<>(players);
  }

  @Override
  public Field getField() {
    return field;
  }

  @Override
  public String toString() {
    return "GameSessionImpl{" +
        "id=" + id +
        '}';
  }

  public static FoodGenerator getFoodGenerator(){return foodGenerator;}

  public static VirusGenerator getVirusGenerator(){return virusGenerator;}
}
