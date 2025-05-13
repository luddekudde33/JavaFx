package com.example.demo2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class NavigationService {
    private static Stage primaryStage;
    private static final Deque<Scene> history = new ArrayDeque<>();
    private static boolean userLoggedIn = false;
    private static boolean staffLoggedIn = false;

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static void setUserLoggedIn(boolean value)  { userLoggedIn  = value; }
    public static void setStaffLoggedIn(boolean value)  { staffLoggedIn  = value; }

    public static boolean isUserLoggedIn()  { return userLoggedIn;  }
    public static boolean isStaffLoggedIn() { return staffLoggedIn; }
//    kan användas senare med:
//            NavigationService.setLoggedIn(true);
//
//
//    if (NavigationService.isLoggedIn()) {
//        // Tillåt åtkomst
//    } else {
//        // Visa inloggningsvy
//    }

    public static void navigateTo(String fxml) {
        if ("ProfileView.fxml".equals(fxml) && !userLoggedIn) {
            new Alert(Alert.AlertType.WARNING, "Du måste logga in först.").showAndWait();
            fxml = "LoginView.fxml";
        }
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


