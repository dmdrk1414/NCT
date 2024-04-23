package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.NoticesWriteReqDto;
import back.springbootdeveloper.seungchan.dto.response.NoticeInformation;
import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.repository.NoticeRepository;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import back.springbootdeveloper.seungchan.service.AttendanceService;
import back.springbootdeveloper.seungchan.service.AttendanceTimeService;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest()
@AutoConfigureMockMvc
class NoticeControllerTest {

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private DatabaseService databaseService;
  @Autowired
  private TestSetUp testSetUp;
  @Autowired
  private UserService userService;
  @Autowired
  private NoticeRepository noticeRepository;
  private String token;
  private Long kingUserId;
  private UserInfo kingUser;

  @BeforeEach
  void setUp() {
    databaseService.deleteAllDatabase();
    token = testSetUp.getToken(mockMvc);
    kingUserId = testSetUp.getKingUserId();
    kingUser = userService.findUserById(kingUserId);
  }

  @Test
  void 공지_사항_등록_테스트() throws Exception {
    final String url = "/control/notices/write";

    // 검증을 위한 데이터 준비
    final String testNoticeTitle = "테스트 공지 사항 제목";
    final String testNoticeContent = "테스트 공지 사항 내용";
    NoticesWriteReqDto requestDto = NoticesWriteReqDto.builder()
        .noticeTitle(testNoticeTitle)
        .noticeContent(testNoticeContent)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(post(url)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody)
        .header("authorization", "Bearer " + token) // token header에 담기
    );
    Notice target = noticeRepository.findAll().get(0);

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.SUCCESS_WRITE_NOTICE.get()))
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(target.getTitle()).isEqualTo(testNoticeTitle);
    assertThat(target.getContent()).isEqualTo(testNoticeContent);
  }

  @Test
  void 공지_사항_삭제_테스트() throws Exception {
    System.out.println(this.noticeRepository.count());
    final String url = "/control/notices/{notice_id}";
    boolean resultDelete = false;

    // 검증을 위한 데이터 준비
    final String testNoticeTitle = "테스트 공지 사항 제목";
    final String testNoticeContent = "테스트 공지 사항 내용";
    Notice testNotice = Notice.builder()
        .title(testNoticeTitle)
        .content(testNoticeContent)
        .build();
    Notice targetNotice = noticeRepository.save(testNotice);

    ResultActions result = mockMvc.perform(delete(url, targetNotice.getNoticeId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    try {
      noticeRepository.findById(targetNotice.getNoticeId()).orElseThrow(NotFoundException::new);
    } catch (Exception e) {
      resultDelete = true;
    }

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.SUCCESS_DELETE_NOTICE.get()))
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    assertThat(resultDelete).isTrue();
  }
}