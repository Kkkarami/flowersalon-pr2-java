package com.fedelesh.flowersalon.presentation.navigation;

import com.google.inject.Injector;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public record SceneManager(Stage stage, Injector injector) {

    private FXMLLoader createLoader(String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setControllerFactory(injector::getInstance);
        return loader;
    }

    private Scene loadScene(String fxml) {
        try {
            FXMLLoader loader = createLoader(fxml);
            return new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void switchScene(String fxml, String title) {
        Scene scene = loadScene(fxml);

        stage.setScene(scene);
        stage.setTitle(title);

        stage.show();
        stage.centerOnScreen();
        stage.toFront();
        stage.requestFocus();
    }

    public void switchSceneMaximized(String fxml, String title) {
        Scene scene = loadScene(fxml);

        stage.setScene(scene);
        stage.setTitle(title);

        stage.show();
        stage.centerOnScreen();
        stage.toFront();
        stage.requestFocus();
    }

    public <T> T switchSceneGetControllerMaximized(String fxml, String title) {
        try {
            FXMLLoader loader = createLoader(fxml);

            Scene scene = new Scene(loader.load());

            stage.setScene(scene);
            stage.setTitle(title);

            stage.show();
            stage.centerOnScreen();
            stage.toFront();
            stage.requestFocus();

            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}