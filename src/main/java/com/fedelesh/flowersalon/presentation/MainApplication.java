package com.fedelesh.flowersalon.presentation;

import atlantafx.base.theme.NordLight;
import com.fedelesh.flowersalon.bootstrap.AppModule;
import com.fedelesh.flowersalon.presentation.navigation.SceneManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

  public static SceneManager sceneManager;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) {
    Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

    Injector injector = Guice.createInjector(new AppModule());

    sceneManager = new SceneManager(stage, injector);
    sceneManager.switchSceneMaximized("/view/login-view.fxml", "Login");
  }
}
