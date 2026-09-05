package com.auca.studytracker.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Subject is one of the two entities on which full CRUD is implemented (JSF + Hibernate).
 * Example: "Mathematics", "Data Structures", "Software Quality Assurance".
 */
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Subject name is required")
    @Size(min = 2, max = 60, message = "Subject name must be between 2 and 60 characters")
    @Column(nullable = false, length = 60)
    private String name;

    @Size(max = 200, message = "Description cannot exceed 200 characters")
    @Column(length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudySession> studySessions = new HashSet<>();

    public Subject() {}

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Set<StudySession> getStudySessions() { return studySessions; }
    public void setStudySessions(Set<StudySession> studySessions) { this.studySessions = studySessions; }
}
