package chase;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

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

    private static final int row[] = {-1, 0, 0, 1};
    private static final int col[] = {0, -1, 1, 0};

    private static boolean isValid(int mat[][], boolean visited[][], int row, int col) {
        return (row >= 0) && (row < mat.length) && (col >= 0) && (col < mat[0].length)
                && mat[row][col] == 1 && !visited[row][col];
    }

    public static Direction BFS(IMap map, int i_start, int j_start, int x, int y) {

        boolean[][] mat = mapToBoolArray(map);

        boolean[][] discovered = new boolean[mat.length][mat.length];

        Queue<Node> queue = new ArrayDeque<>();

        discovered[j_start][i_start] = true;
        queue.add(new Node(i_start, j_start, null));

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            // Go breath-first into each direction
            for (Direction dir : Direction.values()) {
                int newX = node.x + dir.getDx();
                int newY = node.y + dir.getDy();
                Direction newDir = node.initialDir == null ? dir : node.initialDir;

                // Mouse found?
                if (newX == x && newY == y) {
                    return newDir;
                }

                // Is there a path in the direction (= is it a free field in the labyrinth)?
                // And has that field not yet been discovered?
                if (newX < mat.length && newY < mat.length && newY >= 0 && newX >= 0) {
                    if (!mat[newY][newX] && !discovered[newY][newX]) {
                        // "Discover" and enqueue that field
                        discovered[newY][newX] = true;
                        queue.add(new Node(newX, newY, newDir));
                    }
                }
            }
        }
        throw new IllegalStateException("No path found");
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