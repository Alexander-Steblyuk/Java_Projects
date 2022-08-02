package responeces;

import java.time.LocalDateTime;
import java.util.Date;

public class Task {
    private int id;
    private String name;
    private LocalDateTime term;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = LocalDateTime.parse(term);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
