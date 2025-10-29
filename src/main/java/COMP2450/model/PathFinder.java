package COMP2450.model;


public class PathFinder {


    public static boolean solveMap(Map map, Position start, Position goal) {
        Stack stack = new Stack();
        Position current = start;
        map.markPushed(current);
        boolean flag = true;
        boolean output = false;

        while (flag) {
            map.printMaze(current);

            if (map.isExit(current,goal)) {
                output = true;
                flag = false;
            }

            map.markVisited(current);

            Position[] nbrs = map.getNeighbors(current);
            for (Position n : nbrs) {
                if (!map.isVisited(n) && !map.isPushed(n)) {
                    stack.push(n);
                    map.markPushed(n);
                }
            }

            if (stack.isEmpty())
                flag = false;

            current = stack.pop();
        }
        return output;
    }
}