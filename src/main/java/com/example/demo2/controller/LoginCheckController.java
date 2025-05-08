package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import javafx.fxml.FXML;

public class LoginCheckController {
    @FXML
    private void goBack() {
        NavigationService.goBack();
    }

}
