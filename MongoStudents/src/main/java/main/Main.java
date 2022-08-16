package main;

import mongo.MongoDBSource;
import mongo.NoSQLQuery;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MongoDBSource mongoDBSource = MongoDBSource.instance();
        NoSQLQuery noSQLQuery = new NoSQLQuery(mongoDBSource);

        System.out.println(noSQLQuery.getStudentsCount());
        System.out.println(noSQLQuery.getStudentsCountFortyOlder());
        System.out.println(noSQLQuery.getOldestStudentName());
        System.out.println(Arrays.toString(noSQLQuery.getOldestStudentCoursesList().toArray(String[]::new)));
    }
}
