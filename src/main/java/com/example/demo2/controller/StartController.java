package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartController {

    @FXML
    private void onGoToLogin(ActionEvent event) {
        NavigationService.navigateTo("loginView.fxml");
    }

    @FXML
    private void onGoToRegister(ActionEvent event) {
        NavigationService.navigateTo("registerView.fxml");
    }

    @FXML
    private void onGoToSearch(ActionEvent event) {
        NavigationService.navigateTo("SearchView.fxml");
    }

    @FXML
    private void onGoToProfile(ActionEvent event) {
        NavigationService.navigateTo("profileView.fxml"); // skapa om du saknar
    }

    @FXML
    private void onAbout(ActionEvent event) {
        // Visa om-dialog, t.ex. via Alert
    }
}
