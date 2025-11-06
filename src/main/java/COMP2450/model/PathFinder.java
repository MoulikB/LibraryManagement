package COMP2450.model;

import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Preconditions;

/**
 * PathFinder
 * ------------------------------------------------------------
 * Implements a backtracking (stack-based DFS) algorithm on the map grid
 * to find a path from a start coordinate to an end coordinate.
 *
 * Design by Contract:
 *  - Preconditions checked with Guava Preconditions.
 *  - Loop invariants documented inline.
 *  - Postconditions verified upon completion.
 *
 * Dependencies:
 *  - Uses Stack<Coordinate> interface and LinkedListStack<Coordinate> implementation.
 *  - Relies on Map.java exposing a 2D char[][] grid field named 'map'.
 */
public class PathFinder {

    private final Map mapRef;
    private final Coordinate start;
    private final Coordinate end;

    // Stores final discovered path (start -> ... -> goal)
    private final LinkedListStack<Coordinate> path = new LinkedListStack<>();

    public PathFinder(Map mapRef, Coordinate start, Coordinate end) {
        // === Preconditions ===


        this.mapRef = mapRef;
        this.start = start;
        this.end = end;
    }

    /**
     * Attempts to find a path from start to end using a backtracking (DFS) algorithm.
     * Returns true if a valid path exists, false otherwise.
     */
    public boolean findPath() {
        char[][] grid = mapRef.getMap();
        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Coordinate[][] parent = new Coordinate[rows][cols];

        Stack<Coordinate> stack = new LinkedListStack<>();
        stack.push(start);

        // === Invariant before loop ===
        // - Each coordinate in the stack is visitable and not yet visited.
        // - visited[x][y] == true ↔ cell has been fully expanded.

        while (!stack.isEmpty()) {
            Coordinate current = (Coordinate) stack.pop();
            int x = current.getX();
            int y = current.getY();

            if (!inBounds(grid, current)) continue;
            if (visited[y][x]) continue;

            visited[y][x] = true;

            // === Goal check ===
            if (current.equals(end)) {
                reconstructPath(parent, current);
                // === Postcondition ===
                Preconditions.checkState(!path.isEmpty(), "Path reconstruction failed.");
                return true;
            }

            // Generate and push valid neighbours (E, W, S, N)
            for (Coordinate neighbor : neighbors(current, cols, rows)) {
                int nx = neighbor.getX();
                int ny = neighbor.getY();

                if (inBounds(grid, neighbor)
                        && !visited[ny][nx]
                        && isWalkable(grid[ny][nx])) {
                    parent[ny][nx] = current;
                    stack.push(neighbor);
                }
            }

            // === Loop Invariant (maintained) ===
            // Every coordinate pushed into the stack is unvisited and visitable.
        }

        // === Postcondition ===
        Preconditions.checkState(path.isEmpty(), "Path stack must be empty if no path found.");
        return false;
    }

    /**
     * Returns the path found (top of the stack is the goal).
     */
    public Stack<Coordinate> getPath() {
        return path;
    }

    // ------------------- Helper methods -------------------

    private static boolean inBounds(char[][] grid, Coordinate c) {
        int rows = grid.length;
        int cols = grid[0].length;
        int x = c.getX();
        int y = c.getY();
        return y >= 0 && y < rows && x >= 0 && x < cols;
    }

    private static boolean isWalkable(char cell) {
        return cell == ' ';
    }

    private static List<Coordinate> neighbors(Coordinate c, int cols, int rows) {
        int x = c.getX();
        int y = c.getY();
        List<Coordinate> out = new ArrayList<>(4);
        if (x + 1 < cols) out.add(new Coordinate(x + 1, y));
        if (x - 1 >= 0)   out.add(new Coordinate(x - 1, y));
        if (y + 1 < rows) out.add(new Coordinate(x, y + 1));
        if (y - 1 >= 0)   out.add(new Coordinate(x, y - 1));
        return out;
    }

    private void reconstructPath(Coordinate[][] parent, Coordinate goal) {
        Coordinate cur = goal;
        while (cur != null) {
            path.push(cur);
            int x = cur.getX();
            int y = cur.getY();
            cur = parent[y][x];
        }
    }

    /**
     * Prints the map grid with the discovered path drawn on it.
     *
     * Preconditions:
     *  - findPath() must have been called and succeeded
     * Postconditions:
     *  - Map printed with '+' for path cells, 'S' for start, 'E' for goal
     *  - Original mapRef.map is NOT permanently modified
     */
    public void printPathOnMap() {
        Preconditions.checkNotNull(mapRef, "Map reference cannot be null.");
        Preconditions.checkNotNull(mapRef.getMap(), "Map grid cannot be null.");
        Preconditions.checkState(!path.isEmpty(),
                "Path not found. Run findPath() successfully before printing.");

        char[][] grid = mapRef.getMap();
        int rows = grid.length;
        int cols = grid[0].length;

        // Create a copy so we don’t alter the real map
        char[][] visual = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(grid[r], 0, visual[r], 0, cols);
        }

        // Mark the path
        LinkedListStack<Coordinate> temp = new LinkedListStack<>();

        // Reverse path order to draw from start → goal
        while (!path.isEmpty()) {
            Coordinate c = path.pop();
            temp.push(c);
        }

        boolean first = true;
        while (!temp.isEmpty()) {
            Coordinate c = temp.pop();
            int x = c.getX();
            int y = c.getY();

            // Mark start, end, and path
            if (first) {
                visual[y][x] = 'S'; // start
                first = false;
            } else if (temp.isEmpty()) {
                visual[y][x] = 'E'; // end
            } else {
                if (visual[y][x] == ' ') {
                    visual[y][x] = '+';
                }
            }

            // Restore original stack order
            path.push(c);
        }

        // Print the visualized map
        System.out.println("=== MAP WITH PATH ===");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(visual[r][c]);
            }
            System.out.println();
        }
        System.out.println("=====================");
    }

}
