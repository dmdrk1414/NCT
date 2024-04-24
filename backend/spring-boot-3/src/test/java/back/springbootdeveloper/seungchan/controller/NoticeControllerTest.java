package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.NoticesReqDto;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.repository.NoticeRepository;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    NoticesReqDto requestDto = NoticesReqDto.builder()
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

  @Test
  void 공지_사항_삭제_예외_존제하지_않는_공지_삭제_테스트() throws Exception {
    final String url = "/control/notices/{notice_id}";

    // 검증을 위한 데이터 준비
    int notExistNoticeId = noticeRepository.findAll().size() + 1;

    ResultActions result = mockMvc.perform(delete(url, notExistNoticeId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_DELETE_NOTICE.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }


  @Test
  void 공지_사항_업데이트_테스트() throws Exception {
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
    // 업데이트 검증 데이터 준비
    final String testUpdateTitle = "테스트 업데이트 공지 사항 제목";
    final String testUpdateContent = "테스트 업데이트 공지 사항 내용";
    NoticesReqDto requestDto = NoticesReqDto.builder()
        .noticeTitle(testUpdateTitle)
        .noticeContent(testUpdateContent)
        .build();

    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(put(url, targetNotice.getNoticeId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    Notice target = noticeRepository.findById(targetNotice.getNoticeId())
        .orElseThrow(NotFoundException::new);

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.SUCCESS_UPDATE_NOTICE.get()))
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    assertThat(target.getTitle()).isEqualTo(testUpdateTitle);
    assertThat(target.getContent()).isEqualTo(testUpdateContent);
  }

  @Test
  void 공지_사항_업데이트_예외_존제하지_않는_공지_업데이트_테스트() throws Exception {
    final String url = "/control/notices/{notice_id}";

    // 검증을 위한 데이터 준비
    int notExistNoticeId = noticeRepository.findAll().size() + 1;

    final String testUpdateTitle = "테스트 업데이트 공지 사항 제목";
    final String testUpdateContent = "테스트 업데이트 공지 사항 내용";
    NoticesReqDto requestDto = NoticesReqDto.builder()
        .noticeTitle(testUpdateTitle)
        .noticeContent(testUpdateContent)
        .build();

    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(put(url, notExistNoticeId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("authorization", "Bearer " + token) // token header에 담기
        .content(requestBody)
    );

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_UPDATE_NOTICE.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }
}