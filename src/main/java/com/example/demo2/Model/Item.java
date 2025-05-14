package com.example.demo2.Model;

public class Item {
    private int itemId;
    private String title;
    private String category;
    private String authorOrArtist;
    private String publisher;
    private String barcode;
    private String classification;
    private String physicalLocation;

    public Item() { }

    public Item(int itemId, String title, String category,
                String authorOrArtist, String publisher,
                String barcode, String classification,
                String physicalLocation) {
        this.itemId = itemId;
        this.title = title;
        this.category = category;
        this.authorOrArtist = authorOrArtist;
        this.publisher = publisher;
        this.barcode = barcode;
        this.classification = classification;
        this.physicalLocation = physicalLocation;
    }

    // Getters & setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getAuthorOrArtist() { return authorOrArtist; }
    public void setAuthorOrArtist(String authorOrArtist) { this.authorOrArtist = authorOrArtist; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }
    public String getPhysicalLocation() { return physicalLocation; }
    public void setPhysicalLocation(String physicalLocation) { this.physicalLocation = physicalLocation; }
}
