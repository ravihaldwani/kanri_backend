package com.kanrisoft.kanri.shared;

import java.util.regex.Pattern;

public class Constants {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_REGEX = Pattern.compile("^[0-9]{10}$");
}
