package back.springbootdeveloper.seungchan.constant;

import lombok.Getter;

@Getter
public enum DateConstants {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4);

    private final Integer index;

    DateConstants(Integer index) {
        this.index = index;
    }
}
