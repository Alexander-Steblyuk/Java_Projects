package org.example.entity.subscription;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SubscriptionPK implements Serializable {
    @Column(name = "student_id")
    private int studId;
    @Column(name = "course_id")
    private int courseID;

    public SubscriptionPK() {
    }

    public SubscriptionPK(int studId, int courseId) {
        this.studId = studId;
        this.courseID = courseId;
    }
}
