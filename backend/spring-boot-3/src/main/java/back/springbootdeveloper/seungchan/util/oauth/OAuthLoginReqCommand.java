package back.springbootdeveloper.seungchan.util.oauth;

public interface OAuthLoginReqCommand<T> {

    public T makeOAuthTokenReqBody(String authCode);
}
