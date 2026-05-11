package com.fedelesh.flowersalon;

import com.fedelesh.flowersalon.infrastructure.persistence.util.DatabaseInitializer;

public class Main {

    public static void main(String[] args) {

        DatabaseInitializer.init();

    }
}