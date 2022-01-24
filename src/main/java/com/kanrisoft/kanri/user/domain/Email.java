package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.shared.Constants;
import com.kanrisoft.kanri.shared.ValueValidator;
import com.kanrisoft.kanri.user.exception.InvalidEmailException;

class EmailValidator implements ValueValidator<String> {

    public static EmailValidator getInstance() {
        return new EmailValidator();
    }

    public boolean isValid(String email) {
        if (email == null || email.isBlank()) return false;
        return Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches();
    }
}

public record Email(String email) {
    private static final ValueValidator<String> validator = EmailValidator.getInstance();

    public Email {
        if (!validator.isValid(email)) throw new InvalidEmailException(email);
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public String value() {
        return email;
    }
}
