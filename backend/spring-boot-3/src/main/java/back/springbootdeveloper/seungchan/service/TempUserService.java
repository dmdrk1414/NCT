package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.domain.TempUser;
import back.springbootdeveloper.seungchan.dto.request.TempUserFormRequest;
import back.springbootdeveloper.seungchan.dto.response.NewUserEachResponse;
import back.springbootdeveloper.seungchan.dto.response.NewUsersResponse;
import back.springbootdeveloper.seungchan.repository.TempUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TempUserService {
    private final TempUserRepository tempUserRepository;

    public void save(TempUserFormRequest requestUserForm) {
        TempUser newTempUser = requestUserForm.toEntity();
        tempUserRepository.save(newTempUser);
    }

    public List<NewUsersResponse> findAllNewUsers() {
        List<NewUsersResponse> newUsersResponseList = new ArrayList<>();
        List<TempUser> tempUserList = tempUserRepository.findAll();
        for (TempUser tempUser : tempUserList) {
            Long id = tempUser.getId();
            String name = tempUser.getName();
            newUsersResponseList.add(new NewUsersResponse(id, name));
        }
        return newUsersResponseList;
    }

    public TempUser findNewUsers(long id) {
        return tempUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected new user"));
    }
}
