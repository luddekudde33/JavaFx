
package com.example.demo2.controller;

import com.example.demo2.Model.Loan;
import com.example.demo2.Model.User;
import com.example.demo2.Dao.LoanDao;
import com.example.demo2.NavigationService;
import com.example.demo2.service.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class ProfileController {
    @FXML private TableView<Loan> profileLoanTable;
    @FXML private TableColumn<Loan, Integer> colProfileLoanId;
//    @FXML private TableColumn<Loan, Integer> colProfileCopyId;
    @FXML private TableColumn<Loan,Integer> colProfileBookId;
    @FXML private TableColumn<Loan,Integer> colProfileMovieId;
    @FXML private TableColumn<Loan, Integer> colProfileStatus;
    @FXML private TableColumn<Loan, LocalDate> colProfileLoanDate;
    @FXML private TableColumn<Loan, LocalDate> colProfileDueDate;

    private final LoanDao loanDao = new LoanDao();
    private final ObservableList<Loan> loanData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colProfileLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
//        colProfileCopyId.setCellValueFactory(new PropertyValueFactory<>("copyId"));
        colProfileBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colProfileMovieId.setCellValueFactory(new PropertyValueFactory<>("movieId"));
        colProfileStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProfileLoanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        colProfileDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        profileLoanTable.setItems(loanData);
        loadUserLoans();
    }

    private void loadUserLoans() {
        User currentUser = Session.getCurrentUser();
        if (currentUser == null) {
            System.out.println("Ingen inloggad användare.");
            return;
        }
        int currentUserId = currentUser.getUserId();
        List<Loan> list = loanDao.getLoansByUser(currentUserId);
        loanData.setAll(list);
    }



    public void onGoToStart(javafx.event.ActionEvent actionEvent) {
        NavigationService.navigateTo("StartView.fxml");
    }

    public void onGoToBookSearch(javafx.event.ActionEvent actionEvent) {
        NavigationService.navigateTo("BookSearchView.fxml");
    }

    public void onGoToMovieSearch(javafx.event.ActionEvent actionEvent) {
        NavigationService.navigateTo("MovieSearchView.fxml");
    }

    public void onGoToAbout(javafx.event.ActionEvent actionEvent) {
        NavigationService.navigateTo("AboutView.fxml");
    }
}
