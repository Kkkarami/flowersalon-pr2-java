package com.fedelesh.flowersalon.presentation.util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TableUtils {

    public static <T> TableColumn<T, Object> column(String title, String property) {

        TableColumn<T, Object> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));

        return column;
    }

    public static <T> TableColumn<T, String> imageColumn(String title, String property) {

        TableColumn<T, String> column = new TableColumn<>(title);

        column.setCellValueFactory(new PropertyValueFactory<>(property));

        column.setCellFactory(value -> new TableCell<>() {

            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String path, boolean empty) {

                super.updateItem(path, empty);

                if (empty || path == null || path.isBlank()) {
                    setGraphic(null);
                    return;
                }

                try {

                    Image image = new Image(
                          TableUtils.class.getResourceAsStream(path),
                          55,
                          55,
                          true,
                          true
                    );

                    imageView.setImage(image);
                    imageView.setFitWidth(55);
                    imageView.setFitHeight(55);
                    imageView.setPreserveRatio(true);

                    setGraphic(imageView);

                } catch (Exception e) {

                    setGraphic(null);
                }
            }
        });

        return column;
    }
}