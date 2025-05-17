package com.example.demo2.Model;

import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int userId;
    private int copyId;        // referens till Book.bookID eller Movie.movieID
    private int status;        // 1 = utlånad, 2 = återlämnad
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // null om inte återlämnad

    public Loan() {}

    public Loan(int loanId, int userId, int copyId, int status,
                LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.loanId     = loanId;
        this.userId     = userId;
        this.copyId     = copyId;
        this.status     = status;
        this.loanDate   = loanDate;
        this.dueDate    = dueDate;
        this.returnDate = returnDate;
    }

    // Getters & setters för alla fält

    public int getLoanId() { return loanId; }
    public void setLoanId(int loanId) { this.loanId = loanId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCopyId() { return copyId; }
    public void setCopyId(int copyId) { this.copyId = copyId; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
