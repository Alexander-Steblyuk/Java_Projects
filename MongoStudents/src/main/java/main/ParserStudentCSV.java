package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserStudentCSV {
    private static final File studentCSV = new File("src/main/resources/mongo.csv");

    private static final Pattern SEPARATOR_PATTERN = Pattern.compile(",|\".+\"");
    private static final Pattern COURSES_LIST_PATTERN = Pattern.compile("\".+\"");
    private static final Pattern COURSE_NAME_PATTERN = Pattern.compile("[^,|\"]+");

    private static final int NAME_INDEX = 0;
    private static final int AGE_INDEX = 1;

    public List<Student> parse() {
        List<Student> students = new ArrayList<>();

        try (Scanner scanner = new Scanner(studentCSV)) {
            while (scanner.hasNextLine()) {
                String currLine = scanner.nextLine();
                String[] params = getNameAndAge(currLine);

                students.add(new Student(params[NAME_INDEX], Integer.parseInt(params[AGE_INDEX]), getCourses(currLine)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }

    private String[] getNameAndAge(String line) {
        return line.split(SEPARATOR_PATTERN.pattern());
    }

    private List<String> getCourses(String line) {
        List<String> courses = new ArrayList<>();
        Matcher coursesListMatcher = COURSES_LIST_PATTERN.matcher(line);

        if (coursesListMatcher.find()) {
            Matcher courseMatcher = COURSE_NAME_PATTERN.matcher(coursesListMatcher.group());

            while (courseMatcher.find()) {
                courses.add(courseMatcher.group());
            }
        }

        return courses;
    }
}
