package COMP2450.model;

public class PathFinder {

    private static final char PATH_CHAR   = '+';
    private static final char FLOOR_CHAR  = ' ';
    private static final char START_CHAR  = 'K';

    private final Library library;
    private char[][] map;

    /** Construct a PathFinder tied to a specific Library object. */
    public PathFinder(Library library) {
        if (library == null) {
            throw new IllegalArgumentException("Library cannot be null");
        }
        this.library = library;
        this.map = library.getMap().getMap(); // use the map from the library
    }

    /**
     * Run DFS pathfinding from 'K' to the specified target character.
     * @param targetChar The symbol to find (e.g., 'T', 'L', 'C')
     * @return true if a path was found
     */
    public boolean runForTarget(char targetChar) {
        Coordinate start  = findChar(START_CHAR);
        Coordinate target = findChar(targetChar);

        if (start == null || target == null) {
            System.out.println("❌ Start or target not found on the map.");
            return false;
        }

        return dfsAndMarkPath(start, target);
    }

    /**
     * Perform DFS pathfinding using only LinkedListStack and Coordinate.
     */
    private boolean dfsAndMarkPath(Coordinate start, Coordinate target) {
        final int rows = map.length;
        final int cols = map[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Coordinate[][] parent = new Coordinate[rows][cols];

        LinkedListStack<Coordinate> stack = new LinkedListStack<>();
        stack.push(start);

        final int[][] dirs = { {1,0}, {-1,0}, {0,1}, {0,-1} };

        boolean found = false;

        while (!stack.isEmpty()) {
            Coordinate cur = stack.pop();
            int r = cur.getX();
            int c = cur.getY();

            if (!inBounds(r, c, rows, cols) || visited[r][c]) continue;

            visited[r][c] = true;

            if (r == target.getX() && c == target.getY()) {
                found = true;
                break;
            }

            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                if (isWalkable(nr, nc, rows, cols, visited, target)) {
                    if (parent[nr][nc] == null) parent[nr][nc] = cur;
                    stack.push(new Coordinate(nr, nc));
                }
            }
        }

        if (!found) return false;

        // reconstruct true path from target → start
        LinkedListStack<Coordinate> rev = new LinkedListStack<>();
        Coordinate step = target;
        while (step != null) {
            rev.push(step);
            Coordinate p = parent[step.getX()][step.getY()];
            if (p == null) break;
            step = p;
        }

        // draw path
        while (!rev.isEmpty()) {
            Coordinate k = rev.pop();
            int rr = k.getX(), cc = k.getY();
            if (map[rr][cc] == FLOOR_CHAR) map[rr][cc] = PATH_CHAR;
        }

        return true;
    }

    private boolean inBounds(int r, int c, int rows, int cols) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    private boolean isWalkable(int r, int c, int rows, int cols, boolean[][] visited, Coordinate target) {
        if (!inBounds(r, c, rows, cols)) return false;
        if (visited[r][c]) return false;
        if (r == target.getX() && c == target.getY()) return true;
        return map[r][c] == FLOOR_CHAR;
    }

    private Coordinate findChar(char ch) {
        for (int r = 0; r < map.length; r++)
            for (int c = 0; c < map[r].length; c++)
                if (map[r][c] == ch) return new Coordinate(r, c);
        return null;
    }

    /** Clears '+' marks from a previous pathfinding run. */
    public void clearPath() {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == PATH_CHAR) map[r][c] = FLOOR_CHAR;
            }
        }
    }

    /** Print the current map state. */
    public void printMap() {
        System.out.println("\n=== LIBRARY MAP WITH PATH ===");
        for (char[] row : map) {
            System.out.println(new String(row));
        }
    }
}
