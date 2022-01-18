package ru.oleglunko.kinopoiskparser.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String originalName;

    @NotNull
    private Integer position;

    @NotNull
    private Integer releaseYear;

    @NotNull
    private Double rating;

    @NotNull
    private Integer votesCount;

    @NotNull
    private LocalDate actualDate;

    public Film() {
    }

    public Film(String originalName, Integer position, Integer releaseYear, Double rating, Integer votesCount, LocalDate actualDate) {
        this.originalName = originalName;
        this.position = position;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.votesCount = votesCount;
        this.actualDate = actualDate;
    }

    public Film(Long id, String originalName, Integer position, Integer releaseYear, Double rating, Integer votesCount, LocalDate actualDate) {
        this(originalName, position, releaseYear, rating, votesCount, actualDate);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
    }

    public LocalDate getActualDate() {
        return actualDate;
    }

    public void setActualDate(LocalDate actualDate) {
        this.actualDate = actualDate;
    }
}
