package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.VacationCountRequest;
import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.VacationsResponce;
import back.springbootdeveloper.seungchan.service.AttendanceService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저들의 출석 API", description = "회원들의 출석, 요청, 조회, 휴가 부여 등 출석에 관한 API들의 모음")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/vacations")
public class VacationPageController {
    private final UserUtillService userUtillService;
    private final AttendanceService attendanceService;
    private final TokenService tokenService;

    @PostMapping("/request")
    public ResponseEntity<BaseResponseBody> applyVacation(@RequestBody VacationRequest vacationRequest, HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        userUtillService.subVacationCount(userId, vacationRequest);
        attendanceService.updateVacationDate(userId, vacationRequest);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }

    @GetMapping("")
    public ResponseEntity<VacationsResponce> findVacation(HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);

        VacationsResponce vacationsResponce = attendanceService.findVacations(userId);
        return ResponseEntity.ok().body(vacationsResponce);
    }

    @PostMapping("/count")
    public ResponseEntity<BaseResponseBody> vacationCount(@RequestBody VacationCountRequest vacationCountRequest, HttpServletRequest request) {
        boolean isNuriKing = tokenService.getNuriKingFromToken(request);
        if (isNuriKing) {
            Long userId = vacationCountRequest.getUserId();
            int vacationNumWantAdd = vacationCountRequest.getVacationCount();
            userUtillService.addVacationConunt(userId, vacationNumWantAdd);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess();
        }
        return BaseResponseBodyUtiil.BaseResponseBodyForbidden();
    }
}
