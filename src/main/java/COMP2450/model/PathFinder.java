package COMP2450.model;

public class PathFinder {

    /**
     * Finds a path from start to goal on the map.
     * This version uses only arrays and loops.
     */
    public static void findPath(Map map, Position start, Position goal) {
        char[][] grid = map.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Position[][] previous = new Position[rows][cols];
        Position[] queue = new Position[rows * cols];
        int front = 0;
        int back = 0;

        queue[back] = start;
        back++;
        visited[start.row][start.col] = true;

        int[][] moves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        boolean found = false;

        while (front < back && !found) {
            Position current = queue[front];
            front++;

            for (int i = 0; i < 4; i++) {
                int newRow = current.row + moves[i][0];
                int newCol = current.col + moves[i][1];

                if (isValid(grid, newRow, newCol, visited)) {
                    Position next = new Position(newRow, newCol);
                    visited[newRow][newCol] = true;
                    previous[newRow][newCol] = current;
                    queue[back] = next;
                    back++;

                    if (next.equals(goal)) {
                        found = true;
                        break;
                    }
                }
            }
        }

        if (found) {
            // trace the path backward
            Position current = goal;
            while (!current.equals(start)) {
                if (grid[current.row][current.col] == ' ') {
                    grid[current.row][current.col] = '.';
                }
                current = previous[current.row][current.col];
            }
            map.printMap();
        } else {
            System.out.println("No path found.");
        }
    }

    private static boolean isValid(char[][] grid, int r, int c, boolean[][] visited) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length) return false;
        if (visited[r][c]) return false;

        char ch = grid[r][c];
        return ch == ' ' || ch == 'D' || ch == '~';
    }
}