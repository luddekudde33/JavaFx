package com.example.demo2.controller;

import com.example.demo2.Model.Loan;
import com.example.demo2.Model.User;
import com.example.demo2.Dao.LoanDao;
import com.example.demo2.NavigationService;
import com.example.demo2.service.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProfileController {
    @FXML private TableView<Loan> profileLoanTable;
    @FXML private TableColumn<Loan,Integer> colProfileLoanId;
    @FXML private TableColumn<Loan,String>  colProfileBookTitle;
    @FXML private TableColumn<Loan,String>  colProfileMovieTitle;
    @FXML private TableColumn<Loan,Integer> colProfileStatus;
    @FXML private TableColumn<Loan,java.time.LocalDate> colProfileLoanDate;
    @FXML private TableColumn<Loan,java.time.LocalDate> colProfileDueDate;

    @FXML private Button returnBtn;
    @FXML private Button receiptBtn;

    private final LoanDao loanDao = new LoanDao();
    private final ObservableList<Loan> loanData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Kolumner
        colProfileLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colProfileBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colProfileMovieTitle.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        colProfileStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProfileLoanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        colProfileDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        profileLoanTable.setItems(loanData);

        // Ladda användarens aktiva lån
        loadUserLoans();

        // Styr knapparna beroende på om något är markerat
        returnBtn.setDisable(true);
        receiptBtn.setDisable(true);
        profileLoanTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            boolean any = newSel != null;
            returnBtn.setDisable(!any);
            receiptBtn.setDisable(!any);
        });
    }

    private void loadUserLoans() {
        User u = Session.getCurrentUser();
        if (u == null) return;
        List<Loan> lista = loanDao.getLoansByUser(u.getUserId());
        loanData.setAll(lista);
    }

    @FXML
    private void onReturnLoan(ActionEvent e) {
        Loan sel = profileLoanTable.getSelectionModel().getSelectedItem();
        if (sel == null) return; // dubbelcheck
        boolean ok = loanDao.returnLoan(sel.getLoanId());
        if (ok) {
            loadUserLoans();
            new Alert(Alert.AlertType.INFORMATION, "Lån markerat som återlämnat").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Kunde inte återlämna lånet").showAndWait();
        }
    }

    @FXML
    private void onPrintReceipt(ActionEvent e) {
        Loan sel = profileLoanTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        StringBuilder sb = new StringBuilder();
        sb.append("--- Kvitto ---\n");
        sb.append("Lån-ID: ").append(sel.getLoanId()).append("\n");
        if (sel.getBookTitle() != null)  sb.append("Bok: ").append(sel.getBookTitle()).append("\n");
        if (sel.getMovieTitle()!= null)  sb.append("Film: ").append(sel.getMovieTitle()).append("\n");
        sb.append("Lån av användare: ").append(sel.getUserId()).append("\n");
        sb.append("Lånedatum: ").append(sel.getLoanDate()).append("\n");
        sb.append("Åter senast: ").append(sel.getDueDate()).append("\n");
        sb.append("Status: ").append(sel.getStatus()==1 ? "Utlånat" : "Återlämnat");

        TextArea ta = new TextArea(sb.toString());
        ta.setEditable(false);
        ta.setWrapText(true);
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Kvitto");
        dlg.getDialogPane().setContent(ta);
        dlg.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dlg.showAndWait();
    }

    @FXML
    private void onGoToBookSearch(ActionEvent event) {
        NavigationService.navigateTo("BookSearchView.fxml");
    }

    @FXML
    public void onGoToMovieSearch(ActionEvent actionEvent) {
        NavigationService.navigateTo("MovieSearchView.fxml");
    }
    @FXML
    public void onGoToAbout(ActionEvent actionEvent) {
        NavigationService.navigateTo("AboutView.fxml");
    }

    public void onGoToStart(ActionEvent actionEvent) {
        NavigationService.navigateTo("StartView.fxml");
    }
}
