package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class MovieSearchController {

    @FXML
    private void onGoToLogin(ActionEvent event) {
        try {
            NavigationService.navigateTo("LoginView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Kunde inte ladda inloggningssidan.")
                    .showAndWait();
        }
    }
    @FXML
    private void onGoToStart(ActionEvent event) {
        NavigationService.navigateTo("StartView.fxml");
    }

    public void onGoToRegister(ActionEvent actionEvent) {
        NavigationService.navigateTo("RegisterView.fxml");
    }

    public void onGoToBookSearch(ActionEvent actionEvent) {
        NavigationService.navigateTo("BookSearchView.fxml");
    }

    public void onGoToAbout(ActionEvent actionEvent) {
        NavigationService.navigateTo("AboutView.fxml");
    }

    public void onGotoProfile(ActionEvent actionEvent) {
        NavigationService.navigateTo("ProfileView.fxml");
    }
}