package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.NoticesWriteReqDto;
import back.springbootdeveloper.seungchan.dto.response.NoticeInformation;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.repository.NoticeRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

  private final NoticeRepository noticeRepository;

  /**
   * 모든 공지사항을 검색하고 이를 NoticeInformation 객체의 목록으로 변환하여 반환합니다.
   *
   * @return 공지사항을 나타내는 NoticeInformation 객체의 목록
   */
  public List<NoticeInformation> getNoticeInformations() {
    List<Notice> notices = noticeRepository.findAll();

    return notices.stream()
        .map(NoticeInformation::new)
        .toList();
  }

  /**
   * 주어진 DTO를 사용하여 새로운 공지사항을 저장합니다.
   *
   * @param noticesWriteReqDto 저장할 공지사항 정보를 담은 DTO
   * @return 저장된 공지사항
   */
  @Transactional
  public Notice save(final NoticesWriteReqDto noticesWriteReqDto) {
    Notice notice = noticesWriteReqDto.ofEntity();

    return noticeRepository.save(notice);
  }
}
