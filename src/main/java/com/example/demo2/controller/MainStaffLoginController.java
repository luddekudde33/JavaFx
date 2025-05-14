package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import javafx.event.ActionEvent;

public class MainStaffLoginController {
    public void goBack(ActionEvent actionEvent) {
        NavigationService.goBack();
    }

    public void onGoToManeger(ActionEvent actionEvent) {
        NavigationService.navigateTo("ItemManagementView.fxml");
    }
}
