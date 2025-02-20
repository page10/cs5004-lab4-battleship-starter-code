import battleship.BattleshipModel;
import battleship.BattleshipModelImpl;
import battleship.CellState;
import java.util.Scanner;

/**
 * Main class to run the Battleship game in the console.
 */
public class BattleMain {
  /**
   * Main method to run the Battleship game in the console.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    BattleshipModel model = new BattleshipModelImpl();
    model.startGame();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to Battleship!");
    System.out.println("The game has been initialized. Try to sink all the ships.");

    // Game loop
    while (!model.isGameOver()) {
      // Display the grid
      printCellGrid(model.getCellGrid());

      // Get the user's guess
      System.out.print("Enter your guess (e.g., A0): ");
      String input = scanner.nextLine().trim().toUpperCase();
      if (input.length() < 2 || input.length() > 3) {
        System.out.println(
            "Invalid input. Please enter a letter (A-J) followed by a number (0-9).");
        continue;
      }
      // Parse row and column
      int row = input.charAt(0) - 'A';
      int col;

      try {
        col = Integer.parseInt(input.substring(1));
      } catch (NumberFormatException e) {
        System.out.println("Invalid column number. Please enter a number between 0 and 9.");
        continue;
      }

      // Validate row and column ranges
      if (row < 0 || row >= 10 || col < 0 || col >= 10) {
        System.out.println("Coordinates out of bounds. Rows: A-J, Columns: 0-9.");
        continue;
      }
      // Process the guess
      try {
        boolean hit = model.makeGuess(row, col);
        if (hit) {
          System.out.println("Hit!");
        } else {
          System.out.println("Miss!");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid guess: " + e.getMessage());
      } catch (IllegalStateException e) {
        System.out.println("Game over: " + e.getMessage());
        break;
      }
      System.out.println("Guesses so far: " + model.getGuessCount() + "/" + model.getMaxGuesses());
    }

    // End game
    System.out.println("\nGame Over!");

    if (model.areAllShipsSunk()) {
      System.out.println(
          "Congratulations! You've sunk all the ships in " + model.getGuessCount() + " guesses.");
    } else {
      System.out.println("You've run out of guesses.");
    }

    // Reveal the ship positions
    try {
      printCellGrid(model.getCellGrid());
      printShipGrid(model.getShipGrid());
    } catch (IllegalStateException e) {
      System.out.println("Error retrieving ship grid: " + e.getMessage());
    }

    scanner.close();
  }

  /**
   * Prints the current state of the cell grid.
   *
   * @param grid The current state of the cell grid.
   */
  private static void printCellGrid(CellState[][] grid) {
    System.out.println("\nCurrent Grid State:");
    System.out.print("  ");
    for (int i = 0; i < grid[0].length; i++) {
      System.out.print(i + " ");
    }
    System.out.println();
    for (int i = 0; i < grid.length; i++) {
      System.out.print((char) ('A' + i) + " ");
      for (int j = 0; j < grid[i].length; j++) {
        System.out.print(grid[i][j].getSymbol() + " ");
      }
      System.out.println();
    }
  }

  /**
   * Prints the ship grid after the game is over.
   *
   * @param grid The ship grid after the game is over.
   */
  private static void printShipGrid(battleship.ShipType[][] grid) {
    System.out.println("\nShip Grid:");
    System.out.print("  ");
    for (int i = 0; i < grid[0].length; i++) {
      System.out.print(i + " ");
    }
    System.out.println();
    for (int i = 0; i < grid.length; i++) {
      System.out.print((char) ('A' + i) + " ");
      for (int j = 0; j < grid[i].length; j++) {
        System.out.print(grid[i][j] == null ? "- " : grid[i][j].getSymbol() + " ");
      }
      System.out.println();
    }
  }
}