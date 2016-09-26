package no.joharei.flixr.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import no.joharei.flixr.Henson;
import no.joharei.flixr.R;
import no.joharei.flixr.network.models.Photo;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private final Context context;
    private final Drawable mDefaultCardImage;
    private ArrayList<Photo> photos;

    public PhotoAdapter(Context context) {
        this.context = context;
        photos = new ArrayList<>();
        mDefaultCardImage = ContextCompat.getDrawable(context, R.drawable.movie);
    }

    public void swap(List<Photo> photos) {
        this.photos.clear();
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        itemView.setFocusable(true);
        itemView.setFocusableInTouchMode(true);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        Picasso.with(context)
                .load(photo.getCardImageUrl())
                .placeholder(R.color.black_opaque)
                .error(mDefaultCardImage)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = Henson.with(context)
                    .gotoPhotoViewerActivity()
                    .photos(photos)
                    .position(position)
                    .build();
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
