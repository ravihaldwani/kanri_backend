package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.shared.Constants;
import com.kanrisoft.kanri.shared.ValueValidator;

import java.util.Objects;

class PhoneNumberValidator implements ValueValidator<String> {
    static PhoneNumberValidator getInstance() {
        return new PhoneNumberValidator();
    }

    public boolean isValid(String phone) {
        if (phone == null || phone.isBlank()) return false;
        return Constants.VALID_PHONE_REGEX.matcher(phone).matches();
    }
}

public final class PhoneNumber {
    private static final ValueValidator<String> phoneValidator = PhoneNumberValidator.getInstance();
    private final String phone;

    private PhoneNumber(String phone) {
        if (!phoneValidator.isValid(phone)) throw new IllegalArgumentException("Invalid phone");
        this.phone = phone;
    }

    public static PhoneNumber of(String phone) {
        return new PhoneNumber(phone);
    }

    public String getValue() {
        return this.phone;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PhoneNumber)) return false;
        final PhoneNumber other = (PhoneNumber) o;
        return Objects.equals(this.getValue(), other.getValue());
    }

    public int hashCode() {
        final int PRIME = 59;
        final Object $phone = getValue();
        return PRIME + ($phone == null ? 43 : $phone.hashCode());
    }

    public String toString() {
        return "PhoneNumber(phone=" + getValue() + ")";
    }
}
