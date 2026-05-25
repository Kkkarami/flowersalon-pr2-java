package com.fedelesh.flowersalon.presentation;

import com.fedelesh.flowersalon.infrastructure.persistence.util.DatabaseInitializer;
import javafx.application.Application;

public class Launcher {

    public static void main(String[] args) {
        DatabaseInitializer.init();
        Application.launch(MainApplication.class, args);
    }
}
