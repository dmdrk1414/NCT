package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberReqDto;
import back.springbootdeveloper.seungchan.dto.response.AttendancePassResDto;
import back.springbootdeveloper.seungchan.dto.response.AttendanceNumberResponse;
import back.springbootdeveloper.seungchan.entity.NumOfTodayAttendence;
import back.springbootdeveloper.seungchan.service.NumOfTodayAttendenceService;
import back.springbootdeveloper.seungchan.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<AttendancePassResDto> AttendanceIsPassController(@RequestBody @Valid AttendanceNumberReqDto attendanceNumberRequest, HttpServletRequest request) {
        String numOfAttendance = attendanceNumberRequest.getNumOfAttendance();
        Long id = tokenService.getUserIdFromToken(request);
        boolean passAttendance = numOfTodayAttendenceService.checkAttendanceNumber(numOfAttendance, id);

        return ResponseEntity.ok().body(new AttendancePassResDto(passAttendance));
    }

    @Operation(summary = "출석 번호 조회", description = "출석 번호: 랜덤의 4자리 번호 조회")
    @GetMapping("/find/number")
    public ResponseEntity<AttendanceNumberResponse> findAttendanceNumber() {
        NumOfTodayAttendence numOfTodayAttendence = numOfTodayAttendenceService.findNumOfTodayAttendenceAtNow();
        String attendanceNum = numOfTodayAttendence.getCheckNum();
        String dayAtNow = numOfTodayAttendence.getDay();
        return ResponseEntity.ok().body(AttendanceNumberResponse.builder()
                .attendanceNum(attendanceNum)
                .dayAtNow(dayAtNow)
                .build());
    }
}
