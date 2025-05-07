package com.example.demo2;

import javafx.fxml.FXML;

public class MainController {
    @FXML
    private void goBack() {
        NavigationService.goBack();
    }

}
