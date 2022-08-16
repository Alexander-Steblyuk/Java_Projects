package com.efimchick.ifmo.collections.countwords;

import java.text.Collator;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Words {
    private static final int MIN_WORD_FREQUENCY = 10;

    private static final Locale RU_LOCALE = Locale.forLanguageTag("ru");
    private static final Pattern WORD_PATTERN = Pattern.compile("[\\p{IsLatin}[а-яА-Я]]{4,}");

    private static final String ELEMENT_SEPARATOR = " - ";
    private static final String END_OF_LINE = "\n";

    private List<String> wordsList;

    public Words() {
        wordsList = new ArrayList<>();
    }

    public String countWords(List<String> lines) {
        Map<String, Integer> wordsStatistic = new HashMap<>();
        StringBuilder outStringBuilder = new StringBuilder();

        wordsList = divideLinesToWords(lines);

        for (String curWord : wordsList) {
            if (!wordsStatistic.containsKey(curWord)) {
                wordsStatistic.put(curWord, 1);
            } else {
                int count = wordsStatistic.get(curWord);
                wordsStatistic.put(curWord, count + 1);
            }

        }

        wordsStatistic = filterByFrequency(wordsStatistic);

        List<Map.Entry<String, Integer>> sortedWords = sortWordsByAmount(wordsStatistic);

        for (Map.Entry<String, Integer> entry : sortedWords) {
            outStringBuilder
                    .append(entry.getKey())
                    .append(ELEMENT_SEPARATOR)
                    .append(entry.getValue())
                    .append(END_OF_LINE);
        }

        outStringBuilder.deleteCharAt(outStringBuilder.lastIndexOf(END_OF_LINE));

        return outStringBuilder.toString();
    }

    private List<String> divideLinesToWords(List<String> lines) {
        Matcher matcher;
        List<String> matchedWords = new ArrayList<>();

        for (String curString : lines) {
            matcher = WORD_PATTERN.matcher(curString);

            while (matcher.find()) {
                matchedWords.add(matcher.group().toLowerCase(Locale.ROOT));
            }
        }

        return matchedWords;
    }

    private Map<String, Integer> filterByFrequency(Map<String, Integer> srcMap) {
        Map<String, Integer> filteredMap = new HashMap<>();

        for (Map.Entry<String, Integer> entry : srcMap.entrySet()) {
            if (entry.getValue() >= MIN_WORD_FREQUENCY) {
                filteredMap.put(entry.getKey(), entry.getValue());
            }
        }

        return filteredMap;
    }

    private List<Map.Entry<String, Integer>> sortWordsByAmount(Map<String, Integer> wordsFrequencies) {
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordsFrequencies.entrySet());

        sortedWords.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                int result = -Integer.compare(entry1.getValue(), entry2.getValue());

                if (result == 0) {
                    result = Collator.getInstance(RU_LOCALE).compare(entry1.getKey(), entry2.getKey());
                }

                return result;
            }
        });

        return sortedWords;
    }
}
