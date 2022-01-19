package com.kanrisoft.kanri.user.domain;

import java.util.Objects;

public final class PhoneNumber {
    private final String phone;

    private PhoneNumber(String phone) {
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
