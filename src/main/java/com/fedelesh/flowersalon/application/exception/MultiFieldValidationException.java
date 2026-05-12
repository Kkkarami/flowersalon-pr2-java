package com.fedelesh.flowersalon.application.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiFieldValidationException extends RuntimeException {

    private final Map<String, List<String>> fieldErrors;

    public MultiFieldValidationException() {
        super("Validation failed");
        this.fieldErrors = new HashMap<>();
    }

    public MultiFieldValidationException(String message, Map<String, List<String>> fieldErrors) {
        super(message);
        this.fieldErrors = new HashMap<>(fieldErrors);
    }

    public void addFieldError(String fieldName, String errorMessage) {
        fieldErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
    }

    public boolean hasFieldErrors(String fieldName) {
        return fieldErrors.containsKey(fieldName) && !fieldErrors.get(fieldName).isEmpty();
    }

    public List<String> getFieldErrors(String fieldName) {
        return fieldErrors.getOrDefault(fieldName, Collections.emptyList());
    }

    public Map<String, List<String>> getAllFieldErrors() {
        return Collections.unmodifiableMap(fieldErrors);
    }

    public boolean hasErrors() {
        return !fieldErrors.isEmpty();
    }

    public void throwIfHasErrors() {
        if (hasErrors()) {
            throw new MultiFieldValidationException(buildMessage(), fieldErrors);
        }
    }

    private String buildMessage() {

        StringBuilder sb = new StringBuilder();

        fieldErrors.forEach((field, errors) -> {
            sb.append(field).append(": ").append(String.join(", ", errors)).append("\n");
        });

        return sb.toString();
    }
}
