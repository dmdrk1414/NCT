package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.service.AttendanceService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class VacationPageController {
    private final UserUtillService userUtillService;
    private final AttendanceService attendanceService;

    @PostMapping("/vacations/request")
    public ResponseEntity<BaseResponseBody> applyVacation(@RequestBody VacationRequest vacationRequest) {
        Long userId = 1L;
        userUtillService.subVacationCount(userId, vacationRequest);
        attendanceService.updateVacationDate(userId, vacationRequest);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }
}
