package com.example.demo2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApp extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        NavigationService.init(stage);
        NavigationService.navigateTo("frontView.fxml");
        stage.setTitle("Bibliotekssök");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
