package com.example.demo2.controller;

import com.example.demo2.Model.User;
import com.example.demo2.NavigationService;
import com.example.demo2.Dao.UserDao;
import com.example.demo2.service.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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

        User user = userDao.authenticate(email, pwd);
        if (user != null) {
            // Spara inloggad användare i sessionen
            Session.setCurrentUser(user);

            // Markera navigeringsservicen som inloggad
            NavigationService.setUserLoggedIn(true);
            switchToMainView();
        } else {
            alert("Felaktig e-postadress eller lösenord.");
        }
    }

    @FXML private void onRegister()      { try {
        NavigationService.navigateTo("registerView.fxml");

    } catch (Exception e) {
        e.printStackTrace();
        alert("Kunde inte ladda registreringssidan.");
    } }
    @FXML private void onForgotPassword(){ alert("Skicka återställnings‑länk …"); }

    private void switchToMainView() {
        try {
            NavigationService.navigateTo("LoginCheckView.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            alert("Kunde inte ladda nästa vy.");
        }
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    public void onGoToBookSearch(ActionEvent actionEvent) {
        NavigationService.navigateTo("searchView.fxml");
    }

    public void onGoToRegister(ActionEvent actionEvent) {
        NavigationService.navigateTo("registerView.fxml");
    }

    public void onGoToStart(ActionEvent actionEvent) {
        NavigationService.navigateTo("startView.fxml");
    }

    public void onGoToMovieSearch(ActionEvent actionEvent) {
        NavigationService.navigateTo("MovieSearchView.fxml");
    }

    public void onGoToAbout(ActionEvent actionEvent) {
        NavigationService.navigateTo("AboutView.fxml");
    }
}
