package no.joharei.flixr;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import no.joharei.flixr.api.models.Photo;
import no.joharei.flixr.api.models.Photoset;
import no.joharei.flixr.mainpage.models.Contact;

public class CardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    private static final int CARD_HEIGHT = 176;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        final Context context = parent.getContext();
        sDefaultBackgroundColor = ContextCompat.getColor(context, R.color.default_background);
        sSelectedBackgroundColor = ContextCompat.getColor(context, R.color.selected_background);
        mDefaultCardImage = ContextCompat.getDrawable(context, R.drawable.movie);

        ImageCardView cardView = new ImageCardView(context) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        cardView.setMainImageDimensions(ViewGroup.LayoutParams.WRAP_CONTENT, CARD_HEIGHT);
        Context context = viewHolder.view.getContext();
        if (item instanceof Photoset) {
            Photoset photoset = (Photoset) item;
            cardView.setTitleText(photoset.getTitle().getContent());
            cardView.setContentText(photoset.getDescription().getContent());
            Picasso.with(context)
                    .load(photoset.getCardImageUrl())
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        } else if (item instanceof Photo) {
            Photo photo = (Photo) item;
            cardView.setTitleText(photo.getTitle());
            Picasso.with(context)
                    .load(photo.getCardImageUrl())
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        } else if (item instanceof Contact) {
            Contact contact = (Contact) item;
            cardView.setTitleText(!(contact.getRealname() == null || contact.getRealname().isEmpty()) ?
                    contact.getRealname() :
                    contact.getUsername());
            Log.d(TAG, "Contact photo: " + contact.getCardImageUrl());
            Picasso.with(context)
                    .load(contact.getCardImageUrl())
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
