package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;
import org.bson.types.MaxKey;

import java.util.List;

public class NoSQLQuery {
    private static final String dbName = "University";
    private static final String collectionName = "students";

    private MongoDBSource mongoDBSource;

    public NoSQLQuery(MongoDBSource mongoDBSource) {
        this.mongoDBSource = mongoDBSource;
    }

    public long getStudentsCount() {
        return mongoDBSource.getMongoClient().getDatabase(dbName).getCollection(collectionName).countDocuments();
    }

    public long getStudentsCountFortyOlder() {
        return mongoDBSource.getMongoClient().getDatabase(dbName).getCollection(collectionName)
                .countDocuments(Filters.gt("age", 40));
    }

    public String getOldestStudentName() {
        return mongoDBSource.getMongoClient().getDatabase(dbName).getCollection(collectionName)
                .find().sort(Sorts.ascending("age")).limit(1).first().get("name").toString();
    }

    public List<String> getOldestStudentCoursesList() {
        return mongoDBSource.getMongoClient().getDatabase(dbName).getCollection(collectionName)
                .find().sort(Sorts.ascending("age")).limit(1).first().getList("courses", String.class);
    }
}
