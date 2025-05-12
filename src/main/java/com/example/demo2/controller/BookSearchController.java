package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class BookSearchController {

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
        NavigationService.navigateTo("startView.fxml");
    }


    public void onGoToMovieSearch(ActionEvent actionEvent) {
        NavigationService.navigateTo("movieSearchView.fxml");
    }
}
