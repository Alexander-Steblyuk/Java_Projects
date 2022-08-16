package org.example.entity.student;

import jakarta.persistence.*;
import org.example.entity.course.Course;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    @Column(name = "registration_date")
    private LocalDateTime registerDate;

    @ManyToMany
    @JoinTable(name = "Subscriptions",
    joinColumns = {@JoinColumn(name = "student_id")},
    inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private List<Course> courses;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
