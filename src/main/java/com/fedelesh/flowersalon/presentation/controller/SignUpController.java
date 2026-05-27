package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.application.contract.SignUpService;
import com.fedelesh.flowersalon.application.exception.MultiFieldValidationException;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.presentation.MainApplication;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    private final SignUpService signUpService;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField codeField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label codeStatusLabel;

    @Inject
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @FXML
    private void onSendCodeClick() {
        errorLabel.setText("");
        codeStatusLabel.setText("Надсилання коду...");

        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                return signUpService.generateVerificationCode(emailField.getText());
            }
        };

        task.setOnSucceeded(event -> {
            codeStatusLabel.setText("Код надіслано на пошту");
        });

        task.setOnFailed(event -> {
            codeStatusLabel.setText("");

            Throwable exception = task.getException();

            if (exception instanceof MultiFieldValidationException validationException) {
                showValidationErrors(validationException);
                return;
            }

            errorLabel.setText(exception.getMessage());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void onRegisterClick() {
        errorLabel.setText("");

        try {
            signUpService.register(
                  firstNameField.getText(),
                  lastNameField.getText(),
                  emailField.getText(),
                  phoneField.getText(),
                  passwordField.getText(),
                  Role.CLIENT,
                  codeField.getText()
            );

            MainApplication.sceneManager.switchSceneMaximized("/view/login-view.fxml", "Login");
        } catch (MultiFieldValidationException e) {
            showValidationErrors(e);
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onBackToLoginClick() {
        MainApplication.sceneManager.switchSceneMaximized("/view/login-view.fxml", "Login");
    }

    private void showValidationErrors(MultiFieldValidationException exception) {
        StringBuilder builder = new StringBuilder();

        exception.getAllFieldErrors().forEach((field, errors) -> {
            builder.append(field)
                  .append(": ")
                  .append(String.join(", ", errors))
                  .append("\n");
        });

        errorLabel.setText(builder.toString());
    }
}