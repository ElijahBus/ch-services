package com.comfihealth.security.authentication.registration.user;

import java.util.function.Predicate;

/**
 * Represents a custom validator for email
 *
 * @since 0.1.0
 * @author elijah
 */
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return false;
    }
}
