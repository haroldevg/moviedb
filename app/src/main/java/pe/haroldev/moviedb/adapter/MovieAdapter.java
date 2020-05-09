package pe.haroldev.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pe.haroldev.moviedb.R;
import pe.haroldev.moviedb.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<Movie> movieList;

    final private ListItemClickListener mOnClickListener;

    public MovieAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        if(movieList == null) return 0;
        return movieList.size();
    }

    public void setAll(List<Movie> movies){

        if(movieList != null) movieList.clear();
        else movieList = new ArrayList<>();

        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(Movie model);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView ivBackground;
        final TextView tvTextView;
        final RatingBar rbAverage;

        MovieViewHolder(View itemView) {
            super(itemView);
            ivBackground = itemView.findViewById(R.id.iv_background);
            tvTextView = itemView.findViewById(R.id.tv_title);
            rbAverage = itemView.findViewById(R.id.rb_average);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            tvTextView.setText(movie.getTitle());
            float average = (float) movie.getVoteAverage();
            rbAverage.setRating(average);
            Picasso.get().load(movie.getPosterPath()).fit().into(ivBackground);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(movieList.get(getAdapterPosition()));
        }
    }

}
