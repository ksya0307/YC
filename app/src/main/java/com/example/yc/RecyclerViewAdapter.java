package com.example.yc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.yc.cinema.Movie;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Movie> list;
    Context context;



    public RecyclerViewAdapter(List<Movie> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.allmovie, parent, false);
        return new ViewHolder(view);

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position));
        holder.movie_name.setText(list.get(position).getTitle());
        holder.movie_genre.setText(list.get(position).getGenres());

        holder.movie_year.setText(list.get(position).getYear());
        holder.movie_time.setText(String.valueOf(list.get(position).getTime())+" мин.");
        holder.movie_age.setText(String.valueOf(list.get(position).getAge())+"+");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(21));
       Glide.with(this.context).load(list.get(position).getImage()).apply(requestOptions).into(holder.movie_img);


       holder.first_lay.setOnClickListener(v -> {
           Log.d(TAG,"клик на "+list.get(position).getImage());
           Toast.makeText(context, list.get(position).getImage(), Toast.LENGTH_SHORT).show();

           Intent intent = new Intent(context,MovieInfo.class);
           intent.putExtra("movie_name",list.get(position).getTitle());
           intent.putExtra("genre",list.get(position).getGenres());
           intent.putExtra("descr",list.get(position).getDescription());
           intent.putExtra("img",list.get(position).getImage());
           context.startActivity(intent);
       });

//        Picasso.with((Context) list).load(list.get(position).getImg()).into(holder.binding.movieImage);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<Movie> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
    public void choosedGenreList(ArrayList<Movie> result){
        list = result;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView movie_img;
        TextView movie_name, movie_genre, movie_decr;
        Button movie_year, movie_time,movie_age;
        LinearLayout first_lay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_img = itemView.findViewById(R.id.movie_image);
            movie_name = itemView.findViewById(R.id.movie_name);
            movie_age=itemView.findViewById(R.id.movie_age);
            movie_genre=itemView.findViewById(R.id.movie_genre);
            movie_time=itemView.findViewById(R.id.movie_time);
            movie_year=itemView.findViewById(R.id.movie_year);
            first_lay = itemView.findViewById(R.id.parent_lay);

            movie_decr=itemView.findViewById(R.id.p_movie_desc);
        }
    }

}
