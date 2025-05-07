package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo2Application extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
        Parent root = loader.load();          // ← returnerar Parent
        stage.setTitle("Demo‑inloggning");
        stage.setScene(new Scene(root));      // tar storlek från FXML
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
