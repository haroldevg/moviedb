package pe.haroldev.moviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import pe.haroldev.moviedb.models.Movie;

public class DetailActivity extends AppCompatActivity {

    private final String MOVIE_EXTRA = "movie";

    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvVoteAverage;
    private TextView tvOverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie model;
        Intent intent = getIntent();
        if(intent.hasExtra(MOVIE_EXTRA)){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                model = bundle.getParcelable(MOVIE_EXTRA);
                if(model != null){
                    populateUI(model);
                }else{
                    closeOnError();
                }
            }else{
                closeOnError();
            }
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Movie movie) {
        //initializing textviews

        ivImage = findViewById(R.id.image_iv);
        tvTitle = findViewById(R.id.title_tv);
        tvReleaseDate = findViewById(R.id.release_date_tv);
        tvVoteAverage = findViewById(R.id.vote_average_tv);
        tvOverView = findViewById(R.id.review_tv);

        //populate ui with Data
        Picasso.get()
                .load(movie.getBackdropPath())
                .into(ivImage);
        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        tvOverView.setText(movie.getOverview());

    }
}
