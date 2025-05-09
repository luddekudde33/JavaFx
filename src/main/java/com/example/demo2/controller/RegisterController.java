package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import com.example.demo2.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;
    @FXML private Button registerButton;
    @FXML private Button loginButton;

    private final UserDao userDao = new UserDao();

    @FXML
    private void onRegister(ActionEvent event) {
        String name    = nameField.getText().trim();
        String email   = emailField.getText().trim();
        String pwd     = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (name.isEmpty() || email.isEmpty() || pwd.isEmpty() || confirm.isEmpty()) {
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            messageLabel.setText("Alla fält måste vara ifyllda.");
            return;
        }

        if (!pwd.equals(confirm)) {
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            messageLabel.setText("Lösenorden matchar inte.");
            return;
        }

        boolean ok = userDao.register(name, email, pwd);
        if (ok) {
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            messageLabel.setText("Registreringen lyckades!");
            registerButton.setDisable(true);
            loginButton.setVisible(true);
        } else {
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            messageLabel.setText("Något gick fel – kunde inte registrera.");
        }
    }

    @FXML
    private void onGoToLogin(ActionEvent event) {
        try {
            NavigationService.navigateTo("loginView.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Kunde inte ladda inloggningssidan.")
                    .showAndWait();
        }
    }
    @FXML
    private void onGoToStart(ActionEvent event) {
        NavigationService.navigateTo("startView.fxml");
    }
    @FXML
    private void goBack() {
        NavigationService.goBack();
    }

}
