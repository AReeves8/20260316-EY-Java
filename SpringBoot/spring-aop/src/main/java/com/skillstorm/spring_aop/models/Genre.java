package com.skillstorm.spring_aop.models;

public enum Genre {
    
    ACTION("Action"),
    COMEDY("Comedy"), 
    DRAMA("Drama"), 
    HORROR("Horror"), 
    THRILLER("Thriller"), 
    ROMANCE("Romance"),
    SCIENCE_FICTION("Sci-Fi"), 
    DOCUMENTARY("Documentary");


    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
