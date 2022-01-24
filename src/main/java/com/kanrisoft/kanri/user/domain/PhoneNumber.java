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

public record PhoneNumber(String phone) {
    private static final ValueValidator<String> phoneValidator = PhoneNumberValidator.getInstance();

    public PhoneNumber {
        if (!phoneValidator.isValid(phone)) throw new IllegalArgumentException("Invalid phone");
    }

    public static PhoneNumber of(String phone) {
        return new PhoneNumber(phone);
    }

    public String value() {
        return this.phone;
    }

}
