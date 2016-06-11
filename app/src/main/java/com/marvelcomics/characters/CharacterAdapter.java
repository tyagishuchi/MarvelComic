package com.marvelcomics.characters;

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
import com.marvelcomics.comics.Comics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHUCHI on 6/4/2016.
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private Context context;
    List<CharacterModel.Data.Result> items = new ArrayList<CharacterModel.Data.Result>();

    public CharacterAdapter(List<CharacterModel.Data.Result> data) {

        this.items = data;
    }


    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_character_row, parent, false);
        context = parent.getContext();
        CharacterViewHolder viewHolder = new CharacterViewHolder(v, new ViewHolderClicks() {
            @Override
            public void showComics(int position) {
                CharacterModel.Data.Result current = items.get(position);
                context.startActivity(new Intent(context,Comics.class).putExtra("id",current.getId()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder viewHolder, int position) {
        CharacterModel.Data.Result current = items.get(position);
        viewHolder.name.setText(current.getName());
        Picasso.with(context)
                .load(current.getThumbnail().getPath()+"."+current.getThumbnail().getExtension())
                .resize(340,320)
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,comic_name;
        ImageView image;
        ViewHolderClicks mListener;

        public CharacterViewHolder(View itemView, ViewHolderClicks listener) {

            super(itemView);
            mListener = listener;
            name = (TextView) itemView.findViewById(R.id.characters_text);
            image = (ImageView) itemView.findViewById(R.id.characters_img);
            comic_name = (TextView)itemView.findViewById(R.id.comic_name);
            comic_name.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    mListener.showComics(getLayoutPosition());
                    break;
            }
        }
    }

    public interface ViewHolderClicks {
        public void showComics(int position);

    }
}