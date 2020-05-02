package com.amressam.movies;

import java.io.Serializable;

public class Cast implements Serializable {

    String name;
    String character;
    String image;

    public Cast(String name, String character, String image) {
        this.name = name;
        this.character = character;
        this.image = image;
    }
    public Cast(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getImage() {
        return ("http://image.tmdb.org/t/p/w500/"+image);
    }

    public void setImage(String image) {
        this.image = image;
    }
}
