package com.fedelesh.flowersalon.presentation;

import atlantafx.base.theme.NordLight;
import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.contract.SignUpService;
import com.fedelesh.flowersalon.application.impl.AuthServiceImpl;
import com.fedelesh.flowersalon.application.impl.SignUpServiceImpl;
import com.fedelesh.flowersalon.infrastructure.email.EmailSender;
import com.fedelesh.flowersalon.infrastructure.email.SmtpEmailSender;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.UserRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.UserRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.security.BcryptPasswordHasher;
import com.fedelesh.flowersalon.infrastructure.security.PasswordHasher;
import com.fedelesh.flowersalon.presentation.controller.LoginController;
import com.fedelesh.flowersalon.presentation.viewmodel.LoginViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

  private AuthService authService;
  private SignUpService signUpService;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception {

    Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
    UserRepository userRepository = new UserRepositoryImpl();
    PasswordHasher passwordHasher = new BcryptPasswordHasher();
    EmailSender emailSender = new SmtpEmailSender();

    authService = new AuthServiceImpl(userRepository, passwordHasher);
    signUpService = new SignUpServiceImpl(userRepository, passwordHasher, emailSender);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));

    Parent root = loader.load();

    LoginController controller = loader.getController();
    controller.setStage(stage);
    controller.setViewModel(new LoginViewModel(authService));
    controller.setSignUpService(signUpService);

    stage.setScene(new Scene(root));
    stage.setTitle("Login");
    stage.show();
  }
}
