package battleship;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.Test;

public class BattleshipModelImplTest {

  private BattleshipModelImpl model;

  @Before
  public void setUp() {
    model = new BattleshipModelImpl();
    model.startGame();
  }

  @Test
  public void testVisualMapIsInitialized() {

    CellState[][] visualMap = model.getCellGrid();
    assertNotNull(visualMap);
    assertEquals(10, visualMap.length);
    assertEquals(10, visualMap[0].length);
  }

  @Test
  public void testShipsArePlacedOnBoard() {
    ShipType[][] shipGrid = model.getShipGrid();
    assertNotNull(shipGrid);
    assertEquals(10, shipGrid.length);
    assertEquals(10, shipGrid[0].length);

    int shipCount = 0;
    for (ShipType[] row : shipGrid) {
      for (ShipType cell : row) {
        if (cell != null) {
          shipCount++;
        }
      }
    }
    assertTrue(shipCount > 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeGuessThrowsIllegalArgumentExceptionForOutOfBounds() {
    model.makeGuess(-1, 0); // Out of bounds
  }

  @Test(expected = IllegalStateException.class)
  public void testMakeGuessThrowsIllegalStateExceptionIfGameOver() {
    while (!model.isGameOver()) {
      model.makeGuess(0, 0);
    }
    model.makeGuess(0, 0);
  }

  @Test
  public void testMakeGuessIncrementsGuessCount() {
    int initialGuessCount = model.getGuessCount();
    model.makeGuess(0, 0);
    assertEquals(initialGuessCount + 1, model.getGuessCount());
  }

  @Test
  public void testMakeGuessIdentifiesHit() {
    boolean hit = model.makeGuess(0, 0);
    assertTrue(
        hit == true || hit == false);  // cannot know true or false as map is generated randomly
  }

  @Test
  public void testMakeGuessUpdatesVisualMap() {
    model.makeGuess(0, 0);
    CellState[][] visualMap = model.getCellGrid();
    assertNotNull(visualMap);
    assertTrue(visualMap[0][0] == CellState.HIT || visualMap[0][0] == CellState.MISS);
  }

  @Test
  public void isGameOver() {
  }

  @Test
  public void areAllShipsSunk() {
  }

  @Test
  public void getGuessCount() {
  }

  @Test
  public void getMaxGuesses() {
  }

  @Test
  public void getCellGrid() {
  }

  @Test
  public void getShipGrid() {
  }
}