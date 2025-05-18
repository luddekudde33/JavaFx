package com.example.demo2.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo2.Model.Loan;
import com.example.demo2.NavigationService;
import com.example.demo2.Dao.LoanDao;
import com.example.demo2.Model.Movie;
import com.example.demo2.Sql.DbUtil;

import com.example.demo2.service.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class MovieSearchController {
	private final LoanDao loanDao = new LoanDao();

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
	@FXML
	private void onLoanMovie(ActionEvent e) {
		if (Session.getCurrentUser() == null) {
			new Alert(Alert.AlertType.WARNING, "Du måste logga in först.").showAndWait();
			NavigationService.navigateTo("LoginView.fxml");
			return;
		}

		Movie sel = tableView.getSelectionModel().getSelectedItem();
		if (sel == null) {
			new Alert(Alert.AlertType.WARNING, "Välj en film att låna.").showAndWait();
			return;
		}

		Dialog<Loan> dlg = new Dialog<>();
		dlg.setTitle("Låna film");
		ButtonType loanBtn = new ButtonType("Låna", ButtonBar.ButtonData.OK_DONE);
		dlg.getDialogPane().getButtonTypes().addAll(loanBtn, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
		TextField userIdF   = new TextField();  userIdF.setPromptText("Användar-ID");
		DatePicker duePicker = new DatePicker(LocalDate.now().plusWeeks(2));
		grid.addRow(0, new Label("Användar-ID:"),      userIdF);
		grid.addRow(1, new Label("Återlämning senast:"), duePicker);
		dlg.getDialogPane().setContent(grid);

		dlg.setResultConverter(btn -> {
			if (btn == loanBtn) {
				Loan loan = new Loan();
				loan.setBookId(null);
				loan.setMovieId(sel.getMovieId());
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
		res.ifPresent(l -> {
			if (loanDao.addLoan(l)) {
				new Alert(Alert.AlertType.INFORMATION, "Film utlånad!").showAndWait();
			} else {
				new Alert(Alert.AlertType.ERROR, "Kunde ej låna film.").showAndWait();
			}
		});
	}
}

    
    
