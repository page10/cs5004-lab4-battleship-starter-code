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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Vector2Int that = (Vector2Int) obj;
    return gridX == that.gridX && gridY == that.gridY;
  }

  @Override
  public int hashCode() {
    return 31 * gridX + gridY;
  }
  
}
