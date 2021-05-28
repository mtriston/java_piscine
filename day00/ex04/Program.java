import java.util.Scanner;

public class Program {

    private static final int MAX_UNICODE = 65535;
    private static final int MAX_OCCUR = 999;
    private static final int TABLE_WIDTH = 10;
    private static final int TABLE_HEIGHT = 10;

    public static int[] countAllOccurences(char[] array) {
        int[] occurArray = new int[MAX_UNICODE + 1];

        for (char c : array) {
            occurArray[c] += 1;
            if (occurArray[c] > MAX_OCCUR) {
                System.err.println("The maximum number of character occurrences is overflow.");
                System.exit(-1);
            }
        }
        return occurArray;
    }

    public static void printTable(int[] array) {
        double scale = (double) (array[0] / 100000) / 10;

        if (array[0] == 0) {
            return;
        }

        for (int i = 0; i <= TABLE_HEIGHT; ++i) {
            for (int j = 0; j < TABLE_WIDTH; ++j) {
                if (array[j] == 0) {
                    break;
                }
                double columnSize = (double) (array[j] / 100000) / scale;
                if ((int) columnSize + i == TABLE_HEIGHT) {
                    System.out.printf("%6d", array[j] / 100000);
                } else if (columnSize + i > TABLE_HEIGHT) {
                    System.out.printf("%6c", '#');
                } else {
                    System.out.printf("%6c", ' ');
                }
            }
            System.out.print("\n");
        }
        for (int i = 0; i < TABLE_WIDTH; ++i) {
            if (array[i] != 0) {
                System.out.printf("%6c", array[i] % 100000);
            }
        }
        System.out.print("\n");
    }

    public static int[] getTopOccurrences(int[] occurArray) {
        int[] topOccurrences = new int[TABLE_WIDTH];
        int maxOccur;
        int maxIndex;
        for (int j = 0; j < TABLE_WIDTH; ++j) {
            maxOccur = 0;
            maxIndex = 0;
            for (int i = 0; i <= MAX_UNICODE; ++i) {
                if (maxOccur < occurArray[i]) {
                    maxOccur = occurArray[i];
                    maxIndex = i;
                }
            }
            occurArray[maxIndex] = 0;
            topOccurrences[j] = maxIndex + 100000 * maxOccur;
        }
        return topOccurrences;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }

        char[] inputArray = scanner.nextLine().toCharArray();
        int[] occurArray = countAllOccurences(inputArray);
        int[] topOccurrences = getTopOccurrences(occurArray);

        printTable(topOccurrences);
    }
}