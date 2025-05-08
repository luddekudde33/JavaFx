package com.example.demo2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApp extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        NavigationService.init(stage);
        // byt ut mot frontView.fxml eller loginView.fxml
        NavigationService.navigateTo("frontView.fxml");
        stage.setTitle("Bibliotekssök");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
