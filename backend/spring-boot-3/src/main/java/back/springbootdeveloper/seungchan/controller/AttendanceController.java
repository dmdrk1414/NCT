package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberRequest;
import back.springbootdeveloper.seungchan.dto.response.AttendanceNumberResponse;
import back.springbootdeveloper.seungchan.service.NumOfTodayAttendenceService;
import back.springbootdeveloper.seungchan.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "출석 체크 API", description = "결석, 방학, 주간 출석관련 API을 관리하는 controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/attendance")
public class AttendanceController {
    private final NumOfTodayAttendenceService numOfTodayAttendenceService;
    private final TokenService tokenService;

    @Operation(summary = "main page 5. 출석 번호 입력 API ", description = "출석 번호를 입력을 하면 출석하였다는 결과를 얻는다.")
    @PostMapping("/number")
    public ResponseEntity<AttendanceNumberResponse> AttendanceNumberController(@RequestBody AttendanceNumberRequest attendanceNumberRequest, HttpServletRequest request) {
        String numOfAttendance = attendanceNumberRequest.getNumOfAttendance();
        Long id = tokenService.getUserIdFromToken(request);

        boolean passAttendance = numOfTodayAttendenceService.checkAttendanceNumber(numOfAttendance, id);

        return ResponseEntity.ok().body(new AttendanceNumberResponse(passAttendance));
    }
}
