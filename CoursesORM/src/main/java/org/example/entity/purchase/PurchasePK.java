
package org.example.entity.purchase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import org.example.entity.course.Course;
import org.example.entity.student.Student;

@Embeddable
public class PurchasePK {
    @ManyToOne(targetEntity = Student.class, cascade = CascadeType.ALL)
    private Student student;
    @ManyToOne(targetEntity = Student.class, cascade = CascadeType.ALL)
    private Course course;

    public PurchasePK() {
    }

    public PurchasePK(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

}
