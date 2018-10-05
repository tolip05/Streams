package streams;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WordsCount {
    private static final String PROJECT_PATH =
            System.getProperty("user.dir") + "/src/resources/";
    private static final String WORDS_FILE_PATH = PROJECT_PATH + "words.txt";
    private static final String TEXT_FILE_PATH = PROJECT_PATH + "text.txt";
    private static final String RESULT_FILE_PATH = PROJECT_PATH + "result.txt";
    public static void main(String[] args) {
        Map<String,Integer> wordsCount = new HashMap<>();
        Map<String,String> wordsOrigin = new HashMap<>();
        try(BufferedReader wordReader =
                    new BufferedReader(new FileReader(WORDS_FILE_PATH));
            BufferedReader textReader =
                    new BufferedReader(new FileReader(TEXT_FILE_PATH));
            BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE_PATH))){

            fiilMap(wordsCount, wordsOrigin, wordReader);

            fiilCount(wordsCount, textReader);

            wordsCount.entrySet()
                    .stream()
                    .sorted((e1,e2) -> e2.getValue()
                            .compareTo(e1.getValue()))
                    .forEach(pair -> System.out.printf("%s - %d%n"
                            ,wordsOrigin.get(pair.getKey()),pair.getValue()));



            wordsCount.entrySet()
                    .stream()
                    .sorted((e1,e2) -> e2.getValue()
                            .compareTo(e1.getValue()))
                    .forEach(pair -> {
                        try {
                            writer.write(String.format("%s - %d",wordsOrigin
                                    .get(pair.getKey()),pair.getValue()));
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fiilCount(Map<String, Integer> wordsCount, BufferedReader textReader) throws IOException {
        String line ="";
        while ((line = textReader.readLine()) != null){
            String[] words = line.split("\\s+");
            for (String word : words) {
                String wordKey = word.toLowerCase();
                if (wordsCount.containsKey(wordKey)){
                    String key = wordKey;
                    Integer count = wordsCount.get(key) + 1;
                    wordsCount.put(key,count);

                }
            }
        }
    }

    private static void fiilMap(Map<String, Integer> wordsCount, Map<String, String> wordsOrigin, BufferedReader wordReader) throws IOException {
        String line ="";
        while ((line = wordReader.readLine()) != null){
            String[] words = line.split("\\s+");
            for (String word : words) {
                String wordKey = word.toLowerCase();
                if (!wordsCount.containsKey(wordKey)){
                    wordsCount.put(wordKey,0);
                    wordsOrigin.put(wordKey,word);
                }
            }
        }
    }
}
