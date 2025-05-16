package com.example.demo2.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo2.NavigationService;
import com.example.demo2.Model.Book;
import com.example.demo2.Sql.DbUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookSearchController {

    @FXML
    private TextField authorField;

    @FXML
    private CheckBox availableOnlyCB;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colAvailable;

    @FXML
    private TableColumn<Book, String> colCategory;

    @FXML
    private TableColumn<Book, String> colISBN;

    @FXML
    private TableColumn<Book, String> colTitle;
    
    @FXML
    private TableColumn<Book, String> colPhysicalLocation;

    @FXML
    private TextField isbnField;

    @FXML
    private ChoiceBox<String> mediaTypeBox;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TextField titleField;

    @FXML
    private void initialize() {
    	mediaTypeBox.getItems().addAll("Bok", "Kurslitteratur", "DVD");
    	
    	//Skapa kolumnerna
    	colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    	colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    	colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
    	colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    	colAvailable.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
    	colPhysicalLocation.setCellValueFactory(new PropertyValueFactory<>("physicalLocation"));
    }
    	
    	        
    @FXML
    void onGoToLogin(ActionEvent event) {
    	try {
            NavigationService.navigateTo("LoginView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Kunde inte ladda inloggningssidan.")
                    .showAndWait();
        }
    }

    @FXML
    void onGoToMovieSearch(ActionEvent event) {
    	NavigationService.navigateTo("movieSearchView.fxml");
    }

    @FXML
    void onGoToStart(ActionEvent event) {
    	NavigationService.navigateTo("startView.fxml");
    }
    
        
    @FXML
    void doSearchBook(ActionEvent event) {
    	//Rensa tabellen från ev. tidigare sökresultat
		tableView.getItems().clear();
		
    	if (!titleField.getText().isEmpty() || !authorField.getText().isEmpty()) {
    		//Sökning där titel och författare ska likna och isbn helt överensstämma
    		String sql = "SELECT * FROM Book WHERE title LIKE ? OR author LIKE ? OR isbn =? LIMIT 100";
    		try (Connection conn = DbUtil.getConnection();
    			PreparedStatement ps = conn.prepareStatement(sql)) {
    			
    			// Borttrimning av ev. onödiga blanksteg och lägger till "wildcards" för LIKE-sökningarna. 
    			//"Wildcard" läggs bara till om sökfältet innehåller någon text, eftersom tomma fält annars alltid ger oönskade svarsresultat.
    			
    		    String titleSearch = titleField.getText().trim();
    		    if (!titleSearch.isEmpty()) {
    		    	titleSearch = ("%" + titleSearch + "%");
    		    }
    		    
    		    String authorSearch = authorField.getText().trim();
    		    if (!authorSearch.isEmpty()) {
    		    	authorSearch = ("%" + authorSearch + "%");
    		    }
    		    
    		    String isbnSearch = isbnField.getText().trim();
    		    if (!titleSearch.isEmpty()) {
    		    	titleSearch = ("%" + titleSearch + "%");
    		    }
    		       		    
    			ps.setString(1, titleSearch);
    			ps.setString(2, authorSearch);
    			ps.setString(3, isbnSearch);
    			
    			try (ResultSet rs = ps.executeQuery()){
    				List<Book> books = new ArrayList<>();
    				while (rs.next()) {
    					Book book = new Book(
    							rs.getInt("bookID"),
    							rs.getString("title"),
    							rs.getString("category"),
    							rs.getString("author"),
    							rs.getString("publisher"),
    							rs.getString("barcode"),
    							rs.getString("isbn"),
    							rs.getString("physicalLocation"),
    							rs.getString("classification"),
    							rs.getInt("isAvailable")							
    							);
    					
    					books.add(book);
    				}
    				//Fyll på tabellen med aktuella resultat
    				tableView.getItems().setAll(books);
    			}
    			
    		} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
}

    
    
