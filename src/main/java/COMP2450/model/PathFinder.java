package COMP2450.model;

/**
 * PathFinder
 * Finds and marks a path on the library map from the kiosk ('K')
 * to a chosen destination symbol such as 'T', 'L', or 'C'.
 * Uses a depth-first search (DFS) algorithm with a stack.
 */
public class PathFinder {

    private static final char PATH_CHAR  = '+';
    private static final char FLOOR_CHAR = ' ';
    private static final char START_CHAR = 'K';

    private final Library library;
    private final char[][] map;

    /**
     * Creates a PathFinder for a specific library.
     *
     * @param library the library this pathfinder will use
     * @throws IllegalArgumentException if the library is null
     */
    public PathFinder(Library library) {
        if (library == null) {
            throw new IllegalArgumentException("Library cannot be null");
        }
        this.library = library;
        this.map = library.getMap().getMap();
    }

    /**
     * Runs the DFS pathfinding algorithm to find a route
     * from the kiosk ('K') to a given target symbol.
     *
     * @param targetChar the destination character on the map (for example, 'T', 'L', or 'C')
     * @return true if a valid path is found, false otherwise
     */
    public boolean runForTarget(char targetChar) {
        boolean result;
        Coordinate start  = findChar(START_CHAR);
        Coordinate target = findChar(targetChar);

        if (start == null || target == null) {
            System.out.println("❌ Start or target not found on the map.");
            result = false;
        } else {
            result = dfsAndMarkPath(start, target);
        }

        return result;
    }

    /**
     * Performs a depth-first search to locate the path between
     * the start and target points, then draws it on the map.
     *
     * @param start  starting coordinate (the kiosk)
     * @param target target coordinate (chosen destination)
     * @return true if a path is found
     */
    private boolean dfsAndMarkPath(Coordinate start, Coordinate target) {
        boolean found = false;
        final int rows = map.length;
        final int cols = map[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Coordinate[][] parent = new Coordinate[rows][cols];
        LinkedListStack<Coordinate> stack = new LinkedListStack<>();

        stack.push(start);
        final int[][] dirs = { {1,0}, {-1,0}, {0,1}, {0,-1} };

        while (!stack.isEmpty()) {
            Coordinate cur = stack.pop();
            int r = cur.getX();
            int c = cur.getY();

            if (inBounds(r, c, rows, cols) && !visited[r][c]) {
                visited[r][c] = true;

                if (r == target.getX() && c == target.getY()) {
                    found = true;
                } else {
                    for (int[] dir : dirs) {
                        int nr = r + dir[0];
                        int nc = c + dir[1];
                        if (isWalkable(nr, nc, rows, cols, visited, target)) {
                            if (parent[nr][nc] == null) {
                                parent[nr][nc] = cur;
                            }
                            stack.push(new Coordinate(nr, nc));
                        }
                    }
                }
            }
        }

        if (found) {
            LinkedListStack<Coordinate> rev = new LinkedListStack<>();
            Coordinate step = target;
            while (step != null) {
                rev.push(step);
                step = parent[step.getX()][step.getY()];
            }

            while (!rev.isEmpty()) {
                Coordinate k = rev.pop();
                int rr = k.getX();
                int cc = k.getY();
                if (map[rr][cc] == FLOOR_CHAR) {
                    map[rr][cc] = PATH_CHAR;
                }
            }
        }

        return found;
    }

    /**
     * Checks whether the given position is inside the map boundaries.
     *
     * @param r row index
     * @param c column index
     * @param rows total rows in map
     * @param cols total columns in map
     * @return true if inside bounds, false otherwise
     */
    private boolean inBounds(int r, int c, int rows, int cols) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    /**
     * Checks if a map cell can be walked on.
     *
     * @param r row index
     * @param c column index
     * @param rows total rows in map
     * @param cols total columns in map
     * @param visited cells that have already been visited
     * @param target target coordinate
     * @return true if the cell is walkable
     */
    private boolean isWalkable(int r, int c, int rows, int cols, boolean[][] visited, Coordinate target) {
        boolean walkable = false;
        if (inBounds(r, c, rows, cols)) {
            if (!visited[r][c]) {
                if (r == target.getX() && c == target.getY()) {
                    walkable = true;
                } else if (map[r][c] == FLOOR_CHAR) {
                    walkable = true;
                }
            }
        }
        return walkable;
    }

    /**
     * Finds the first coordinate on the map that matches a given character.
     *
     * @param ch the character to find
     * @return a Coordinate with that character, or null if not found
     */
    private Coordinate findChar(char ch) {
        Coordinate found = null;
        for (int r = 0; r < map.length && found == null; r++) {
            for (int c = 0; c < map[r].length && found == null; c++) {
                if (map[r][c] == ch) {
                    found = new Coordinate(r, c);
                }
            }
        }
        return found;
    }

    /**
     * Removes all '+' marks left from a previous pathfinding run.
     */
    public void clearPath() {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == PATH_CHAR) {
                    map[r][c] = FLOOR_CHAR;
                }
            }
        }
    }

    /**
     * Prints the current map to the console,
     * showing the library layout and any marked path.
     */
    public void printMap() {
        System.out.println("\n=== LIBRARY MAP WITH PATH ===");
        for (char[] row : map) {
            System.out.println(new String(row));
        }
    }
}
