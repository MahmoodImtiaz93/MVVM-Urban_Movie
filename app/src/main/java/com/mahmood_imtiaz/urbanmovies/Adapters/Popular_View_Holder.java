package com.mahmood_imtiaz.urbanmovies.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmood_imtiaz.urbanmovies.R;

import org.jetbrains.annotations.NotNull;

public class Popular_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnMovieListener onMovieListener;
    ImageView imageView2;
    RatingBar ratingBar2;

    public Popular_View_Holder(@NonNull @NotNull View itemView,OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        imageView2 = itemView.findViewById(R.id.movie_img2);
        ratingBar2 = itemView.findViewById(R.id.rating_bar2);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
