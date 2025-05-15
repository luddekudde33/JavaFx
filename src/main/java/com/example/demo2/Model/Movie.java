package com.example.demo2.Model;

public class Movie {
    private int movieId;
    private String title;
    private String mainCharacter;
    private String barcode;
    private String physicalLocation;

    public Movie() { }

    public Movie(int movieId, String title, String mainCharacter,
                 String barcode, String physicalLocation) {
        this.movieId = movieId;
        this.title = title;
        this.mainCharacter = mainCharacter;
        this.barcode = barcode;
        this.physicalLocation = physicalLocation;
    }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMainCharacter() { return mainCharacter; }
    public void setMainCharacter(String mainCharacter) { this.mainCharacter = mainCharacter; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getPhysicalLocation() { return physicalLocation; }
    public void setPhysicalLocation(String physicalLocation) { this.physicalLocation = physicalLocation; }
}