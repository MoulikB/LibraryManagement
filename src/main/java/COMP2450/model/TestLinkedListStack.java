package COMP2450.model;

/**
 * Minimal pathfinding test.
 * - Uses ONLY LinkedListStack<Coordinate> and Coordinate.
 * - DFS search with parent reconstruction.
 * - Small 5×5 map for easy verification.
 */
public class TestLinkedListStack {

    private static final char PATH_CHAR   = '+';
    private static final char FLOOR_CHAR  = ' ';
    private static final char WALL_CHAR   = '#';
    private static final char START_CHAR  = 'K';
    private static final char TARGET_CHAR = 'T';

    private static final char[][] map = {
            "#####".toCharArray(),
            "#K  #".toCharArray(),
            "# # T".toCharArray(),

            "#   #".toCharArray(),
            "#####".toCharArray()
    };

    public static void main(String[] args) {
        Coordinate start  = findChar(START_CHAR);
        Coordinate target = findChar(TARGET_CHAR);

        if (start == null || target == null) {
            System.out.println("❌ Start or target not found.");
            printMap();
            return;
        }

        boolean found = dfsAndMarkPath(start, target);

        if (found) {
            System.out.println("✅ Path found!");
        } else {
            System.out.println("❌ No path found.");
        }

        printMap();
    }

    /**
     * DFS with parent reconstruction.
     */
    private static boolean dfsAndMarkPath(Coordinate start, Coordinate target) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Coordinate[][] parent = new Coordinate[rows][cols];

        LinkedListStack<Coordinate> stack = new LinkedListStack<>();
        stack.push(start);
        boolean found = false;

        int[][] dirs = { {1,0}, {-1,0}, {0,1}, {0,-1} };

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

            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];
                if (isWalkable(nr, nc, rows, cols, visited)) {
                    if (parent[nr][nc] == null) parent[nr][nc] = cur;
                    stack.push(new Coordinate(nr, nc));
                }
            }
        }

        if (!found) return false;

        // reconstruct true path
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
            int rr = k.getX();
            int cc = k.getY();
            if (map[rr][cc] == FLOOR_CHAR) map[rr][cc] = PATH_CHAR;
        }

        return true;
    }

    private static boolean inBounds(int r, int c, int rows, int cols) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    private static boolean isWalkable(int r, int c, int rows, int cols, boolean[][] visited) {
        if (!inBounds(r, c, rows, cols)) return false;
        if (visited[r][c]) return false;
        char tile = map[r][c];
        return tile == FLOOR_CHAR || tile == TARGET_CHAR;
    }

    private static Coordinate findChar(char ch) {
        for (int r = 0; r < map.length; r++)
            for (int c = 0; c < map[r].length; c++)
                if (map[r][c] == ch) return new Coordinate(r, c);
        return null;
    }

    private static void printMap() {
        System.out.println("\n=== SMALL MAP WITH PATH ===");
        for (char[] row : map) System.out.println(new String(row));
    }
}
