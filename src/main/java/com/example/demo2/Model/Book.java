package com.example.demo2.Model;

public class Book {
    private int bookId;
    private String title;
    private String category;
    private String author;
    private String publisher;
    private String barcode;
    private String isbn;
    private String physicalLocation;
    private String classification;
    private int isAvailable;

    public Book() { }

    public Book(int bookId, String title, String category, String author, String publisher, String barcode, String isbn, 
    		String physicalLocation, String classification, int isAvailable) {
        this.bookId = bookId;
        this.title = title;
        this.category = category;
        this.author = author;
        this.publisher = publisher;
        this.barcode = barcode;
        this.isbn = isbn;
        this.physicalLocation = physicalLocation;
        this.classification = classification;
        this.isAvailable = isAvailable;
    }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getPhysicalLocation() { return physicalLocation; }
    public void setPhysicalLocation(String physicalLocation) { this.physicalLocation = physicalLocation; }
    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }
    public int getAvailable() { return isAvailable; }
    public void setAvailable(int available) { isAvailable = available; }
}

