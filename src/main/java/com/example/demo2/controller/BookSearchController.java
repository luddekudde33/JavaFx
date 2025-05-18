package com.example.demo2.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo2.Dao.LoanDao;
import com.example.demo2.Model.Loan;
import com.example.demo2.NavigationService;
import com.example.demo2.Model.Book;
import com.example.demo2.Sql.DbUtil;

import com.example.demo2.service.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;



public class BookSearchController {
    private final LoanDao loanDao = new LoanDao();

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
    	mediaTypeBox.getItems().addAll("Alla", "Bok", "Kurslitteratur");
    	mediaTypeBox.setValue("Alla");
    	
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
    		String sql = "SELECT * FROM Book WHERE (title LIKE ? OR author LIKE ? OR isbn =?) ";
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
    @FXML private void onLoanBook(ActionEvent e) {
        if (Session.getCurrentUser() == null) {
            new Alert(Alert.AlertType.WARNING, "Du måste logga in först.").showAndWait();
            NavigationService.navigateTo("LoginView.fxml");
            return;
        }
        Book sel = tableView.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Välj en bok att låna.").showAndWait();
            return;
        }
        Dialog<Loan> dlg = new Dialog<>();
        dlg.setTitle("Låna bok");
        ButtonType loanBtn = new ButtonType("Låna", ButtonBar.ButtonData.OK_DONE);
        dlg.getDialogPane().getButtonTypes().addAll(loanBtn, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField userIdF = new TextField(); userIdF.setPromptText("Användar-ID");
        DatePicker duePicker = new DatePicker(LocalDate.now().plusWeeks(2));
        grid.addRow(0, new Label("Användar-ID:"), userIdF);
        grid.addRow(1, new Label("Återlämning senast:"), duePicker);
        dlg.getDialogPane().setContent(grid);

        dlg.setResultConverter(btn -> {
            if (btn == loanBtn) {
                Loan loan = new Loan();
                loan.setBookId(sel.getBookId());
                loan.setMovieId(null);
                loan.setUserId(Integer.parseInt(userIdF.getText()));
                loan.setLoanDate(LocalDate.now());
                loan.setDueDate(duePicker.getValue());
                loan.setStatus(1);
                return loan;
            }
            return null;
        });


        Optional<Loan> result = dlg.showAndWait();
        result.ifPresent(loan -> {
            if (loanDao.addLoan(loan)) {
                new Alert(Alert.AlertType.INFORMATION, "Bok utlånad!").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Kunde ej låna bok.").showAndWait();
            }
        });
    }

}




    
    
