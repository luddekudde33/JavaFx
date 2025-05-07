package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;        // lägg till
import javafx.scene.Scene;         // lägg till
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final UserDao userDao = new UserDao();

    @FXML
    private void onLogin() {
        String email = emailField.getText().trim();
        String pwd   = passwordField.getText();

        if (email.isEmpty() || pwd.isEmpty()) {
            alert("Fyll i både e‑postadress och lösenord.");
            return;
        }

        if (userDao.authenticate(email, pwd)) {
            switchToMainView();
        } else {
            alert("Felaktig e‑postadress eller lösenord.");
        }
    }

    @FXML private void onRegister()      { try {
        Parent root = new FXMLLoader(
                getClass().getResource("registerView.fxml"))
                .load();
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (Exception e) {
        e.printStackTrace();
        alert("Kunde inte ladda registreringssidan.");
    } }
    @FXML private void onForgotPassword(){ alert("Skicka återställnings‑länk …"); }

    private void switchToMainView() {
        try {
            Parent root = new FXMLLoader(
                    getClass().getResource("MainView.fxml")).load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            alert("Kunde inte ladda nästa vy.");
        }
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}
