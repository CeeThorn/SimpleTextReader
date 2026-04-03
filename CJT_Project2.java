
import java.io.*;
import java.util.*;

public class CJT_Project2 {

    public static List<String> preprocessText(String text, Set<String> stopwords) {
        List<String> words = new ArrayList<>();
        String[] tokens = text.toLowerCase().split("\\W+");

        for (String word : tokens) {
            if (!stopwords.contains(word) && !word.isEmpty()) {
                words.add(word);
            }
        }
        return words;
    }

    public static Map<String, Integer> countFrequencies(List<String> words) {
        Map<String, Integer> freqMap = new HashMap<>();

        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }
        return freqMap;
    }

    public static List<Map.Entry<String, Integer>> getTopWords(Map<String, Integer> freqMap, int N) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(freqMap.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        return list.subList(0, Math.min(N, list.size()));
    }

    public static double calculateRatio(List<Map.Entry<String, Integer>> topWords, int totalWords) {
        int sum = 0;
        for (Map.Entry<String, Integer> entry : topWords) {
            sum += entry.getValue();
        }
        return (double) sum / totalWords;
    }

    public static int countPunctuation(String text) {
        int count = 0;
        for (char ch : text.toCharArray()) {
            if (!Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch)) {
                count++;
            }
        }
        return count;
    }



public static void main(String[] args) {
    try {
       
        StringBuilder textBuilder = new StringBuilder();
        Scanner textScanner = new Scanner(new File("alice29.txt"));

        while (textScanner.hasNextLine()) {
            textBuilder.append(textScanner.nextLine()).append(" ");
        }
        textScanner.close();

        String text = textBuilder.toString();

       
        Set<String> stopwords = new HashSet<>();
        Scanner stopScanner = new Scanner(new File("stopwords.txt"));

        while (stopScanner.hasNext()) {
            stopwords.add(stopScanner.next().toLowerCase());
        }
        stopScanner.close();

       
        List<String> processed = preprocessText(text, stopwords);
        Map<String, Integer> freq = countFrequencies(processed);
        List<Map.Entry<String, Integer>> top = getTopWords(freq, 10);

        double ratio = calculateRatio(top, processed.size());

        
        System.out.println("Total words AFTER preprocessing: " + processed.size());
        System.out.println("Top 10 Words: " + top);
        System.out.println("Ratio: " + ratio);
        System.out.println("Punctuation Count: " + countPunctuation(text));

    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + e.getMessage());
    }
}

}