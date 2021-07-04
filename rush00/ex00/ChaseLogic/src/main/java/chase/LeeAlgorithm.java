package chase;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    STAY(0, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}

class Node {
    int x, y;
    final Direction initialDir;

    Node(int x, int y, Direction initialDir) {
        this.x = x;
        this.y = y;
        this.initialDir = initialDir;
    }
};

public class LeeAlgorithm {

    public static Direction BFS(IMap map, int i_start, int j_start, int x, int y, boolean isPlayer) {

        boolean[][] mat = mapToBoolArray(map);

        boolean[][] discovered = new boolean[mat.length][mat.length];

        Queue<Node> queue = new ArrayDeque<>();

        discovered[j_start][i_start] = true;
        queue.add(new Node(i_start, j_start, null));

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            for (Direction dir : Direction.values()) {
                int newX = node.x + dir.getDx();
                int newY = node.y + dir.getDy();
                Direction newDir = node.initialDir == null ? dir : node.initialDir;

                if (newX == x && newY == y) {
                    return newDir;
                }

                if (newX < mat.length && newY < mat.length && newY >= 0 && newX >= 0) {
                    if (!mat[newY][newX] && !discovered[newY][newX]) {
                        discovered[newY][newX] = true;
                        queue.add(new Node(newX, newY, newDir));
                    }
                }
            }
        }
        if (isPlayer)
            throw new IllegalStateException("No path found");
        return Direction.STAY;
    }

    private static boolean[][] mapToBoolArray(IMap map) {
        int h = map.getSize();
        int w = map.getSize();
        boolean[][] intMap = new boolean[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (map.isEmpty(i, j) || map.isUnit(i, j)) {
                    intMap[i][j] = false;
                }
                if (map.isWall(i, j)) {
                    intMap[i][j] = true;
                }
            }
        }
        return intMap;
    }
}