package com.example.demo2.controller;

import com.example.demo2.NavigationService;
import com.example.demo2.ItemDao;
import com.example.demo2.Item;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class ItemManagementController {

    @FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, Integer> colId;
    @FXML private TableColumn<Item, String> colTitle;
    @FXML private TableColumn<Item, String> colCategory;
    @FXML private TableColumn<Item, String> colAuthor;
    @FXML private TableColumn<Item, String> colPublisher;
    @FXML private TableColumn<Item, String> colBarcode;
    @FXML private TableColumn<Item, String> colClassification;
    @FXML private TableColumn<Item, String> colLocation;

    private final ItemDao itemDao = new ItemDao();
    private final ObservableList<Item> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("authorOrArtist"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        colClassification.setCellValueFactory(new PropertyValueFactory<>("classification"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("physicalLocation"));
        itemTable.setItems(data);
        loadItems();
    }

    private void loadItems() {
        List<Item> list = itemDao.getAllItems();
        data.setAll(list);
    }

    @FXML
    private void onAddItem(ActionEvent e) {
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Lägg till objekt");
        ButtonType addBtn = new ButtonType("Lägg till", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField titleF = new TextField();
        TextField catF   = new TextField();
        TextField authF  = new TextField();
        TextField pubF   = new TextField();
        TextField bcF    = new TextField();
        TextField clsF   = new TextField();
        TextField locF   = new TextField();
        grid.addRow(0, new Label("Titel:"), titleF);
        grid.addRow(1, new Label("Kategori:"), catF);
        grid.addRow(2, new Label("Författare/Artist:"), authF);
        grid.addRow(3, new Label("Förlag:"), pubF);
        grid.addRow(4, new Label("Streckkod:"), bcF);
        grid.addRow(5, new Label("Classificering:"), clsF);
        grid.addRow(6, new Label("Plats:"), locF);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == addBtn) {
                return new Item(0,
                        titleF.getText(), catF.getText(), authF.getText(),
                        pubF.getText(), bcF.getText(), clsF.getText(), locF.getText());
            }
            return null;
        });

        Optional<Item> res = dialog.showAndWait();
        res.ifPresent(item -> {
            if (itemDao.addItem(item)) loadItems();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej lägga till").showAndWait();
        });
    }

    @FXML
    private void onEditItem(ActionEvent e) {
        Item sel = itemTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Välj ett objekt först.").showAndWait();
            return;
        }
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Redigera objekt");
        ButtonType updBtn = new ButtonType("Spara", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField titleF = new TextField(sel.getTitle());
        TextField catF   = new TextField(sel.getCategory());
        TextField authF  = new TextField(sel.getAuthorOrArtist());
        TextField pubF   = new TextField(sel.getPublisher());
        TextField bcF    = new TextField(sel.getBarcode());
        TextField clsF   = new TextField(sel.getClassification());
        TextField locF   = new TextField(sel.getPhysicalLocation());
        grid.addRow(0, new Label("Titel:"), titleF);
        grid.addRow(1, new Label("Kategori:"), catF);
        grid.addRow(2, new Label("Författare/Artist:"), authF);
        grid.addRow(3, new Label("Förlag:"), pubF);
        grid.addRow(4, new Label("Streckkod:"), bcF);
        grid.addRow(5, new Label("Classificering:"), clsF);
        grid.addRow(6, new Label("Plats:"), locF);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == updBtn) {
                sel.setTitle(titleF.getText());
                sel.setCategory(catF.getText());
                sel.setAuthorOrArtist(authF.getText());
                sel.setPublisher(pubF.getText());
                sel.setBarcode(bcF.getText());
                sel.setClassification(clsF.getText());
                sel.setPhysicalLocation(locF.getText());
                return sel;
            }
            return null;
        });

        Optional<Item> res = dialog.showAndWait();
        res.ifPresent(item -> {
            if (itemDao.updateItem(item)) loadItems();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej uppdatera").showAndWait();
        });
    }

    @FXML
    private void onDeleteItem(ActionEvent e) {
        Item sel = itemTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Välj ett objekt att ta bort.").showAndWait();
            return;
        }
        Alert c = new Alert(Alert.AlertType.CONFIRMATION,
                "Ta bort '"+sel.getTitle()+"'?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> r = c.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.YES) {
            if (itemDao.deleteItem(sel.getItemId())) loadItems();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej ta bort").showAndWait();
        }
    }

    @FXML private void onGoToDashboard(ActionEvent e) {
        NavigationService.navigateTo("staffDashboardView.fxml");
    }
    @FXML private void onLogout(ActionEvent e) {
        NavigationService.navigateTo("loginView.fxml");
    }

    public void goBack(ActionEvent actionEvent) {
        NavigationService.goBack();
    }
}