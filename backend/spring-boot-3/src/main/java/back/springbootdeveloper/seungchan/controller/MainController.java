package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.ObUser;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.dto.request.VacationCountRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.ObUserOfMainResponse;
import back.springbootdeveloper.seungchan.dto.response.UserOfDetail2MainResponse;
import back.springbootdeveloper.seungchan.dto.response.YbUserOfMainResponse;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import back.springbootdeveloper.seungchan.service.UserOfMainService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
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

    ///main/ybs
    @GetMapping("main/ybs")
    public ResponseEntity<List<YbUserOfMainResponse>> findAllYbUser() {
        boolean isObUser = false;
        List<YbUserOfMainResponse> list = userOfMainService.findAllByIsOb(isObUser);
        return ResponseEntity.ok().body(list);
    }


    @GetMapping("main/obs")
    public ResponseEntity<List<ObUserOfMainResponse>> findAllObUser() {
        //         TODO Token을 받아서 현재 유저의 정보를 가져와야된다.
        // 1. i will take token from front
        // 2. i have to get a user Information from token of user
        // 3. so... resulte that we get a user_ID from token
        Long userTempID = Long.valueOf(1); // user_id is ID from user DB
        boolean isNuriKing = userUtillService.isNuriKing(userTempID);

        List<ObUser> obUserList = userOfMainService.findAllObUser();

        return ResponseEntity.ok().body(Collections.singletonList(new ObUserOfMainResponse(obUserList, isNuriKing)));
    }

    @GetMapping("/main/detail")
    public ResponseEntity<UserOfDetail2MainResponse> fetchUserOfDetail2Main() {

//         TODO Token을 받아서 현재 유저의 정보를 가져와야된다.
        // 1. i will take token from front
        // 2. i have to get a user Information from token of user
        // 3. so... resulte that we get a user_ID from token
        Long userTempID = Long.valueOf(1); // user_id is ID from user DB
        User user = userServiceImp.findUserById(userTempID);

        Long userId = user.getId();
        UserUtill userUtill = userUtilRepository.findByUserId(userId);

        UserOfDetail2MainResponse response = new UserOfDetail2MainResponse(userUtill, user);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/vacation/count")
    public ResponseEntity<BaseResponseBody> vacationCount(@RequestBody VacationCountRequest vacationCountRequest) {
        // TODO 토큰으로 실장인지 아닌지 확인
        // TODO vacation Count
        Long userId = vacationCountRequest.getUserId();
        int vacationNumWantAdd = vacationCountRequest.getVacationCount();
        userUtillService.addVacationConunt(userId, vacationNumWantAdd);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }
}
