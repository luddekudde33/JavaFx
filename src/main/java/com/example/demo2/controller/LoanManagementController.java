package com.example.demo2.controller;

import com.example.demo2.Model.Loan;
import com.example.demo2.Dao.LoanDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LoanManagementController {
    @FXML private TableView<Loan> loanTable;
    @FXML private TableColumn<Loan, Integer> colLoanId;
    @FXML private TableColumn<Loan, Integer> colCopyId;
    @FXML private TableColumn<Loan, Integer> colUserId;
    @FXML private TableColumn<Loan, Integer> colStatus;
    @FXML private TableColumn<Loan, LocalDate> colLoanDate;
    @FXML private TableColumn<Loan, LocalDate> colDueDate;


    private final LoanDao loanDao = new LoanDao();
    private final ObservableList<Loan> data = FXCollections.observableArrayList();

    @FXML public void initialize() {
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colCopyId.setCellValueFactory(new PropertyValueFactory<>("copyId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colLoanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        loanTable.setItems(data);
        loadLoans();
    }

    private void loadLoans() {
        List<Loan> list = loanDao.getOverdueLoans();
        data.setAll(list);
    }

    @FXML private void onLoanItem(ActionEvent e) {
        Dialog<Loan> dlg = new Dialog<>();
        dlg.setTitle("Skapa lån");
        ButtonType create = new ButtonType("Låna", ButtonBar.ButtonData.OK_DONE);
        dlg.getDialogPane().getButtonTypes().addAll(create, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        TextField copyIdF = new TextField(); copyIdF.setPromptText("Objekt-ID");
        TextField userIdF = new TextField(); userIdF.setPromptText("Användar-ID");
        DatePicker duePicker = new DatePicker(LocalDate.now().plusWeeks(2));
        grid.addRow(0, new Label("Objekt-ID:"), copyIdF);
        grid.addRow(1, new Label("Användar-ID:"), userIdF);
        grid.addRow(2, new Label("Återlämning senast:"), duePicker);
        dlg.getDialogPane().setContent(grid);

        dlg.setResultConverter(btn -> {
            if (btn == create) {
                Loan loan = new Loan();
                try {
                    loan.setCopyId(Integer.parseInt(copyIdF.getText()));
                } catch (NumberFormatException ex) {
                    loan.setCopyId(0);
                }
                try {
                    loan.setUserId(Integer.parseInt(userIdF.getText()));
                } catch (NumberFormatException ex) {
                    loan.setUserId(0);
                }
                loan.setLoanDate(LocalDate.now());
                loan.setDueDate(duePicker.getValue());
                loan.setStatus(1);
                return loan;
            }
            return null;
        });

        Optional<Loan> res = dlg.showAndWait();
        res.ifPresent(loan -> {
            if (loanDao.addLoan(loan)) loadLoans();
            else new Alert(Alert.AlertType.ERROR, "Kunde inte skapa lån.").showAndWait();
        });
    }

    @FXML private void onReturnItem(ActionEvent e) {
        Loan sel = loanTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Välj ett lån först.").showAndWait();
            return;
        }
        boolean ok = loanDao.returnLoan(sel.getLoanId(), LocalDate.now());
        if (ok) loadLoans(); else new Alert(Alert.AlertType.ERROR, "Kunde inte markera återlämnad.").showAndWait();
    }
}