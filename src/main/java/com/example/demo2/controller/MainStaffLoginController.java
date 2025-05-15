package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import javafx.event.ActionEvent;

public class MainStaffLoginController {
    public void goBack(ActionEvent actionEvent) {
        NavigationService.goBack();
    }

    public void onGoToMovieManeger(ActionEvent actionEvent) {
        NavigationService.navigateTo("MovieManagementView.fxml");
    }
    public void onGoToManageBooks(ActionEvent actionEvent) {
        NavigationService.navigateTo("BookManagementView.fxml");
    }
}
