package com.fedelesh.flowersalon.presentation.navigation;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage stage;

    private Injector injector;

    @Inject
    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    public void switchScene(
          String fxml,
          String title
    ) {

        try {

            FXMLLoader loader =
                  new FXMLLoader(
                        getClass().getResource(fxml)
                  );

            loader.setControllerFactory(
                  injector::getInstance
            );

            Scene scene =
                  new Scene(loader.load());

            stage.setScene(scene);

            stage.setTitle(title);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}