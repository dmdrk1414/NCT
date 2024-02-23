package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.DormancysMembersResDto;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "club detail page 컨트롤러", description = "club datail page 컨트롤러 - 토큰 필요")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/informations/{club_id}/details")
public class ClubDetailPageController {

}