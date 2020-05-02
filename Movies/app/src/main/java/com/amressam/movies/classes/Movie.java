package com.amressam.movies;



import java.io.Serializable;

public class Movie implements Serializable {

    private int mMovie_Id;
    private String mTitle;
    private String mImage;
    private String mPosterImage;
    private String mReleaseDate;
    private int mRate;
    private int mVote_count;
    private String mOverView;
    private int mCategory;


    public Movie(int m_id,String title, String image,String poster,String releaseDate, int rate, int vote_count,String overview,int category) {
        mTitle = title;
        mRate = rate;
        mReleaseDate = releaseDate;
        mImage = image;
        mPosterImage=poster;
        mOverView = overview;
        mMovie_Id = m_id;
        mVote_count = vote_count;
        mCategory = category;
    }

    public int getMovie_Id() {
        return mMovie_Id;
    }

    public void setMovie_Id(int movie_Id) {
        mMovie_Id = movie_Id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getPosterImage() {
        return mPosterImage;
    }

    public void setPosterImage(String posterImage) {
        mPosterImage = posterImage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public int getRate() {
        return mRate;
    }

    public void setRate(int rate) {
        mRate = rate;
    }

    public int getVote_count() {
        return mVote_count;
    }

    public void setVote_count(int vote_count) {
        mVote_count = vote_count;
    }

    public String getOverView() {
        return mOverView;
    }

    public void setOverView(String overView) {
        mOverView = overView;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        mCategory = category;
    }
}
