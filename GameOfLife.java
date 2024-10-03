import java.util.Random;

public class GameOfLife {
    private int rows;
    private int cols;
    private int[][] grid;
    private int[][] nextGrid;

    // Constructor to initialize the grid
    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new int[rows][cols];
        nextGrid = new int[rows][cols];
        initializeGrid();
    }

    // Initialize the grid randomly with 0 (dead) or 1 (alive) cells
    private void initializeGrid() {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = random.nextInt(2); // Randomly assign 0 or 1
            }
        }
    }

    // Print the grid
    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] == 1 ? "O " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Count live neighbors of a cell at position (x, y)
    private int countLiveNeighbors(int x, int y) {
        int count = 0;
        // Iterate over the 3x3 neighborhood around (x, y)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Exclude the cell itself
                if (i == 0 && j == 0) continue;
                int row = (x + i + rows) % rows; // Wrap around the grid edges
                int col = (y + j + cols) % cols;
                count += grid[row][col];
            }
        }
        return count;
    }

    // Apply the rules of Game of Life for the next generation
    public void nextGeneration() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                // Apply rules
                if (grid[i][j] == 1) { // Live cell
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        nextGrid[i][j] = 0; // Dies due to under/overpopulation
                    } else {
                        nextGrid[i][j] = 1; // Lives on
                    }
                } else { // Dead cell
                    if (liveNeighbors == 3) {
                        nextGrid[i][j] = 1; // Becomes alive due to reproduction
                    } else {
                        nextGrid[i][j] = 0; // Stays dead
                    }
                }
            }
        }

        // Update the grid for the next generation
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = nextGrid[i][j];
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int rows = 10;
        int cols = 10;
        GameOfLife game = new GameOfLife(rows, cols);

        // Run for a certain number of generations
        int generations = 10;
        for (int i = 0; i < generations; i++) {
            System.out.println("Generation " + (i + 1));
            game.printGrid();
            game.nextGeneration();
            Thread.sleep(1000); // Pause for 1 second between generations
        }
    }
}
