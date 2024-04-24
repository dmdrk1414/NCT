package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.NoticesReqDto;
import back.springbootdeveloper.seungchan.dto.response.NoticeInformation;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.NoticeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
  public Notice save(final NoticesReqDto noticesWriteReqDto) {
    Notice notice = noticesWriteReqDto.ofEntity();

    return noticeRepository.save(notice);
  }

  /**
   * 주어진 공지사항 ID에 해당하는 공지를 삭제합니다.
   *
   * @param noticeId 삭제할 공지사항의 ID
   * @return 삭제 작업이 성공했는지 여부를 나타내는 Boolean 값입니다. 삭제 작업이 성공했으면 true를 반환하고, 그렇지 않으면 false를 반환합니다.
   */
  @Transactional
  public Boolean delete(final Long noticeId) {

    try {
      noticeRepository.findById(noticeId).orElseThrow(NotFoundException::new);
      noticeRepository.deleteById(noticeId);
    } catch (NotFoundException e) {
      return false;
    }
    return true;
  }

  public boolean update(final Long noticeId) {
    Notice target = noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new);

  }
}
