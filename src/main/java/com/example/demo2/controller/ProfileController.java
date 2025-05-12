package com.example.demo2.controller;


import com.example.demo2.NavigationService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class ProfileController {

    @FXML
    private void onGoToLogin(ActionEvent e) {
        NavigationService.navigateTo("LoginView.fxml");
    }

    @FXML
    private void onGoToRegister(ActionEvent e) {
        NavigationService.navigateTo("RegisterView.fxml");
    }

    public void onGoToBookSearch(ActionEvent actionEvent) {
        NavigationService.navigateTo("BookSearchView.fxml");
    }

    public void onGoToMovieSearch(ActionEvent actionEvent) {
        NavigationService.navigateTo("MovieSearchView.fxml");
    }

    public void onGoToAbout(ActionEvent actionEvent) {
        NavigationService.navigateTo("AboutView.fxml");
    }

    public void onGoToStart(ActionEvent actionEvent) {
        NavigationService.navigateTo("StartView.fxml");
    }
}
