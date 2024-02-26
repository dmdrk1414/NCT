package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.ClubRegistrationReqDto;
import back.springbootdeveloper.seungchan.dto.request.FindPasswordReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.service.ClubRegistrationService;
import back.springbootdeveloper.seungchan.service.EntityService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "스터디 팀을 등록하는 컨트롤러", description = "팀이름, 팀소개, 팀프로필 사진, 팀 소개사진을 입력받는다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/main/club/apply")
public class ClubRegistrationController {
    private final ClubRegistrationService clubRegistrationService;
    private final EntityService entityService;

    @Operation(summary = "팀 등록 api ")
    @PostMapping("")
    public ResponseEntity<BaseResponseBody> getClubRegistraionInfo(@RequestBody @Valid ClubRegistrationReqDto clubRegistrationReqDto, HttpServletRequest request) {
        this.entityService.applyClub();

        clubRegistrationService.save(clubRegistrationReqDto);
        return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.TEMP_PASSWORD_MESSAGE.get());
    }

    @GetMapping ("")
    public ResponseEntity<BaseResponseBody> cas() {
        this.entityService.applyClub();

        return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.TEMP_PASSWORD_MESSAGE.get());
    }
}
