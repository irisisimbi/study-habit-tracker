package com.auca.studytracker.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Custom validator (validation type #2 required by the assignment, alongside standard
 * JSF validators and Hibernate/JSR-303 Bean Validation).
 *
 * Business rule that a plain "required"/"length" tag cannot express:
 * a study session logged for "today" cannot claim more than 8 hours of study,
 * since that is unrealistic for a single day and likely a data-entry mistake.
 */
@FacesValidator("studySessionDurationValidator")
public class StudySessionDurationValidator implements Validator<Double> {

    private static final double MAX_REALISTIC_SINGLE_SESSION_HOURS = 8.0;

    @Override
    public void validate(FacesContext context, UIComponent component, Double value) {
        if (value == null) {
            return; // let @NotNull / required handle empty values
        }
        if (value <= 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Duration must be a positive number", null));
        }
        if (value > MAX_REALISTIC_SINGLE_SESSION_HOURS) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "A single session over " + (int) MAX_REALISTIC_SINGLE_SESSION_HOURS +
                            " hours looks like a mistake. Please split it into multiple sessions.", null));
        }
    }
}
