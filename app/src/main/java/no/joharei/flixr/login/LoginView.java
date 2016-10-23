package no.joharei.flixr.login;

import android.content.Context;

interface LoginView {
    Context getContext();

    void showProgress(boolean show);

    void getUserDetailsCompleted();
}
