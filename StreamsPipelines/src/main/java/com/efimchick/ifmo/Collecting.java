package com.efimchick.ifmo;

import com.efimchick.ifmo.util.CourseResult;
import com.efimchick.ifmo.util.MarkCharacter;
import com.efimchick.ifmo.util.Person;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collecting {
    private static final int EVEN_DIVIDER = 2;
    private static final String EMPTY_STRING = "";

    public int sum(IntStream stream) {
        return stream.sum();
    }

    public int production(IntStream stream) {
        return stream.reduce((x, y) -> x * y)
                .orElse(0);
    }

    public int oddSum(IntStream stream) {
        return stream.filter(x -> x % EVEN_DIVIDER != 0)
                .sum();
    }

    public Map<Integer, Integer> sumByRemainder(int divisor, IntStream stream) {
        return stream.boxed()
                .collect(Collectors.toMap(k -> k % divisor, v -> v, Integer::sum));
    }

    public Map<Person, Double> totalScores(Stream<CourseResult> stream) {
        double countOfTasks;
        CourseResult[] courseResults;

        courseResults = stream.toArray(CourseResult[]::new);

        countOfTasks = Stream.of(courseResults)
                .flatMap(e -> e.getTaskResults()
                        .keySet()
                        .stream())
                .distinct()
                .count();

        return Stream.of(courseResults)
                .collect(Collectors.toMap(
                        CourseResult::getPerson,
                        v -> v.getTaskResults()
                                .values()
                                .stream()
                                .mapToInt(Integer::intValue)
                                .sum() / countOfTasks));

    }

    public double averageTotalScore(Stream<CourseResult> stream) {
        double countOfTasks;
        double countOfStudents;
        CourseResult[] courseResults;

        courseResults = stream.toArray(CourseResult[]::new);

        countOfTasks = Stream.of(courseResults)
                .flatMap(e -> e.getTaskResults()
                        .keySet()
                        .stream())
                .distinct()
                .count();

        countOfStudents = Stream.of(courseResults).count();

        return Stream.of(courseResults).flatMap(e -> e.getTaskResults()
                        .values()
                        .stream())
                .mapToInt(Integer::intValue)
                .sum() / (countOfTasks * countOfStudents);
    }

    public Map<String, Double> averageScoresPerTask(Stream<CourseResult> stream) {
        double countOfStudents;
        CourseResult[] courseResults;
        Map<String, Integer> scoresPerTask;

        courseResults = stream.toArray(CourseResult[]::new);

        countOfStudents = Stream.of(courseResults).count();

        scoresPerTask = Stream.of(courseResults)
                .flatMap(e -> e.getTaskResults()
                        .entrySet()
                        .stream())
                .map(e -> Map.entry(e.getKey(), e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum));

        return scoresPerTask.entrySet().stream()
                .map(e -> Map.entry(e.getKey(), e.getValue() / countOfStudents))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    public Map<Person, String> defineMarks(Stream<CourseResult> stream) {
        return totalScores(stream).entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey(), getCharOfMark(e.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public String easiestTask(Stream<CourseResult> stream) {
        return averageScoresPerTask(stream).entrySet()
                .stream()
                .min((e1, e2) -> -Double.compare(e1.getValue(), e2.getValue()))
                .orElse(Map.entry(EMPTY_STRING, 0D))
                .getKey();

    }

    public Collector<CourseResult, Object, Object> printableStringCollector() {
        throw new UnsupportedOperationException();
    }

    private String getCharOfMark(double markValue) {
        return MarkCharacter.getMark(markValue);
    }
}
