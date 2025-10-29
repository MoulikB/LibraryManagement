package COMP2450.model;


import com.google.common.base.Preconditions;

/**
 * Map
 * An ASCII grid map that represents the physical layout of a library,
 * including spaces, categories, and walkable areas.
 */

public class Map {
    private char[][] map;
    public Library library;
    private char[][] grid;
    private int row, column;
    private boolean[][] visited;
    private boolean[][] pushed = new boolean[row][column];

    /*
    Creates map, for phase 1 we are using a default map
     */
    public Map(Library library) {
        Preconditions.checkNotNull(library);
        this.library = library;
        map = new char[][]{
                "+--------------------------------------------------------+".toCharArray(),
                "| 💻💻💻💻💻💻💻💻💻💻                                🚪|".toCharArray(),
                "| 💻💻💻💻💻💻💻💻💻💻                                  |".toCharArray(),
                "| HHHHHH             CCCC            AAAAAA         |".toCharArray(),
                "| HHHHHH             CCCC            AAAAAA         |".toCharArray(),
                "| RRRRRR             TTTT            FFFFFF        |".toCharArray(),
                "| RRRRRR             TTTT            FFFFFF        |".toCharArray(),
                "| NNNNNN             🖨️🖨️            3️⃣3️⃣3️⃣         |".toCharArray(),
                "| NNNNNN             🖨️🖨️            3️⃣3️⃣3️⃣         |".toCharArray(),
                "|                                                        |".toCharArray(),
                "| |--------D---------|  |-------D--------|  |------D-------| |".toCharArray(),
                "| |                |  |               |  |             | |".toCharArray(),
                "| |  Private Study |  | Private Study |  | Private Study| |".toCharArray(),
                "| |     Space 1    |  |    Space 2    |  |    Space 3  | |".toCharArray(),
                "| |                |  |               |  |             | |".toCharArray(),
                "| |----------------|  |---------------|  |-------------| |".toCharArray(),
                "|                                                        |".toCharArray(),
                "|                                   |~~~~~~~~~~~~~~~|    |".toCharArray(),
                "|                                   |    Main Desk  |    |".toCharArray(),
                "|                                   |     (Help)    |    |".toCharArray(),
                "|                                   |~~~~~~~~~~~~~~~|    |".toCharArray(),
                "+--------------------------------------------------------+".toCharArray()
        };
    }

    public char[][] getMap() {
        return this.map;
    }

    /**
     * Determines whether current point is the exit
     *
     * @param p position to check
     * @return whether exit or not
     */
    public boolean isExit(Position p, Position goal) {
        return p.equals(goal);
    }

    /**
     * Determines whether current point has been visited before
     *
     * @param p position to check
     * @return whether visited or not
     */
    public boolean isVisited(Position p) {
        return this.visited[p.row][p.column];
    }

    /**
     * Determines whether current point has been pushed or not
     *
     * @param p position to check
     * @return boolean representing whether pushed or not
     */
    public boolean isPushed(Position p) {
        return this.pushed[p.row][p.column];
    }

    /**
     * Marks given position as visiited
     *
     * @param p position to mark
     */
    public void markVisited(Position p) {
        visited[p.row][p.column] = true;
        if (this.grid[p.row][p.column] == '.' || this.grid[p.row][p.column] == 'U')
            this.grid[p.row][p.column] = '+';
    }

    /**
     * Marks given position as pushed
     *
     * @param p position to mark
     */
    public void markPushed(Position p) {
        this.pushed[p.row][p.column] = true;
    }

    /**
     * Get the surrounding neighbour positions of current position
     *
     * @param p position to check
     * @return an array of the neighbouring positions
     */
    public Position[] getNeighbors(Position p) {
        Position[] nbrs = new Position[4];
        int count = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] d : dirs) {
            int nr = p.row + d[0];
            int nc = p.column + d[1];
            if (nr >= 0 && nr < this.row && nc >= 0 && nc < this.column && this.grid[nr][nc] != 'W') {
                nbrs[count++] = new Position(nr, nc);
            }
        }

        Position[] result = new Position[count];
        System.arraycopy(nbrs, 0, result, 0, count);
        return result;
    }

    /**
     * Prints the maze and checks whether a position is our current or already visited
     */
    public void printMaze(Position current) {
        System.out.println("\nStatus of maze (X = current position, + = previously visited):");
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                if (i == current.row && j == current.column)
                    System.out.print("X ");
                else
                    System.out.print(this.grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
