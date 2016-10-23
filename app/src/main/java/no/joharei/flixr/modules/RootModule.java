package no.joharei.flixr.modules;


import dagger.Module;

@Module(
        includes = {
                MainModule.class,
                AndroidModule.class
        }
)
public class RootModule {
}
