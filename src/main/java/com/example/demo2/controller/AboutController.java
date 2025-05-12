package com.example.demo2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.example.demo2.NavigationService;

public class AboutController {


    @FXML private void onGoToLogin(ActionEvent e) {
        NavigationService.navigateTo("LoginView.fxml");
    }
    @FXML private void onGoToRegister(ActionEvent e) {
        NavigationService.navigateTo("RegisterView.fxml");
    }
    @FXML private void onGoToBookSearch(ActionEvent e) {
        NavigationService.navigateTo("BookSearchView.fxml");
    }
    @FXML private void onGoToMovieSearch(ActionEvent e) {
        NavigationService.navigateTo("MovieSearchView.fxml");
    }
    @FXML private void onGoToProfile(ActionEvent e) {
        NavigationService.navigateTo("ProfileView.fxml");
    }

    public void onGoToStart(ActionEvent actionEvent) {
        NavigationService.navigateTo("StartView.fxml");
    }
}