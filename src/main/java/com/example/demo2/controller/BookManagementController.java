package com.example.demo2.controller;

import com.example.demo2.Model.Book;
import com.example.demo2.Dao.BookDao;
import com.example.demo2.NavigationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class BookManagementController {
    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, Integer> colId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, String> colBarcode;
    @FXML private TableColumn<Book, String> colLocation;

    private final BookDao bookDao = new BookDao();
    private final ObservableList<Book> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("physicalLocation"));
        bookTable.setItems(data);
        loadBooks();
    }

    private void loadBooks() {
        List<Book> list = bookDao.getAllBooks();
        data.setAll(list);
    }

    @FXML
    private void onAddBook(ActionEvent e) {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Lägg till bok");
        ButtonType addBtn = new ButtonType("Lägg till", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField titleF = new TextField();
        TextField authorF= new TextField();
        TextField bcF    = new TextField();
        TextField locF   = new TextField();
        grid.addRow(0, new Label("Titel:"), titleF);
        grid.addRow(1, new Label("Författare:"), authorF);
        grid.addRow(2, new Label("Streckkod:"), bcF);
        grid.addRow(3, new Label("Plats:"), locF);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> btn == addBtn
                ? new Book(0, titleF.getText(), null, authorF.getText(), null, bcF.getText(), null, locF.getText(), true)
                : null);

        Optional<Book> res = dialog.showAndWait();
        res.ifPresent(book -> {
            if (bookDao.addBook(book)) loadBooks();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej lägga till").showAndWait();
        });
    }

    @FXML
    private void onEditBook(ActionEvent e) {
        Book sel = bookTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Välj en bok först.").showAndWait();
            return;
        }
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Redigera bok");
        ButtonType updBtn = new ButtonType("Spara", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField titleF = new TextField(sel.getTitle());
        TextField authorF= new TextField(sel.getAuthor());
        TextField bcF    = new TextField(sel.getBarcode());
        TextField locF   = new TextField(sel.getPhysicalLocation());
        grid.addRow(0, new Label("Titel:"), titleF);
        grid.addRow(1, new Label("Författare:"), authorF);
        grid.addRow(2, new Label("Streckkod:"), bcF);
        grid.addRow(3, new Label("Plats:"), locF);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == updBtn) {
                sel.setTitle(titleF.getText());
                sel.setAuthor(authorF.getText());
                sel.setBarcode(bcF.getText());
                sel.setPhysicalLocation(locF.getText());
                return sel;
            }
            return null;
        });

        Optional<Book> res = dialog.showAndWait();
        res.ifPresent(book -> {
            if (bookDao.updateBook(book)) loadBooks();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej uppdatera").showAndWait();
        });
    }

    @FXML
    private void onDeleteBook(ActionEvent e) {
        Book sel = bookTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Välj en bok att ta bort.").showAndWait();
            return;
        }
        Alert c = new Alert(Alert.AlertType.CONFIRMATION,
                "Ta bort '"+sel.getTitle()+"'?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> r = c.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.YES) {
            if (bookDao.deleteBook(sel.getBookId())) loadBooks();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej ta bort").showAndWait();
        }
    }

    public void goBack(ActionEvent actionEvent) {
        NavigationService.goBack();
    }
}
