package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        determineSentiment("file.txt", readCsv("positive_words.csv"), readCsv("negative_words.csv"));
    }

    public static int countWordFrequency(String filePath, String word) {
        try {
            StringBuilder lineBuilder = new StringBuilder();
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                lineBuilder.append(scanner.nextLine().toLowerCase().trim()).append(" ");
            }

            String line = lineBuilder.toString();

            String delims = "\\W+";
            String[] tokens = line.split(delims);


            int count = 0;

            for (String item :
                    tokens) {
                if (item.equals(word.toLowerCase())) {
                    count++;
                }
            }
            scanner.close();

            return count;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return 0;
        }
    }

    public static void determineSentiment(String documentPath, String[] positiveWords, String[] negativeWords) {
        int positiveWordCount = 0;
        int negativeWordCount = 0;

        for (String positiveWord : positiveWords) {
            positiveWordCount += countWordFrequency(documentPath, positiveWord);
        }

        for (String negativeWord : negativeWords) {
            negativeWordCount += countWordFrequency(documentPath, negativeWord);
        }

        if (positiveWordCount > negativeWordCount) {
            System.out.println("Positive sentiment");
        } else if (positiveWordCount < negativeWordCount) {
            System.out.println("Negative sentiment");
        } else {
            System.out.println("Neutral");
        }
    }

    public static String[] readCsv(String filePath) {

        List<String> content = new java.util.ArrayList<>(List.of());

        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                content.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content.toArray(new String[0]);
    }
}
