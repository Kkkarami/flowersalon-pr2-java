package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.contract.SignUpService;
import com.fedelesh.flowersalon.application.exception.MultiFieldValidationException;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.presentation.MainApplication;
import com.google.inject.Inject;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

public class SignUpController {

  private final SignUpService signUpService;
  private final AuthService authService;

  @FXML private TextField firstNameField;

  @FXML private TextField lastNameField;

  @FXML private TextField emailField;

  @FXML private TextField phoneField;

  @FXML private PasswordField passwordField;

  @FXML private TextField codeField;

  @FXML private Label firstNameErrorLabel;

  @FXML private Label lastNameErrorLabel;

  @FXML private Label emailErrorLabel;

  @FXML private Label phoneErrorLabel;

  @FXML private Label passwordErrorLabel;

  @FXML private Label codeErrorLabel;

  @FXML private Label statusLabel;

  @FXML private ProgressIndicator progressIndicator;

  @Inject
  public SignUpController(SignUpService signUpService, AuthService authService) {
    this.signUpService = signUpService;
    this.authService = authService;
  }

  @FXML
  private void onSendCodeClick() {
    clearValidationLabels();

    statusLabel.setText("Надсилання коду...");

    if (progressIndicator != null) {
      progressIndicator.setVisible(true);
    }

    Task<String> task =
        new Task<>() {
          @Override
          protected String call() {
            return signUpService.generateVerificationCode(emailField.getText());
          }
        };

    task.setOnSucceeded(
        event -> {
          statusLabel.setText("Код надіслано на пошту");

          if (progressIndicator != null) {
            progressIndicator.setVisible(false);
          }
        });

    task.setOnFailed(
        event -> {
          statusLabel.setText("");

          if (progressIndicator != null) {
            progressIndicator.setVisible(false);
          }

          Throwable exception = task.getException();

          if (exception instanceof MultiFieldValidationException validationException) {
            showValidationErrors(validationException);
            return;
          }

          codeErrorLabel.setText(exception.getMessage());
        });

    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }

  @FXML
  private void onRegisterClick() {
    clearValidationLabels();

    try {
      signUpService.register(
          firstNameField.getText(),
          lastNameField.getText(),
          emailField.getText(),
          phoneField.getText(),
          passwordField.getText(),
          Role.CLIENT,
          codeField.getText());

      authService.authenticate(emailField.getText(), passwordField.getText());

      MainApplication.sceneManager.switchSceneMaximized("/view/main-view.fxml", "Main");
    } catch (MultiFieldValidationException e) {
      showValidationErrors(e);
    } catch (Exception e) {
      codeErrorLabel.setText(e.getMessage());
    }
  }

  @FXML
  private void onBackToLoginClick() {
    MainApplication.sceneManager.switchSceneMaximized("/view/login-view.fxml", "Login");
  }

  private void clearValidationLabels() {
    firstNameErrorLabel.setText("");
    lastNameErrorLabel.setText("");
    emailErrorLabel.setText("");
    phoneErrorLabel.setText("");
    passwordErrorLabel.setText("");
    codeErrorLabel.setText("");

    if (statusLabel != null) {
      statusLabel.setText("");
    }
  }

  private void showValidationErrors(MultiFieldValidationException exception) {
    clearValidationLabels();

    exception
        .getAllFieldErrors()
        .forEach(
            (field, errors) -> {
              String message = String.join(", ", errors);

              switch (field) {
                case "firstName" -> firstNameErrorLabel.setText(message);
                case "lastName" -> lastNameErrorLabel.setText(message);
                case "email" -> emailErrorLabel.setText(message);
                case "phone" -> phoneErrorLabel.setText(message);
                case "password" -> passwordErrorLabel.setText(message);
                case "inputCode", "code" -> codeErrorLabel.setText(message);
              }
            });
  }
}
