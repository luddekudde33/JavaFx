package com.example.demo2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class NavigationService {
    private static Stage primaryStage;
    private static final Deque<Scene> history = new ArrayDeque<>();

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static void navigateTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationService.class.getResource(fxml)
            );
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            if (primaryStage.getScene() != null) {
                history.push(primaryStage.getScene());
            }
            primaryStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goBack() {
        if (!history.isEmpty()) {
            primaryStage.setScene(history.pop());
        }
    }
}
