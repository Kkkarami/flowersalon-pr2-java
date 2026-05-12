package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.contract.SignUpService;
import com.fedelesh.flowersalon.presentation.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

  public static AuthService authService;

  @FXML private TextField emailField;

  @FXML private PasswordField passwordField;

  @FXML private Label errorLabel;

  private LoginViewModel viewModel;
  private Stage stage;
  private SignUpService signUpService;

  public void setViewModel(LoginViewModel viewModel) {
    this.viewModel = viewModel;
    authService = viewModel.authService();
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void setSignUpService(SignUpService signUpService) {
    this.signUpService = signUpService;
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
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signup-view.fxml"));
      Parent root = loader.load();

      SignUpController controller = loader.getController();
      controller.setStage(stage);
      controller.setSignUpService(signUpService);

      stage.getScene().setRoot(root);
      stage.sizeToScene();
      stage.centerOnScreen();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
