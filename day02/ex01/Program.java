import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

public class Program {
	
	private static TreeSet<String> dictionary = new TreeSet<String>();

	private static double checkSimilarity(TreeMap<String, Integer> a, TreeMap<String, Integer> b) {
		double numerator = 0;
		double denominatorA = 0;
		double denominatorB = 0;

		for (String word : dictionary) {
			Integer aValue = a.get(word);
			Integer bValue = b.get(word);
			aValue = aValue == null ? 0 : aValue;
			bValue = bValue == null ? 0 : bValue;
			numerator += aValue * bValue;
			denominatorA += aValue * aValue;
			denominatorB += bValue * bValue;
		}

		return numerator / (Math.sqrt(denominatorA) * Math.sqrt(denominatorB));
	}

	private static TreeMap<String, Integer> parseFile(String file) {

		TreeMap<String, Integer> wordsFrequency = new TreeMap<String, Integer>();

		try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {

			while (fileReader.ready()) {

				String line = fileReader.readLine();
				String []words = line.replaceAll("[^A-za-z ]", "").split("\\s+");

				for (String word : words) {

					dictionary.add(word);

					if (wordsFrequency.containsKey(word)) {
						wordsFrequency.put(word, wordsFrequency.get(word) + 1);
					} else {
						wordsFrequency.put(word, 1);
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return wordsFrequency;
	}

	public static void main(String[] args) {
		
		if (args.length != 2) {
			System.err.println("There must be two arguments!");
			System.exit(-1);
		}

		TreeMap<String, Integer> firstFileWords = parseFile(args[0]);
		TreeMap<String, Integer> secondFileWords = parseFile(args[1]);

		double similarity = checkSimilarity(firstFileWords, secondFileWords);

		System.out.printf("Similarity = %.2f\n", similarity);
	}
}
