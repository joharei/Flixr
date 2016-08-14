package no.joharei.flixr.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import no.joharei.flixr.R;
import no.joharei.flixr.network.models.Photo;


public class PhotoViewAdapter extends PagerAdapter {

    private final ArrayList<Photo> photos;

    public PhotoViewAdapter(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("PhotoViewAdapter", "instantiateItem");
        Context context = container.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemLayout = inflater.inflate(R.layout.fullscreen_image, container, false);

        Photo photo = photos.get(position);
        ImageView imageView = (ImageView) itemLayout.findViewById(R.id.image);
        TextView textView = (TextView) itemLayout.findViewById(R.id.title);
        textView.setText(photo.getTitle());
        Log.d("PhotoViewAdapter", "Getting image: " + photo.getFullscreenImageUrl());
        Picasso.with(context)
                .load(photo.getFullscreenImageUrl())
                .into(imageView);

        container.addView(itemLayout);
        return itemLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }
}
