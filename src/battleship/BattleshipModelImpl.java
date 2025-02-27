package battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BattleshipModelImpl implements BattleshipModel {

  private final int height = 10;  // height of the map
  private final int width = 10;  // width of the map
  private final int maxGuesses = 10;  // maximum number of guesses

  private CellState[][] visualMap;  // map of the game
  private ArrayList<Ship> ships;  // list of ships

  /**
   * Generate initial visual map.
   */
  private void initvisualMap() {
    visualMap = new CellState[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        visualMap[i][j] = CellState.UNKNOWN;
      }
    }
  }

  /**
   * Find all legal places on board for a given ship.
   * Based on map height, width, and occupied grids.
   *
   * @param boatLength length of the ship
   * @param isVertical whether the ship is vertical
   * @return a list of all legal places
   */
  private ArrayList<Vector2Int> findLegalStartPlaceForShip(ArrayList<Vector2Int> occupiedGrids,
                                                           int boatLength, boolean isVertical) {
    ArrayList<Vector2Int> legalPlaces = new ArrayList<>();
    if (isVertical) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          boolean isLegal = true;
          for (int k = 0; k < boatLength; k++) {
            if (i + k >= height || occupiedGrids.contains(new Vector2Int(i + k, j))) {
              isLegal = false;
              break;
            }
          }
          if (isLegal) {
            legalPlaces.add(new Vector2Int(i, j));
          }
        }
      }
    } else {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          boolean isLegal = true;
          for (int k = 0; k < boatLength; k++) {
            if (j + k >= width || occupiedGrids.contains(new Vector2Int(i, j + k))) {
              isLegal = false;
              break;
            }
          }
          if (isLegal) {
            legalPlaces.add(new Vector2Int(i, j));
          }
        }
      }
    }
    return legalPlaces;
  }

  /**
   * Initializes the game by setting up the grids and randomly placing ships.
   */
  @Override
  public void startGame() throws IllegalStateException {
    initvisualMap();
    ships = new ArrayList<>();
    ArrayList<Vector2Int> occupiedGrids = new ArrayList<>();
    for (ShipType shipType : ShipType.values()) {
      int boatLength = shipType.getSize();
      boolean isVertical = Math.random() < 0.5;
      ArrayList<Vector2Int> legalPlaces = findLegalStartPlaceForShip(occupiedGrids, boatLength,
          isVertical);
      if (legalPlaces.size() == 0) {
        throw new IllegalStateException("No legal place for ship " + shipType);
      }
      int randomIndex = (int) (Math.random() * legalPlaces.size());
      Vector2Int startPlace = legalPlaces.get(randomIndex);
      ArrayList<Vector2Int> body = new ArrayList<>();
      for (int i = 0; i < boatLength; i++) {
        if (isVertical) {
          body.add(new Vector2Int(startPlace.gridX + i, startPlace.gridY));
          occupiedGrids.add(new Vector2Int(startPlace.gridX + i, startPlace.gridY));
        } else {
          body.add(new Vector2Int(startPlace.gridX, startPlace.gridY + i));
          occupiedGrids.add(new Vector2Int(startPlace.gridX, startPlace.gridY + i));
        }
      }
      ships.add(new Ship(shipType, body));
    }
  }

  /**
   * Processes the player's guess at the specified coordinate.
   *
   * @param row the row index (0-based)
   * @param col the column index (0-based)
   * @return true if the guess was a hit, false otherwise
   * @throws IllegalArgumentException if the coordinates are out of bounds or the cell has already
   *                                  been guessed
   * @throws IllegalStateException    if the game is already over
   */
  @Override
  public boolean makeGuess(int row, int col) {
    return false;
  }

  /**
   * Checks if the game is over.
   *
   * @return true if all ships are sunk or the maximum number of guesses is reached, false otherwise
   */
  @Override
  public boolean isGameOver() {
    // out of guesses
    if (getGuessCount() >= maxGuesses) {
      return true;
    }
    // all ships are sunk
    return areAllShipsSunk();
  }

  /**
   * Checks if all ships have been sunk.
   *
   * @return true if all ships are sunk, false otherwise
   */
  @Override
  public boolean areAllShipsSunk() {
    for (Ship ship : ships) {
      if (!ship.isDead()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Gets the number of guesses the player has made so far.
   *
   * @return the number of guesses made
   */
  @Override
  public int getGuessCount() {
    return 0;
  }

  /**
   * Gets the maximum number of guesses allowed.
   *
   * @return the maximum number of guesses
   */
  @Override
  public int getMaxGuesses() {
    return 0;
  }


  /**
   * Retrieves the current state of the cell grid for display purposes.
   *
   * @return a deep copy of the 2D array representing the cell grid state
   */
  @Override
  public CellState[][] getCellGrid() {
    return new CellState[0][];
  }

  /**
   * Retrieves the current state of the ship grid after the game is over. The ship grid should not
   * be revealed during the game.
   *
   * @return a deep copy of the 2D array representing the ship grid state
   * @throws IllegalStateException if the game is not over
   */
  @Override
  public ShipType[][] getShipGrid() {
    return new ShipType[0][];
  }
}
