package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.service.NoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "", description = "content")
@RestController
@RequestMapping("/all-url")
@RequiredArgsConstructor
public class NoticeController {

  private final NoticeService noticeService;
}
