package com.auca.studytracker.dao;

import com.auca.studytracker.model.StudySession;
import com.auca.studytracker.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudySessionDAO {

    public void save(StudySession session_) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(session_);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void update(StudySession session_) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(session_);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            StudySession studySession = session.get(StudySession.class, id);
            if (studySession != null) {
                session.remove(studySession);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public StudySession findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(StudySession.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<StudySession> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM StudySession ORDER BY sessionDate DESC").list();
        }
    }

    /** Total hours logged per subject name - powers the progress view. */
    @SuppressWarnings("unchecked")
    public List<Object[]> totalHoursPerSubject() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "SELECT s.subject.name, SUM(s.durationHours) FROM StudySession s GROUP BY s.subject.name"
            ).list();
        }
    }
}
