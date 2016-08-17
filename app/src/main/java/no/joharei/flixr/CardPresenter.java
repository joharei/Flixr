/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package no.joharei.flixr;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import no.joharei.flixr.network.models.Contact;
import no.joharei.flixr.network.models.Photo;
import no.joharei.flixr.network.models.Photoset;

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class CardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    private static final int CARD_WIDTH = 313;
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
        Log.d(TAG, "onCreateViewHolder");

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
        Log.d(TAG, "onBindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
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
            cardView.setTitleText(contact.getRealname());
            Picasso.with(context)
                    .load(contact.getCardImageUrl())
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
