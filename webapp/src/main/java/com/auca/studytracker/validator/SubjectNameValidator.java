package com.auca.studytracker.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

/**
 * Custom validator ensuring a Subject name only contains letters, numbers, spaces
 * and basic punctuation (blocks stray symbols that Bean Validation's @Size alone would allow).
 */
@FacesValidator("subjectNameValidator")
public class SubjectNameValidator implements Validator<String> {

    private static final Pattern ALLOWED = Pattern.compile("^[A-Za-z0-9 &/\\-()]+$");

    @Override
    public void validate(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return; // required/@NotBlank already cover empty values
        }
        if (!ALLOWED.matcher(value).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Subject name may only contain letters, numbers, spaces and & / - ( )", null));
        }
    }
}
