package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.FindPasswordReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원들의 정보를 찾는 컨트롤러", description = "회원들의 정보를 입력하고, 정보를 찾는 기능들의 모임")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/admin")
public class LookupController {

}
