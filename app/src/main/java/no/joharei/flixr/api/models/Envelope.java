package no.joharei.flixr.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Envelope<T> {
    private String stat;
    private int code;
    private String message;
    @SerializedName(value = "data", alternate = {"photosets", "photoset", "contacts", "user"})
    private T data;

    public String getStat() {
        return stat;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static class FailureException extends RuntimeException {
        final String stat;
        final int code;
        final String message;

        public FailureException(String stat, int code, String message) {
            super(String.format(Locale.getDefault(), "Stat: %s, code: %d, message: %s", stat, code, message));
            this.stat = stat;
            this.code = code;
            this.message = message;
        }
    }
}
