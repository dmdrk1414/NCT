package back.springbootdeveloper.seungchan.util;

import back.springbootdeveloper.seungchan.filter.exception.judgment.NotLeaderException;
import back.springbootdeveloper.seungchan.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;

public class MyValidation {

  public static void isLeaderMember(TokenService tokenService, final HttpServletRequest request) {
    if (!tokenService.isNuriKing(request)) {
      throw new NotLeaderException();
    }
  }
}
