package back.springbootdeveloper.seungchan.testutills;

import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.repository.NoticeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestSaveEntity {

  @Autowired
  private NoticeRepository noticeRepository;

  public void creatEntityTest() throws Exception {
    // Notice Entity
    createNoticeEntity();

  }

  /**
   * Notice Entity
   */
  private void createNoticeEntity() {
    List<Notice> notices = new ArrayList<>();
    for (int i = 1; i <= 100; i++) {
      notices.add(
          Notice.builder()
              .title("테스트 공지사항 제목_" + i)
              .content("테스트 공지사항 내용_" + i)
              .build()
      );
    }

    noticeRepository.saveAll(notices);
  }
}
