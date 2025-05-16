package com.example.demo2.controller;

import com.example.demo2.Model.Movie;
import com.example.demo2.Dao.MovieDao;
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

public class MovieManagementController {

    @FXML private TableView<Movie> movieTable;
    @FXML private TableColumn<Movie, Integer> colId;
    @FXML private TableColumn<Movie, String> colTitle;
    @FXML private TableColumn<Movie, String> colMainCharacter;
    @FXML private TableColumn<Movie, String> colBarcode;
    @FXML private TableColumn<Movie, String> colLocation;

    private final MovieDao movieDao = new MovieDao();
    private final ObservableList<Movie> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("movieId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colMainCharacter.setCellValueFactory(new PropertyValueFactory<>("mainCharacter"));
        colBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("physicalLocation"));
        movieTable.setItems(data);
        loadMovies();
    }

    private void loadMovies() {
        List<Movie> list = movieDao.getAllMovies();
        data.setAll(list);
    }

    @FXML
    private void onAddMovie(ActionEvent e) {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("Lägg till film");
        ButtonType addBtn = new ButtonType("Lägg till", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField titleF = new TextField();
        TextField mainF  = new TextField();
        TextField bcF    = new TextField();
        TextField locF   = new TextField();
        grid.addRow(0, new Label("Titel:"), titleF);
        grid.addRow(1, new Label("Huvudkaraktär:"), mainF);
        grid.addRow(2, new Label("Streckkod:"), bcF);
        grid.addRow(3, new Label("Plats:"), locF);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == addBtn) {
                return new Movie(0, titleF.getText(), mainF.getText(), bcF.getText(), locF.getText(), "DVD", 1);
            }
            return null;
        });

        Optional<Movie> res = dialog.showAndWait();
        res.ifPresent(movie -> {
            if (movieDao.addMovie(movie)) loadMovies();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej lägga till").showAndWait();
        });
    }

    @FXML
    private void onEditMovie(ActionEvent e) {
        Movie sel = movieTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Välj en film först.").showAndWait();
            return;
        }
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("Redigera film");
        ButtonType updBtn = new ButtonType("Spara", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField titleF = new TextField(sel.getTitle());
        TextField mainF  = new TextField(sel.getMainCharacter());
        TextField bcF    = new TextField(sel.getBarcode());
        TextField locF   = new TextField(sel.getPhysicalLocation());
        grid.addRow(0, new Label("Titel:"), titleF);
        grid.addRow(1, new Label("Huvudkaraktär:"), mainF);
        grid.addRow(2, new Label("Streckkod:"), bcF);
        grid.addRow(3, new Label("Plats:"), locF);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == updBtn) {
                sel.setTitle(titleF.getText());
                sel.setMainCharacter(mainF.getText());
                sel.setBarcode(bcF.getText());
                sel.setPhysicalLocation(locF.getText());
                return sel;
            }
            return null;
        });

        Optional<Movie> res = dialog.showAndWait();
        res.ifPresent(movie -> {
            if (movieDao.updateMovie(movie)) loadMovies();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej uppdatera").showAndWait();
        });
    }

    @FXML
    private void onDeleteMovie(ActionEvent e) {
        Movie sel = movieTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Välj en film att ta bort.").showAndWait();
            return;
        }
        Alert c = new Alert(Alert.AlertType.CONFIRMATION,
                "Ta bort '"+sel.getTitle()+"'?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> r = c.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.YES) {
            if (movieDao.deleteMovie(sel.getMovieId())) loadMovies();
            else new Alert(Alert.AlertType.ERROR, "Kunde ej ta bort").showAndWait();
        }
    }

    public void goBack(ActionEvent actionEvent) {
        NavigationService.goBack();
    }
}