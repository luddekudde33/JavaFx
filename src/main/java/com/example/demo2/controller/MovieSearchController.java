package com.example.demo2.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo2.NavigationService;
import com.example.demo2.Model.Movie;
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

public class MovieSearchController {
	
    @FXML
    private TextField titleField;
    
    @FXML
    private TextField mainCharacterField;
    
    @FXML
    private ChoiceBox<String> mediaTypeBox;

    @FXML
    private CheckBox availableOnlyCB;
    
    @FXML
    private TableColumn<Movie, String> colTitle;

    @FXML
    private TableColumn<Movie, String> colMainCharacter;

    @FXML
    private TableColumn<Movie, String> colCategory;
    
    @FXML
    private TableColumn<Movie, String> colAvailable;
    
    @FXML
    private TableColumn<Movie, String> colPhysicalLocation;
  
    @FXML
    private Button searchButton;

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private void initialize() {
    	mediaTypeBox.getItems().addAll("Alla", "DVD", "Blu-ray");
    	mediaTypeBox.setValue("Alla");
    	
    	//Skapa kolumnerna
    	colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    	colMainCharacter.setCellValueFactory(new PropertyValueFactory<>("mainCharacter"));
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
    void onGoToBookSearch(ActionEvent event) {
    	NavigationService.navigateTo("bookSearchView.fxml");
    }

    @FXML
    void onGoToStart(ActionEvent event) {
    	NavigationService.navigateTo("startView.fxml");
    }
    
        
    @FXML
    void doSearchMovie(ActionEvent event) {
    	//Rensa tabellen från ev. tidigare sökresultat
		tableView.getItems().clear();
		
    	if (!titleField.getText().isEmpty() || !mainCharacterField.getText().isEmpty()) {
    		//Sökning där titel och huvudkaraktär ska likna sökresultaten
    		//String sql = "SELECT * FROM Movie WHERE title LIKE ? OR mainCharacter LIKE ? LIMIT 100";
    		String sql = "SELECT * FROM Movie WHERE (title LIKE ? OR mainCharacter LIKE ?)";
    		//Om "bara tillgängliga" är ikryssad läggs det till i sökningen
    		if (availableOnlyCB.isSelected()) {
    			sql += " AND isAvailable > 0";
    		}
    		//Om mediatyp är något annat än "Alla" läggs det till i sökningen
    		if (!mediaTypeBox.getValue().equals("Alla")) {
    			sql += " AND category = '" + mediaTypeBox.getValue() + "'";
    		}
    		sql += " LIMIT 100";
    		try (Connection conn = DbUtil.getConnection();
    			PreparedStatement ps = conn.prepareStatement(sql)) {
    			
    			// Borttrimning av ev. onödiga blanksteg och lägger till "wildcards" för LIKE-sökningarna. 
    			//"Wildcard" läggs bara till om sökfältet innehåller någon text, eftersom tomma fält annars alltid ger oönskade svarsresultat.
    			
    		    String titleSearch = titleField.getText().trim();
    		    if (!titleSearch.isEmpty()) {
    		    	titleSearch = ("%" + titleSearch + "%");
    		    }
    		    
    		    String mainCharacterSearch = mainCharacterField.getText().trim();
    		    if (!mainCharacterSearch.isEmpty()) {
    		    	mainCharacterSearch = ("%" + mainCharacterSearch + "%");
    		    }
    		    
    		        		       		    
    			ps.setString(1, titleSearch);
    			ps.setString(2, mainCharacterSearch);
    			
    			
    			try (ResultSet rs = ps.executeQuery()){
    				List<Movie> movies = new ArrayList<>();
    				while (rs.next()) {
    					Movie movie = new Movie(
    							rs.getInt("movieID"),
    							rs.getString("title"),
    							rs.getString("mainCharacter"),
    							rs.getString("barcode"),
    							rs.getString("physicalLocation"),
    							rs.getString("category"),
    							rs.getInt("isAvailable")							
    							);
    					
    					movies.add(movie);
    				}
    				//Fyll på tabellen med aktuella resultat
    				tableView.getItems().setAll(movies);
    			}
    			
    		} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
}

    
    
