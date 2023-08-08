package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.config.jwt.TokenProvider;
import back.springbootdeveloper.seungchan.domain.ObUser;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.dto.request.VacationCountRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.ObUserOfMainResponse;
import back.springbootdeveloper.seungchan.dto.response.UserOfDetail2MainResponse;
import back.springbootdeveloper.seungchan.dto.response.YbUserOfMainResponse;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserOfMainService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class MainController {
    private final UserService userServiceImp;
    private final UserUtilRepository userUtilRepository;
    private final UserOfMainService userOfMainService;
    private final UserUtillService userUtillService;
    private final TokenService tokenService;

    ///main/ybs
    @GetMapping("main/ybs")
    public ResponseEntity<List<YbUserOfMainResponse>> findAllYbUser() {
        boolean isObUser = false;
        List<YbUserOfMainResponse> list = userOfMainService.findAllByIsOb(isObUser);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("main/obs")
    public ResponseEntity<List<ObUserOfMainResponse>> findAllObUser(HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        boolean isNuriKing = userUtillService.isNuriKing(userId);

        List<ObUser> obUserList = userOfMainService.findAllObUser();

        return ResponseEntity.ok().body(Collections.singletonList(new ObUserOfMainResponse(obUserList, isNuriKing)));
    }

    @GetMapping("/main/detail/{id}")
    public ResponseEntity<UserOfDetail2MainResponse> fetchUserOfDetail2Main(HttpServletRequest request, @PathVariable long id) {
        Long userIdOfSearch = tokenService.getUserIdFromToken(request);
        User user = userServiceImp.findUserById(id);

        Long userId = user.getId();
        UserUtill userUtill = userUtilRepository.findByUserId(userIdOfSearch);

        UserOfDetail2MainResponse response = new UserOfDetail2MainResponse(userUtill, user);

        return ResponseEntity.ok().body(response);
    }

}
