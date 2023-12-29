package back.springbootdeveloper.seungchan.constant.regexp;

public enum RegexpConstant {
    PASSWORD("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");

    private String regexp;

    RegexpConstant(String regexp) {
        this.regexp = regexp;
    }

    public String get() {
        return regexp;
    }
}
