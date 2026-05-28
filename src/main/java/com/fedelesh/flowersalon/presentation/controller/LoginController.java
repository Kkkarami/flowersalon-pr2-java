package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.presentation.MainApplication;
import com.fedelesh.flowersalon.presentation.viewmodel.LoginViewModel;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

  private final LoginViewModel viewModel;

  @FXML private TextField emailField;

  @FXML private PasswordField passwordField;

  @FXML private Label errorLabel;

  @Inject
  public LoginController(LoginViewModel viewModel) {
    this.viewModel = viewModel;
  }

  private void openMain() {
    MainApplication.sceneManager.switchSceneMaximized("/view/main-view.fxml", "Main");
  }

  @FXML
  private void onLoginClick() {
    errorLabel.setText("");

    try {
      boolean success = viewModel.login(emailField.getText(), passwordField.getText());

      if (success) {
        openMain();
      }
    } catch (Exception e) {
      errorLabel.setText("Неправильний логін або пароль");
    }
  }

  @FXML
  private void onOpenSignUpClick() {
    MainApplication.sceneManager.switchSceneMaximized("/view/signup-view.fxml", "Sign Up");
  }
}
