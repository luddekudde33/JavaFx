package com.example.demo2.controller;


import com.example.demo2.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class ProfileController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label categoryLabel;
    @FXML private Label addressLabel;

//    private final UserService userService = new UserService();

//    @FXML
//    public void initialize() {
//        // Hämta inloggad användare
//        User u = userService.getCurrentUser();
//        if (u != null) {
//            nameLabel.setText(u.getFullName());
//            emailLabel.setText(u.getEmail());
//            phoneLabel.setText(u.getPhoneNr());
//            categoryLabel.setText(u.getCategoryName());
//            addressLabel.setText(u.getAddressLine() + ", " + u.getZipCode() + " " + u.getCountry());
//        }
//    }

    @FXML
    private void onEditProfile(ActionEvent e) {
        NavigationService.navigateTo("editProfileView.fxml");
    }

//    @FXML
//    private void onLogout(ActionEvent e) {
//        userService.logout();
//        NavigationService.navigateTo("startView.fxml");
//    }

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
}
