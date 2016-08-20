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

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/*
 * Details activity class that loads LeanbackDetailsFragment class
 */
public class PhotosetActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Intent intent = getIntent();
            if (intent.hasExtra(PhotosetFragment.PHOTOSET_ID)) {
                long photosetId = intent.getLongExtra(PhotosetFragment.PHOTOSET_ID, -1);
                String userId = intent.getStringExtra(PhotosetFragment.USER_ID);
                fragment = PhotosetFragment.newInstance(photosetId, userId);
            } else if (intent.hasExtra(PhotosetsFragment.USER_ID)) {
                String userId = intent.getStringExtra(PhotosetsFragment.USER_ID);
                fragment = PhotosetsFragment.newInstance(userId);
            }
            if (fragment != null) {
                getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
            }
        }
    }

}
