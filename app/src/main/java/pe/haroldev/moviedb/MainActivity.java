package pe.haroldev.moviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import pe.haroldev.moviedb.adapter.MovieAdapter;
import pe.haroldev.moviedb.models.Movie;
import pe.haroldev.moviedb.utilities.JsonUtils;
import pe.haroldev.moviedb.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    private String currentFilter = JsonUtils.POPULAR;

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_main);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        movieAdapter = new MovieAdapter(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(movieAdapter);

        loadMovieData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.it_popular) {
            currentFilter = JsonUtils.POPULAR;
            loadMovieData();
        } else if (menuItemId == R.id.it_toprated) {
            currentFilter = JsonUtils.TOPRATED;
            loadMovieData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovieData() {
        showMovieDataView();
        new FetchMovieTask().execute(currentFilter);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(Movie model) {
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("movie",model);
        startActivity(intent);
    }

    private class FetchMovieTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            String query = params[0];
            String results = null;
            try {
                URL url = NetworkUtils.fetchMovies(query);
                results = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                List<Movie> results = JsonUtils.parseMovieJSON(s);
                movieAdapter.setAll(results);
                showMovieDataView();
            } else {
                showErrorMessage();
            }
        }

    }
}
