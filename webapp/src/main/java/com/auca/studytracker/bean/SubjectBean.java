package com.auca.studytracker.bean;

import com.auca.studytracker.dao.SubjectDAO;
import com.auca.studytracker.model.Subject;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "subjectBean")
@ViewScoped
public class SubjectBean implements Serializable {

    private final SubjectDAO subjectDAO = new SubjectDAO();

    private List<Subject> subjects;
    private Subject selectedSubject = new Subject();
    private boolean editMode = false;

    public void init() {
        if (subjects == null) {
            subjects = subjectDAO.findAll();
        }
    }

    public List<Subject> getSubjects() {
        if (subjects == null) {
            subjects = subjectDAO.findAll();
        }
        return subjects;
    }

    public Subject getSelectedSubject() { return selectedSubject; }
    public void setSelectedSubject(Subject selectedSubject) { this.selectedSubject = selectedSubject; }

    public boolean isEditMode() { return editMode; }

    public String prepareCreate() {
        this.selectedSubject = new Subject();
        this.editMode = false;
        return "subject-form?faces-redirect=true";
    }

    public String prepareEdit(Subject subject) {
        this.selectedSubject = subject;
        this.editMode = true;
        return "subject-form?faces-redirect=true";
    }

    public String save() {
        if (editMode) {
            subjectDAO.update(selectedSubject);
            addMessage("Subject updated successfully.");
        } else {
            subjectDAO.save(selectedSubject);
            addMessage("Subject created successfully.");
        }
        subjects = null; // force reload
        return "subject-list?faces-redirect=true";
    }

    public String delete(Subject subject) {
        subjectDAO.delete(subject.getId());
        subjects = null;
        addMessage("Subject deleted.");
        return "subject-list?faces-redirect=true";
    }

    public String cancel() {
        return "subject-list?faces-redirect=true";
    }

    private void addMessage(String text) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, text, null));
    }
}
