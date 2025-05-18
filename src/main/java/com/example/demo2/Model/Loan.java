package com.example.demo2.Model;

import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int userId;
    private Integer bookId;
    private Integer movieId;
    private String bookTitle;   // title when bookId != null
    private String movieTitle;  // title when movieId != null
    private int status;         // 1 = utlånad, 2 = återlämnad
    private LocalDate loanDate;
    private LocalDate dueDate;

    public Loan() {}

    public Loan(int loanId, int userId,
                Integer bookId, Integer movieId,
                String bookTitle, String movieTitle,
                int status, LocalDate loanDate,
                LocalDate dueDate) {
        this.loanId     = loanId;
        this.userId     = userId;
        this.bookId     = bookId;
        this.movieId    = movieId;
        this.bookTitle  = bookTitle;
        this.movieTitle = movieTitle;
        this.status     = status;
        this.loanDate   = loanDate;
        this.dueDate    = dueDate;
    }

    // getters/setters for all fields
    public int getLoanId() { return loanId; }
    public void setLoanId(int loanId) { this.loanId = loanId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }
    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}