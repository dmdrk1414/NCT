package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOfDetail2MainResponse {
    //    "name": "박승찬",
//    "major": "컴퓨터공학과",
//    "studentId": "20161822",
//    "phoneNum": "010-2383-6578",
//    "hobby": "농구",
//    "specialtySkill": "잠자기",
//    "MBTI": "ENTP",
//    "isNuriKing": true
    private String name;
    private String major;
    private String studentId;
    private String phoneNum;
    private String hobby;
    private String specialtySkill;
    private String mbti;
    private boolean isNuriKing;
    private boolean isOb;
    private Long userId;

    public UserOfDetail2MainResponse(UserUtill userUtill, UserInfo user) {
        this.name = user.getName();
        this.major = user.getMajor();
        this.studentId = user.getStudentId();
        this.phoneNum = user.getPhoneNum();
        this.hobby = user.getHobby();
        this.specialtySkill = user.getSpecialtySkill();
        this.mbti = user.getMbti();
        this.isNuriKing = userUtill.isNuriKing();
        this.isOb = user.isOb();
        this.userId = user.getId();
    }
}
