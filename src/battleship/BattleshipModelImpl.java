package battleship;

import java.util.ArrayList;

/**
 * Represents the model for the Battleship game.
 */
public class BattleshipModelImpl implements BattleshipModel {

  private final int height = 10;  // height of the map
  private final int width = 10;  // width of the map
  private final int maxGuesses = 10;  // maximum number of guesses
  private int guessCount = 0;  // number of guesses made

  private CellState[][] visualMap;  // map of the game
  private ArrayList<Ship> ships;  // list of ships

  /**
   * Generate initial visual map.
   */
  private void initVisualMap() {
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
    initVisualMap();
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
  public boolean makeGuess(int row, int col)
      throws IllegalArgumentException, IllegalStateException {
    // deal with exception
    if (row < 0 || row >= height || col < 0 || col >= width) {
      throw new IllegalArgumentException("Coordinates out of bounds. Rows: A-J, Columns: 0-9.");
    }

    if (isGameOver()) {
      throw new IllegalStateException("Game is already over.");
    }

    // deal with guess count
    guessCount++;

    // sunk the ship if it is a hit
    boolean hit = checkHit(row, col);  // don't like it. checking and modifying should be separated

    // update visual map
    updateVisualMap(new Vector2Int(row, col), hit);

    return hit;

  }


  /**
   * Check if the guess is a hit.
   * sunk the ship if it is a hit.
   *
   * @param row the row index (0-based)
   * @param col the column index (0-based)
   * @return true if the guess was a hit, false otherwise
   */
  private boolean checkHit(int row, int col) {
    for (Ship ship : ships) {
      for (Vector2Int body : ship.getBody()) {
        if (body.gridX == row && body.gridY == col) {
          ship.sink();
          return true;
        }
      }
    }
    return false;
  }

  private void updateVisualMap(Vector2Int pos, boolean hit) {
    if (hit) {
      visualMap[pos.gridX][pos.gridY] = CellState.HIT;
    } else {
      visualMap[pos.gridX][pos.gridY] = CellState.MISS;
    }

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
    return guessCount;
  }

  /**
   * Gets the maximum number of guesses allowed.
   *
   * @return the maximum number of guesses
   */
  @Override
  public int getMaxGuesses() {
    return maxGuesses;
  }


  /**
   * Retrieves the current state of the cell grid for display purposes.
   *
   * @return a deep copy of the 2D array representing the cell grid state
   */
  @Override
  public CellState[][] getCellGrid() {
    return visualMap;
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
    ShipType[][] shipGrid = new ShipType[height][width];

    for (Ship ship : ships) {
      for (Vector2Int body : ship.getBody()) {
        shipGrid[body.gridX][body.gridY] = ship.getType();
      }
    }
    return shipGrid;
  }
}
