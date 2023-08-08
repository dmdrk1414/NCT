package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberRequest;
import back.springbootdeveloper.seungchan.dto.response.AttendanceNumberResponse;
import back.springbootdeveloper.seungchan.service.NumOfTodayAttendenceService;
import back.springbootdeveloper.seungchan.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class AttendanceController {
    private final NumOfTodayAttendenceService numOfTodayAttendenceService;
    private final TokenService tokenService;

    @PostMapping("/attendance/number")
    public ResponseEntity<AttendanceNumberResponse> AttendanceNumberController(@RequestBody AttendanceNumberRequest attendanceNumberRequest, HttpServletRequest request) {
        String numOfAttendance = attendanceNumberRequest.getNumOfAttendance();
        Long id = tokenService.getUserIdFromToken(request);

        boolean passAttendance = numOfTodayAttendenceService.checkAttendanceNumber(numOfAttendance, id);

        return ResponseEntity.ok().body(new AttendanceNumberResponse(passAttendance));
    }
}
