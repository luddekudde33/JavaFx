package com.example.demo2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.example.demo2.NavigationService;

public class AboutController {

    // Navigeringsmetoder
    @FXML private void onGoToLogin(ActionEvent e) {
        NavigationService.navigateTo("loginView.fxml");
    }
    @FXML private void onGoToRegister(ActionEvent e) {
        NavigationService.navigateTo("registerView.fxml");
    }
    @FXML private void onGoToBookSearch(ActionEvent e) {
        NavigationService.navigateTo("BookSearchView.fxml");
    }
    @FXML private void onGoToMovieSearch(ActionEvent e) {
        NavigationService.navigateTo("MovieSearchView.fxml");
    }
    @FXML private void onGoToProfile(ActionEvent e) {
        NavigationService.navigateTo("profileView.fxml");
    }
}