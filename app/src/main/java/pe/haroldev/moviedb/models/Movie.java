package pe.haroldev.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String releaseDate;
    private String posterPath;
    private String backdropPath;
    private double voteAverage;
    private String overview;

    private final String IMAGE_PATH = "http://image.tmdb.org/t/p/";
    private final String POSTER_SIZE = "w185";
    private final String BACKDROP_SIZE = "w780";

    public Movie(int id, String title, String releaseDate, String posterPath,String backdropPath, double voteAverage, String overview) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        releaseDate = in.readString();
        backdropPath = in.readString();
        posterPath = in.readString();
        voteAverage = in.readDouble();
        overview = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return IMAGE_PATH+POSTER_SIZE+posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBackdropPath() {
        return IMAGE_PATH+BACKDROP_SIZE+backdropPath;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(backdropPath);
        dest.writeString(posterPath);
        dest.writeDouble(voteAverage);
        dest.writeString(overview);
    }
}
