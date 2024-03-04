package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceWeekDate;
import back.springbootdeveloper.seungchan.entity.VacationToken;
import back.springbootdeveloper.seungchan.repository.AttendanceWeekDateRepository;
import back.springbootdeveloper.seungchan.repository.VacationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacationTokenService {

  private final VacationTokenRepository vacationTokenRepository;

  @Transactional
  public void save(final VacationToken vacationToken) {
    vacationTokenRepository.save(vacationToken);
  }
}
