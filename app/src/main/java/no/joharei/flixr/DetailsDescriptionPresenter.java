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

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import no.joharei.flixr.network.models.Photoset;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        Photoset photoset = (Photoset) item;

        if (photoset != null) {
            viewHolder.getTitle().setText(photoset.getTitle().getContent());
            viewHolder.getBody().setText(photoset.getDescription().getContent());
        }
    }
}
