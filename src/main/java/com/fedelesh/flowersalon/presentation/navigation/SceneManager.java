package com.fedelesh.flowersalon.presentation.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public record SceneManager(Stage stage) {

    private Scene loadScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            return new Scene(loader.load());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchScene(String fxml, String title) {
        Scene scene = loadScene(fxml);
        stage.setScene(scene);
        stage.setTitle(title);
    }

    public void switchSceneMaximized(String fxml, String title) {
        Scene scene = loadScene(fxml);

        stage.setScene(scene);
        stage.setTitle(title);

        stage.setMaximized(false);
        stage.sizeToScene();

        javafx.application.Platform.runLater(() -> stage.setMaximized(true));
    }

    public <T> T switchSceneGetController(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);

            return loader.getController();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T switchSceneGetControllerMaximized(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);

            stage.setMaximized(false);
            stage.sizeToScene();

            T controller = loader.getController();

            javafx.application.Platform.runLater(() -> stage.setMaximized(true));

            return controller;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}