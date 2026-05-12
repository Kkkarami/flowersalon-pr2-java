package com.fedelesh.flowersalon.application.validation;

import com.fedelesh.flowersalon.application.exception.MultiFieldValidationException;
import java.util.regex.Pattern;

public class ValidationHelper {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+380\\d{9}$");

    private final MultiFieldValidationException validationException;

    public ValidationHelper() {
        this.validationException = new MultiFieldValidationException();
    }

    public ValidationHelper notEmpty(String fieldName, String value) {

        if (value == null || value.trim().isEmpty()) {

            validationException.addFieldError(fieldName, "Поле обов'язкове");
        }

        return this;
    }

    public ValidationHelper validEmail(String fieldName, String email) {

        if (email != null && !EMAIL_PATTERN.matcher(email).matches()) {

            validationException.addFieldError(fieldName, "Email має містити @ та домен");
        }

        return this;
    }

    public ValidationHelper validPhone(String fieldName, String phone) {

        if (phone != null && !PHONE_PATTERN.matcher(phone).matches()) {

            validationException.addFieldError(fieldName, "Формат: +380XXXXXXXXX");
        }

        return this;
    }

    public ValidationHelper minLength(String fieldName, String value, int minLength) {

        if (value != null && value.trim().length() < minLength) {

            validationException.addFieldError(fieldName, "Мінімум " + minLength + " символи");
        }

        return this;
    }

    public ValidationHelper maxLength(String fieldName, String value, int maxLength) {

        if (value != null && value.trim().length() > maxLength) {

            validationException.addFieldError(fieldName, "Максимум " + maxLength + " символів");
        }

        return this;
    }

    public ValidationHelper addErrorIf(boolean condition, String fieldName, String message) {

        if (condition) {

            validationException.addFieldError(fieldName, message);
        }

        return this;
    }

    public void throwIfHasErrors() {
        validationException.throwIfHasErrors();
    }
}
