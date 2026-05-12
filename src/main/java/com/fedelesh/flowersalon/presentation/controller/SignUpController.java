package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.application.contract.SignUpService;
import com.fedelesh.flowersalon.application.exception.MultiFieldValidationException;
import com.fedelesh.flowersalon.domain.enums.Role;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

  @FXML private TextField firstNameField;
  @FXML private Label firstNameErrorLabel;

  @FXML private TextField lastNameField;
  @FXML private Label lastNameErrorLabel;

  @FXML private TextField emailField;
  @FXML private Label emailErrorLabel;

  @FXML private TextField phoneField;
  @FXML private Label phoneErrorLabel;

  @FXML private PasswordField passwordField;
  @FXML private Label passwordErrorLabel;

  @FXML private TextField codeField;
  @FXML private Label codeErrorLabel;

  @FXML private Label statusLabel;

  @FXML private ProgressIndicator progressIndicator;

  @FXML private Button sendCodeButton;

  private SignUpService signUpService;
  private Stage stage;

  public void setSignUpService(SignUpService signUpService) {
    this.signUpService = signUpService;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  private void clearErrors() {
    firstNameErrorLabel.setText("");
    lastNameErrorLabel.setText("");
    emailErrorLabel.setText("");
    phoneErrorLabel.setText("");
    passwordErrorLabel.setText("");
    codeErrorLabel.setText("");
    statusLabel.setText("");
  }

  @FXML
  private void onSendCodeClick() {
    clearErrors();
    progressIndicator.setVisible(true);
    sendCodeButton.setDisable(true);

    new Thread(
            () -> {
              try {
                signUpService.generateVerificationCode(emailField.getText());
                Platform.runLater(
                    () -> {
                      progressIndicator.setVisible(false);
                      sendCodeButton.setDisable(false);
                      statusLabel.setText("Код надіслано на пошту");
                    });
              } catch (MultiFieldValidationException e) {
                Platform.runLater(
                    () -> {
                      progressIndicator.setVisible(false);
                      sendCodeButton.setDisable(false);
                      handleValidationException(e);
                    });
              } catch (Exception e) {
                Platform.runLater(
                    () -> {
                      progressIndicator.setVisible(false);
                      sendCodeButton.setDisable(false);
                      statusLabel.setText(e.getMessage());
                    });
              }
            })
        .start();
  }

  private void openMain() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main-view.fxml"));

      Parent root = loader.load();

      stage.getScene().setRoot(root);
      stage.sizeToScene();
      stage.centerOnScreen();
      stage.setMaximized(true);
      stage.setTitle("Main");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void onRegisterClick() {

    clearErrors();

    try {

      signUpService.register(
          firstNameField.getText(),
          lastNameField.getText(),
          emailField.getText(),
          phoneField.getText(),
          passwordField.getText(),
          Role.CLIENT,
          codeField.getText());

      statusLabel.setText("Реєстрація успішна");

      openMain();

    } catch (MultiFieldValidationException e) {
      handleValidationException(e);

    } catch (Exception e) {
      statusLabel.setText(e.getMessage());
    }
  }

  private void handleValidationException(MultiFieldValidationException e) {
    Map<String, List<String>> errors = e.getAllFieldErrors();

    if (errors.containsKey("firstName")) {
      firstNameErrorLabel.setText(errors.get("firstName").get(0));
    }
    if (errors.containsKey("lastName")) {
      lastNameErrorLabel.setText(errors.get("lastName").get(0));
    }
    if (errors.containsKey("email")) {
      emailErrorLabel.setText(errors.get("email").get(0));
    }
    if (errors.containsKey("phone")) {
      phoneErrorLabel.setText(errors.get("phone").get(0));
    }
    if (errors.containsKey("password")) {
      passwordErrorLabel.setText(errors.get("password").get(0));
    }
    if (errors.containsKey("inputCode") || errors.containsKey("code")) {
      String msg =
          errors.containsKey("inputCode")
              ? errors.get("inputCode").get(0)
              : errors.get("code").get(0);
      codeErrorLabel.setText(msg);
    }
  }

  @FXML
  private void onBackToLoginClick() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));
      Parent root = loader.load();
      stage.getScene().setRoot(root);
      stage.sizeToScene();
      stage.centerOnScreen();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
