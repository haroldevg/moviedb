package pe.haroldev.moviedb.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.haroldev.moviedb.models.Movie;

public class JsonUtils {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String RELEASEDATE = "release_date";
    private static final String BACKDROPPATH = "backdrop_path";
    private static final String POSTERPATH = "poster_path";
    private static final String VOTEAVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String RESULTS = "results";

    public static final String TOPRATED = "top_rated";
    public static final String POPULAR = "popular";


    public static List<Movie> parseMovieJSON(String json) {

        List<Movie> movieList = new ArrayList<>();

        try {
            JSONObject movieJSON = new JSONObject(json);

            if (movieJSON.has(RESULTS)) {

                JSONArray resultsJSON = movieJSON.getJSONArray(RESULTS);

                for (int i = 0; i < resultsJSON.length(); i++) {

                    JSONObject movieObject = resultsJSON.getJSONObject(i);
                    int id = movieObject.has(ID) ? movieObject.getInt(ID) : 0;
                    String title = movieObject.has(TITLE) ? movieObject.getString(TITLE) : "";
                    String releaseDate = movieObject.has(RELEASEDATE) ? movieObject.getString(RELEASEDATE) : "";
                    String posterPath = movieObject.has(POSTERPATH) ? movieObject.getString(POSTERPATH) : "";
                    String backdropPath = movieObject.has(BACKDROPPATH) ? movieObject.getString(BACKDROPPATH) : "";
                    double voteAverage = movieObject.has(VOTEAVERAGE) ? movieObject.getDouble(VOTEAVERAGE) : 0.0;
                    String overview = movieObject.has(OVERVIEW) ? movieObject.getString(OVERVIEW) : "";

                    movieList.add(new Movie(
                            id,
                            title,
                            releaseDate,
                            posterPath,
                            backdropPath,
                            voteAverage,
                            overview
                    ));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;

    }
}
