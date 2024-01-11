package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.domain.ObUser;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.domain.YbUserInfomation;
import back.springbootdeveloper.seungchan.repository.AttendanceStatusRepository;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class UserOfMainService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttendanceStatusRepository attendanceStatusRepository;
    @Autowired
    private UserUtilRepository userUtilRepository;

    /**
     * isOb을 이용해 현재 인원들의 정보를 찾는다.
     * 향후 수정
     *
     * @param isOb 현재회원 구분
     * @return
     */
    public List<YbUserInfomation> findAllByIsOb(boolean isOb) {
        List<YbUserInfomation> responseList = new ArrayList<>();
        List<UserInfo> users = userRepository.findAll();

        for (UserInfo user : users) {
            boolean isObUser = user.isOb();
            if (isObUser == isOb) {
                Long longId = (long) user.getId();
                UserUtill userUtill = userUtilRepository.findByUserId(longId);
                AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(longId);
                UserInfo userInfo = userRepository.findById(longId).orElseThrow(() -> new IllegalArgumentException(String.valueOf((longId))));
                responseList.add(new YbUserInfomation(userInfo, attendanceStatus, userUtill));
            }
        }
        return responseList;
    }

    public List<ObUser> findAllObUser() {
        List<ObUser> responseList = new ArrayList<>();
        List<UserInfo> users = userRepository.findAll();
        for (int id = 1; id < users.size() + 1; id++) {
            boolean isObUser = users.get(id - 1).isOb();
            if (isObUser) {
                int indexOfuser = id - 1;
                UserInfo user = users.get(indexOfuser);
                responseList.add(new ObUser(user));
            }
        }
        return responseList;
    }

}
