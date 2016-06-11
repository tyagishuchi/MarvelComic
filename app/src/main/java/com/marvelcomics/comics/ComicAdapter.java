package com.marvelcomics.comics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marvelcomics.R;
import com.marvelcomics.characters.CharacterModel;
import com.marvelcomics.strips.SingleComic;
import com.marvelcomics.utility.DateMonster;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHUCHI on 6/10/2016.
 */
public class ComicAdapter  extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {
    private Context context;
    List<ComicsModel.Data.Result> items = new ArrayList<ComicsModel.Data.Result>();

    public ComicAdapter(List<ComicsModel.Data.Result> data) {

        this.items = data;
    }


    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_comic_row, parent, false);
        context = parent.getContext();
        ComicViewHolder viewHolder = new ComicViewHolder(v, new ViewHolderClicks() {
            @Override
            public void showComicsStrips(int position) {
                ComicsModel.Data.Result current = items.get(position);
                context.startActivity(new Intent(context, SingleComic.class).putExtra("id", current.getId()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ComicViewHolder viewHolder, int position) {
        try {
            ComicsModel.Data.Result current = items.get(position);
            StringBuilder charString = new StringBuilder();

            viewHolder.comic_name.setText(current.getTitle());
            viewHolder.date.setText(current.getModified());
            Picasso.with(context)
                    .load(current.getThumbnail().getPath() + "." + current.getThumbnail().getExtension())
                    .resize(500, 500)
                    .into(viewHolder.image);
            for (ComicsModel.Data.Result.Characters.Item_ characters : current.getCharacters().getItems()) {
                charString.append(characters.getName() + ",");
            }
            viewHolder.characters.setText("Characters :" + System.lineSeparator() + charString);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView description, comic_name,characters,date;
        ImageView image;
        ViewHolderClicks mListener;

        public ComicViewHolder(View itemView, ViewHolderClicks listener) {

            super(itemView);
            mListener = listener;
            image = (ImageView) itemView.findViewById(R.id.comic_img);
            comic_name = (TextView) itemView.findViewById(R.id.comic_name);
            characters = (TextView)itemView.findViewById(R.id.characters);
            date = (TextView)itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    mListener.showComicsStrips(getLayoutPosition());
                    break;
            }
        }
    }

    public interface ViewHolderClicks {
        public void showComicsStrips(int position);

    }
}
