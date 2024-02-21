package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.service.ImageService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "시간표 자동 등록 페이지 API", description = "시간표 자동 등록을 하여 OCR 적용 한다.")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs")
public class ScheduleController {
    private final ImageService imageService;

    @Autowired
    public ScheduleController(ImageService imageService) {
        this.imageService = imageService;
    }

    // @RequestPart 는 name 속성을 통해 key 값을 지정해 바인딩 해줄 수 있는데,
    // 지정하지 않으면 디폴트로 변수명을 key 값으로 갖기 때문에 특별히 이유가 있지 않다면 따로 지정해주지 않아도 된다.
    @PostMapping(value = "/{club_id}/schedules/{club_member_id}/manual")
    public ResponseEntity<BaseResponseBody> RegisterTimeTableImage(@PathVariable(value = "club_id") Long clubId, @PathVariable(value = "club_member_id") Long clubMemberId, @RequestParam(value = "scheduleImage") MultipartFile scheduleImage) {
//        imageService.uploadImage(scheduleImage);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.REGISTRATION_TIMETABLE_COMPLETE.get());
    }
}
