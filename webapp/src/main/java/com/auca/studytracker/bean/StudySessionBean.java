package com.auca.studytracker.bean;

import com.auca.studytracker.dao.StudySessionDAO;
import com.auca.studytracker.dao.SubjectDAO;
import com.auca.studytracker.model.StudySession;
import com.auca.studytracker.model.Subject;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@ManagedBean(name = "studySessionBean")
@ViewScoped
public class StudySessionBean implements Serializable {

    private final StudySessionDAO studySessionDAO = new StudySessionDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    private List<StudySession> studySessions;
    private StudySession selectedSession = new StudySession();
    private boolean editMode = false;

    public List<StudySession> getStudySessions() {
        if (studySessions == null) {
            studySessions = studySessionDAO.findAll();
        }
        return studySessions;
    }

    public List<Subject> getAvailableSubjects() {
        return subjectDAO.findAll();
    }

    public StudySession getSelectedSession() { return selectedSession; }
    public void setSelectedSession(StudySession selectedSession) { this.selectedSession = selectedSession; }

    public boolean isEditMode() { return editMode; }

    public String prepareCreate() {
        this.selectedSession = new StudySession();
        this.selectedSession.setSessionDate(LocalDate.now());
        this.editMode = false;
        return "session-form?faces-redirect=true";
    }

    public String prepareEdit(StudySession session) {
        this.selectedSession = session;
        this.editMode = true;
        return "session-form?faces-redirect=true";
    }

    public String save() {
        if (editMode) {
            studySessionDAO.update(selectedSession);
            addMessage("Study session updated successfully.");
        } else {
            studySessionDAO.save(selectedSession);
            addMessage("Study session logged successfully.");
        }
        studySessions = null;
        return "session-list?faces-redirect=true";
    }

    public String delete(StudySession session) {
        studySessionDAO.delete(session.getId());
        studySessions = null;
        addMessage("Study session deleted.");
        return "session-list?faces-redirect=true";
    }

    public String cancel() {
        return "session-list?faces-redirect=true";
    }

    public List<Object[]> getTotalHoursPerSubject() {
        return studySessionDAO.totalHoursPerSubject();
    }

    private void addMessage(String text) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, text, null));
    }
}
