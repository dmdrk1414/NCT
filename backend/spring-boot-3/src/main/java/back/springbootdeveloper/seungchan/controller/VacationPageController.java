package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.VacationCountRequest;
import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.FindsVacationResponse;
import back.springbootdeveloper.seungchan.dto.response.VacationsResponce;
import back.springbootdeveloper.seungchan.service.AttendanceService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "회원들의 휴가 신청", description = "회원분이 휴가를 신청을하고 기간을 신청을한다.")
    @PostMapping("/request")
    public ResponseEntity<BaseResponseBody> applyVacation(@RequestBody VacationRequest vacationRequest, HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        userUtillService.subVacationCount(userId, vacationRequest);
        attendanceService.updateVacationDate(userId, vacationRequest);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }


    @Operation(summary = "회원들의 휴가 날짜 조회", description = "회원들의 결석, 변경가능 휴가, 변경불가능 휴가을 나타낸다.")
    @GetMapping("")
    public ResponseEntity<VacationsResponce> findVacation(HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);

        VacationsResponce vacationsResponce = attendanceService.findVacations(userId);
        return ResponseEntity.ok().body(vacationsResponce);
    }

    @Operation(summary = "실장이 회원들에게 휴가 갯수 부여 가능", description = "실장들이 휴가의 갯수를 부여가능하다.")
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

    @Operation(summary = "휴가 신청 페이지 조회", description = "휴가를 신청할때 보여주는 페이지 구성화면 조회")
    @GetMapping("/request")
    public ResponseEntity<FindsVacationResponse> findsVacationRequest(HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);

        int cntVacation = userUtillService.cntVacation(userId);
        VacationsResponce vacationsResponce = attendanceService.findVacations(userId);
        FindsVacationResponse findsVacationResponse = new FindsVacationResponse(cntVacation, vacationsResponce);

        return ResponseEntity.ok().body(findsVacationResponse);
    }
}
