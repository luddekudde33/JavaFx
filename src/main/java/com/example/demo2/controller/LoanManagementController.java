package com.example.demo2.controller;

import com.example.demo2.Model.Loan;
import com.example.demo2.Dao.LoanDao;
import com.example.demo2.NavigationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class LoanManagementController implements Initializable {
    @FXML private TableView<Loan> loanTable;
    @FXML private TableColumn<Loan,Integer> colLoanId;
    @FXML private TableColumn<Loan,Integer> colBookId;
    @FXML private TableColumn<Loan,Integer> colMovieId;
    @FXML private TableColumn<Loan,Integer> colUserId;
    @FXML private TableColumn<Loan,Integer> colStatus;
    @FXML private TableColumn<Loan,LocalDate> colLoanDate;
    @FXML private TableColumn<Loan,LocalDate> colDueDate;

    private final LoanDao loanDao = new LoanDao();
    private final ObservableList<Loan> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Koppla kolumner mot Loan-fält
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colMovieId.setCellValueFactory(new PropertyValueFactory<>("movieId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colLoanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        // Sätt listan till tabellen och ladda försenade lån
        loanTable.setItems(data);
        loadOverdueLoans();
    }

    /** Hämtar bara lån med status=1 och dueDate < idag */
    private void loadOverdueLoans() {
        List<Loan> list = loanDao.getOverdueLoans();
        data.setAll(list);
    }

    public void goBack(javafx.event.ActionEvent actionEvent) {
        NavigationService.goBack();
    }
}

