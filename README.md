# Battleship Game Implementation

## Overview

In this assignment, you will implement a Battleship game model by creating a class named `BattleshipModelImpl` that adheres to the provided `BattleshipModel` interface. Additionally, you will write comprehensive unit tests to ensure the correctness of your implementation.

## Game Rules

In this Battleship game, the program randomly arranges ships on a 10x10 grid. The user’s objective is to sink all the ships by making guesses about their locations. Here are the key rules:

1. **Grid**: The game is played on a 10x10 grid, with rows labeled A-J and columns labeled 0-9.
2. **Ships**: Various types of ships are placed on the grid, each occupying a different number of consecutive cells. The ships are placed either horizontally or vertically.
3. **Ship Types**:
    - **Patrol Boat**: Occupies 2 cells.
    - **Submarine**: Occupies 3 cells.
    - **Destroyer**: Occupies 3 cells.
    - **Battleship**: Occupies 4 cells.
    - **Aircraft Carrier**: Occupies 5 cells.
4. **Guessing**: The user makes guesses by specifying a cell's row and column. The program indicates whether the guess is a hit (a ship is at that location) or a miss.
5. **Game Over**: The game ends when all ships are sunk or the user runs out of guesses.
6. **Feedback**: After each guess, the program updates the grid to show hits and misses, and provides feedback on the number of guesses made and remaining.

The goal is to sink all the ships with the fewest number of guesses.

## Provided Files

- **`BattleshipModel` Interface**: Defines the methods that your `BattleshipModelImpl` class must implement.
- **`CellState` Enum**: Represents the state of a cell in the game grid (e.g., UNKNOWN, MISS, HIT).
- **`ShipType` Enum**: Represents the different types of ships in the game (e.g., DESTROYER, SUBMARINE).

## Requirements

### Implementation

* **Class**: `BattleshipModelImpl`
    - Implement all methods defined in the `BattleshipModel` interface.
    - Ensure that your implementation correctly handles game logic, including ship placement, making guesses, and determining game state.

### Unit Tests

* **Unit Tests**: Create a test class. Here are some suggested tests (but you might need to write more):
    - **Valid Guesses**: Ensure that valid guesses are processed correctly.
    - **Invalid Guesses**: Ensure that guesses outside the grid boundaries or on the same cell twice throw appropriate exceptions.
    - **Game Over**: Verify that the game correctly identifies when all ships are sunk or the maximum number of guesses is reached.
    - **Grid Access**: Ensure that the ship grid and cell grid are correctly managed and that deep copies are returned where necessary.


## Submission

Submit the following files:
- `BattleshipModelImpl.java`: Your implementation of the `BattleshipModel` interface.
- Your unit tests for the `BattleshipModelImpl` class.

## Grading Criteria

- **Correctness**: Your implementation must correctly follow the game rules and interface specifications.
- **Testing**: Your unit tests must comprehensively cover all aspects of the game logic.
- **Code Quality**: Your code should be well-organized, readable, and follow best practices.

## Getting Started

1. Review the provided `BattleshipModel` interface and the `CellState` and `ShipType` enums.
2. Implement the `BattleshipModelImpl` class.
3. Write unit tests to verify your implementation.