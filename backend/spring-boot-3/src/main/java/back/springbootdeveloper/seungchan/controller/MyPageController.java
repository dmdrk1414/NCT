package back.springbootdeveloper.seungchan.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "마이페이지", description = "마이페이지 관련 API")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/personal-info/{club_member_id}")
@RequiredArgsConstructor
public class MyPageController {


}
