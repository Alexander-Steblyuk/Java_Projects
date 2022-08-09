package mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import main.ParserStudentCSV;
import main.Student;
import org.bson.Document;

import java.util.List;

public class MongoDBSource {
    private static final String HOST = "127.0.0.1";
    private static final int PORT_NUMBER = 27017;

    private static final String DB_NAME = "University";
    private static final String COLLECTION_NAME = "students";

    private static final MongoDBSource instance = new MongoDBSource();

    public static MongoDBSource instance() {
        return instance;
    }

    public MongoClient getMongoClient() {
        return new MongoClient(HOST, PORT_NUMBER);
    }

    private MongoDBSource() {
        try (MongoClient mongoClient = new MongoClient(HOST, PORT_NUMBER)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);

            MongoCollection<Document> studentCollection = database.getCollection(COLLECTION_NAME);

            List<Student> students = new ParserStudentCSV().parse();

            int idCounter = 1;

            for (Student student : students) {
                Document dbObject = new Document("_id", idCounter++)
                        .append("name", student.getName())
                        .append("age", student.getAge())
                        .append("courses", student.getCourses());

                try {
                    studentCollection.insertOne(dbObject);
                } catch (MongoWriteException e) {
                    System.err.println("This line is already exists!!!");
                }

            }
        } catch (MongoClientException e) {
            e.printStackTrace();
        }
    }

}
