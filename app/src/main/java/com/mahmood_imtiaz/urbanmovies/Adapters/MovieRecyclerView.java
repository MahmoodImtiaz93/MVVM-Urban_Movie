package com.mahmood_imtiaz.urbanmovies.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmood_imtiaz.urbanmovies.Model.MovieModel;
import com.mahmood_imtiaz.urbanmovies.R;
import com.mahmood_imtiaz.urbanmovies.Utils.Credentials;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP=1;
    private static final int DISPLAY_SEARCH=2;


    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType==DISPLAY_SEARCH){
            view =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_layout,parent,false);
            return new MovieViewHolder(view,onMovieListener);
        }else {
            view =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.popular_list_layout,parent,false);
            return new Popular_View_Holder(view,onMovieListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType==DISPLAY_SEARCH){
            ((MovieViewHolder)holder).title.setText(mMovies.get(position).getTitle());
            ((MovieViewHolder)holder).releaseDate.setText(mMovies.get(position).getRelease_date());
            ((MovieViewHolder)holder).duration.setText(mMovies.get(position).getOriginal_language());
            //Vote avg is over 10 and rating bar is 5; so we are dividing it by 2
            ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(position).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"+mMovies.get(position).getPoster_path())
                    .into(((MovieViewHolder)holder).imageView);
        } else {
             //Vote avg is over 10 and rating bar is 5; so we are dividing it by 2
            ((Popular_View_Holder)holder).ratingBar2.setRating((mMovies.get(position).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"+mMovies.get(position).getPoster_path())
                    .into(((Popular_View_Holder)holder).imageView2);

        }

    }

    @Override
    public int getItemCount() {
        if (mMovies!=null){
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //Getting the id by the movie clicked
    public MovieModel getSelectedMovie(int position){
        if (mMovies!=null){
            if (mMovies.size()>0){
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR){
            return DISPLAY_POP;
        }else
            return DISPLAY_SEARCH;
    }
}
