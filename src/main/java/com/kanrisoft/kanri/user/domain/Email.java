package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.shared.Constants;
import com.kanrisoft.kanri.user.exception.InvalidEmailException;

import java.util.Objects;

class EmailValidator {

    public static EmailValidator getInstance() {
        return new EmailValidator();
    }

    boolean isValid(String email) {
        if (email == null || email.isBlank()) return false;
        return Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches();
    }
}

public final class Email {
    private static final EmailValidator validator = EmailValidator.getInstance();
    private final String email;

    private Email(String email) {
        if (!validator.isValid(email)) throw new InvalidEmailException(email);
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public String getValue() {
        return this.email;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Email)) return false;
        final Email other = (Email) o;
        return Objects.equals(this.email, other.email);
    }

    public int hashCode() {
        final int PRIME = 59;
        final String $email = getValue();
        return PRIME + $email.hashCode();
    }

    public String toString() {
        return "Email(email=" + getValue() + ")";
    }
}
