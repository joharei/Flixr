package no.joharei.flixr;

import android.app.Application;
import android.content.Context;


public class MainApplication extends Application {
    private static MainApplication instance;
    private MainComponent mainComponent;

    public static Context getInstance() {
        return instance;
    }

    public static MainComponent component(Context context) {
        return ((MainApplication) context.getApplicationContext()).mainComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        mainComponent = DaggerMainComponent.builder()
                .build();
        instance = this;
    }
}
