package com.example.demo2.controller;

import com.example.demo2.Dao.StaffDao;
import com.example.demo2.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StaffLoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    private final StaffDao staffDao = new StaffDao();

    @FXML
    private void onStaffLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String pwd   = passwordField.getText();

        if (email.isEmpty() || pwd.isEmpty()) {
            showAlert("Fyll i både e-postadress och lösenord.");
            return;
        }

        if (staffDao.authenticate(email, pwd)) {
            NavigationService.setStaffLoggedIn(true);
            NavigationService.navigateTo("MainStaffLogginView.fxml");
        } else {
            showAlert("Felaktig e-post eller lösenord för personal.");
        }
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }

    // Valfri navigering till startsidan
    @FXML private void goBack(ActionEvent e) {
        NavigationService.goBack();
    }
    @FXML private void onAbout(ActionEvent e) {
        NavigationService.navigateTo("aboutView.fxml");
    }
}


