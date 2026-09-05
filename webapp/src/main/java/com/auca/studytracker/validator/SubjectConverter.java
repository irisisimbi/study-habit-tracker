package com.auca.studytracker.validator;

import com.auca.studytracker.dao.SubjectDAO;
import com.auca.studytracker.model.Subject;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Converts between the Subject entity and its id string so a Subject can be
 * bound directly to an <h:selectOneMenu> value.
 */
@FacesConverter(value = "subjectConverter", forClass = Subject.class)
public class SubjectConverter implements Converter<Subject> {

    private final SubjectDAO subjectDAO = new SubjectDAO();

    @Override
    public Subject getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return subjectDAO.findById(Long.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Subject value) {
        if (value == null || value.getId() == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
