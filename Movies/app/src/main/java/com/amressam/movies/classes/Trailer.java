package com.amressam.movies;

import java.io.Serializable;

public class Trailer implements Serializable {
    String title;
    String image;
    String trailer_id;

    public Trailer(String title, String image, String trailer_id) {
        this.title = title;
        this.image = image;
        this.trailer_id = trailer_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTrailer_id() {
        return trailer_id;
    }

    public void setTrailer_id(String trailer_id) {
        this.trailer_id = trailer_id;
    }
}
