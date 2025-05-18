package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartController {

    @FXML
    private void onGoToLogin(ActionEvent event) {
        NavigationService.navigateTo("LoginView.fxml");
    }

    @FXML
    private void onGoToRegister(ActionEvent event) {
        NavigationService.navigateTo("registerView.fxml");
    }

    @FXML
    private void onGoToBookSearch(ActionEvent event) {
        NavigationService.navigateTo("BookSearchView.fxml");
    }

    @FXML
    private void onGoToProfile(ActionEvent event) {
        NavigationService.navigateTo("ProfileView.fxml");
    }
    @FXML
    public void onGoToMovieSearch(ActionEvent actionEvent) {
        NavigationService.navigateTo("MovieSearchView.fxml");
    }
    @FXML
    public void onGoToAbout(ActionEvent actionEvent) {
        NavigationService.navigateTo("AboutView.fxml");
    }
    @FXML
    public void onGoToStaffLoggin(ActionEvent actionEvent) {
        NavigationService.navigateTo("StaffLoginView.fxml");
    }

}
