package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberRequest;
import back.springbootdeveloper.seungchan.dto.response.AttendanceNumberResponse;
import back.springbootdeveloper.seungchan.service.NumOfTodayAttendenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class AttendanceController {
    private final NumOfTodayAttendenceService numOfTodayAttendenceService;

    @PostMapping("/attendance/number")
    public ResponseEntity<AttendanceNumberResponse> AttendanceNumberController(@RequestBody AttendanceNumberRequest request) {
        String numOfAttendance = request.getNumOfAttendance();
        // userId = 1 :: park seungchan
        // TODO : take userId From token that front give to me
        Long id = 1L; // id (1) is park seung chan // 임시

        boolean passAttendance = numOfTodayAttendenceService.checkAttendanceNumber(numOfAttendance, id);

        return ResponseEntity.ok().body(new AttendanceNumberResponse(passAttendance));
    }
}
