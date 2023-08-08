package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.VacationCountRequest;
import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.VacationsResponce;
import back.springbootdeveloper.seungchan.service.AttendanceService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class VacationPageController {
    private final UserUtillService userUtillService;
    private final AttendanceService attendanceService;
    private final TokenService tokenService;

    @PostMapping("/vacations/request")
    public ResponseEntity<BaseResponseBody> applyVacation(@RequestBody VacationRequest vacationRequest, HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        userUtillService.subVacationCount(userId, vacationRequest);
        attendanceService.updateVacationDate(userId, vacationRequest);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }

    @GetMapping("/vacations")
    public ResponseEntity<VacationsResponce> findVacation(HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);

        VacationsResponce vacationsResponce = attendanceService.findVacations(userId);
        return ResponseEntity.ok().body(vacationsResponce);
    }

    @PostMapping("/vacation/count")
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
