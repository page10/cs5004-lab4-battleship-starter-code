package battleship;

/**
 * Represents a 2D integer vector.
 */
public class Vector2Int {
  public int gridX;
  public int gridY;

  public Vector2Int(int x, int y) {
    this.gridX = x;
    this.gridY = y;
  }

  public boolean equals(Vector2Int other) {
    return this.gridX == other.gridX && this.gridY == other.gridY;
  }

  public boolean equals(int x, int y) {
    return this.gridX == x && this.gridY == y;
  }
}
