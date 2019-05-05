package com.leanagri;

import java.io.Serializable;

public class MovieRecyclerModel implements Serializable {
    private String title;
    private String posterUrl;
    private String releaseDate;
    private String rating;
    private String description;

    public MovieRecyclerModel(){

    }

    public MovieRecyclerModel(String title, String posterUrl, String releaseDate, String rating, String description) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MovieRecyclerModel{" +
                "title='" + title + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
