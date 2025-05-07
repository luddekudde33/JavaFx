package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

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
            Parent root = FXMLLoader.load(
                    getClass().getResource("loginView.fxml")
            );
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Kunde inte ladda inloggningssidan.")
                    .showAndWait();
        }
    }
}
