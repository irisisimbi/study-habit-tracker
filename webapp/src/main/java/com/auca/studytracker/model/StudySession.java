package com.auca.studytracker.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * StudySession is the second entity on which full CRUD is implemented (JSF + Hibernate).
 * Represents a single logged study session for a given Subject: a date and a duration (hours).
 */
@Entity
@Table(name = "study_sessions")
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Session date is required")
    @Column(nullable = false)
    private LocalDate sessionDate;

    @NotNull(message = "Duration is required")
    @DecimalMin(value = "0.25", message = "Duration must be at least 0.25 hours")
    @DecimalMax(value = "12.0", message = "Duration cannot exceed 12 hours in a single session")
    @Column(nullable = false)
    private Double durationHours;

    @Column(length = 200)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    public StudySession() {}

    public StudySession(LocalDate sessionDate, Double durationHours, String notes, Subject subject) {
        this.sessionDate = sessionDate;
        this.durationHours = durationHours;
        this.notes = notes;
        this.subject = subject;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }

    public Double getDurationHours() { return durationHours; }
    public void setDurationHours(Double durationHours) { this.durationHours = durationHours; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
}
