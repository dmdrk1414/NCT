package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.ClubRegistrationReqDto;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubRegistrationService {
    private final ClubRepository clubRepository;
    @Transactional
    public void save(ClubRegistrationReqDto clubRegistrationReqDto) {
    }
}
