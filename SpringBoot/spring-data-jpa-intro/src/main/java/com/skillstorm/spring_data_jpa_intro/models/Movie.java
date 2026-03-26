package com.skillstorm.spring_data_jpa_intro.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity                     // this tells JPA that this is an entity that it needs to map to the DB
@Table(name = "MOVIES")     // tells JPA which specific table this class maps to. if the class name is the same as table name, unnecessary
public class Movie {

    @Id                                                     // tells JPA this is an ID value and the Primary Key for this table
    @Column                                                 // tells JPA this instance variable corresponds to a column in the DB
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // tells JPA this is an Auto Incerement field
    private int id;

    @NotBlank           // Jakarta Validations (formerly known as JSR-303, JSR-380). Use @Valid to automatically check these
    @Column(name = "movie_title")
    private String title;

    @Min(value = 1)
    @Max(value = 10)
    @Column
    private int rating;

    @NotNull(message = "Genre cannot be null")              // can add error messages if you desire
    @Column
    @Enumerated(EnumType.STRING)                            // tells JPA to convert the enum value to a string when putting into DB
    private Genre genre;

    @ManyToOne                                              // tells JPA this is the "many" side of the relationship
    @JoinColumn(name = "director_id")                       // tells JPA to join the Director object by the given column name
    private Director director;
    
    public Movie() {
    }

    public Movie(int id, String title, int rating, Genre genre, Director director) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.genre = genre;
        this.director = director;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + rating;
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((director == null) ? 0 : director.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (id != other.id)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (rating != other.rating)
            return false;
        if (genre != other.genre)
            return false;
        if (director == null) {
            if (other.director != null)
                return false;
        } else if (!director.equals(other.director))
            return false;
        return true;
    }

    // need to make sure only one of movies/directors prints the other one out
    @Override
    public String toString() {
        return "Movie [id=" + id + ", title=" + title + ", rating=" + rating + ", genre=" + genre + ", director="
                + director + "]";
    }

}
