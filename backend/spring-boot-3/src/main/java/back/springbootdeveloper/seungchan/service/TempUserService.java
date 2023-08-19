package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.domain.TempUser;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.dto.request.RequestUserForm;
import back.springbootdeveloper.seungchan.dto.request.TempUserFormRequest;
import back.springbootdeveloper.seungchan.repository.TempUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TempUserService {
    private final TempUserRepository tempUserRepository;

    public void save(TempUserFormRequest requestUserForm) {
        TempUser newTempUser = requestUserForm.toEntity();
        tempUserRepository.save(newTempUser);
    }
}
