package com.skillstorm.spring_data_jpa_intro.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "DIRECTORS")
public class Director {

    @Id                                                     
    @Column                                                 
    @GeneratedValue(strategy = GenerationType.IDENTITY)     
    private int id;

    @NotBlank
    @Column(name = "first_name")       // name is only necessary if the column name is different than the instance variable name
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    //@JsonProperty("last-name")      // when field is converted to and from JSON, it will use the provided value for name in JSON
    private String lastName;

    @JsonIgnore                         // tells Jackson Object Mapper to ignore this property 
    // tells JPA this is part of a one-to-many relationship and that this is the "one" side
    @OneToMany(targetEntity = Movie.class, mappedBy = "director")   // mappedBy is the name of the instance variable in the other class
    Set<Movie> movies;

    
    public Director() {
    }

    public Director(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Director(int id, String firstName, String lastName, Set<Movie> movies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((movies == null) ? 0 : movies.hashCode());
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
        Director other = (Director) obj;
        if (id != other.id)
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (movies == null) {
            if (other.movies != null)
                return false;
        } else if (!movies.equals(other.movies))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Director [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }

}
