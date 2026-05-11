package com.fedelesh.flowersalon.presentation.util;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableUtils {

    public static <T, V> TableColumn<T, V> column(
          String title,
          String property
    ) {

        TableColumn<T, V> column =
              new TableColumn<>(title);

        column.setCellValueFactory(
              new PropertyValueFactory<>(property)
        );

        return column;
    }
}