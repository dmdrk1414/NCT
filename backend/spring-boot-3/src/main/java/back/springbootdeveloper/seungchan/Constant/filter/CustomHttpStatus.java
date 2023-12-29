package back.springbootdeveloper.seungchan.Constant.filter;

import org.springframework.http.HttpStatus;

public enum CustomHttpStatus {
    WEEKEND(-480, "Weekend");

    private final int value;


    private final String reasonPhrase;

    CustomHttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
