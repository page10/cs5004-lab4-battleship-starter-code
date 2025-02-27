package battleship;

import java.util.ArrayList;

/**
 * Represents a ship in the Battleship game.
 * Each ship has a type and a boolean flag indicating
 * whether it has been sunk.
 */
public class Ship {
  private ArrayList<Vector2Int> body = new ArrayList<>();
  private ShipType type;
  private boolean isDead;

  /**
   * Constructor for a Ship object.
   *
   * @param type the type of the ship
   * @param body the body positions of the ship
   */
  public Ship(ShipType type, ArrayList<Vector2Int> body) {
    this.type = type;
    this.isDead = false;
    this.body = body;
  }

  /**
   * Returns the type of the ship.
   *
   * @return the type of the ship
   */
  public ShipType getType() {
    return type;
  }

  /**
   * Returns whether the ship has been sunk.
   *
   * @return true if the ship has been sunk, false otherwise
   */
  public boolean isDead() {
    return isDead;
  }

  /**
   * Marks the ship as sunk.
   */
  public void sink() {
    isDead = true;
  }

  /**
   * Returns the body positions of the ship.
   *
   * @return the body positions of the ship
   */
  public ArrayList<Vector2Int> getBody() {
    return body;
  }

  /**
   * Set the body positions of the ship.
   *
   * @param body the body positions of the ship
   */
  public void setBody(ArrayList<Vector2Int> body) {
    this.body = body;
  }

}
