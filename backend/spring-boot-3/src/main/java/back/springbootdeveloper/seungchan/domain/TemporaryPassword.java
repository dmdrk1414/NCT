package back.springbootdeveloper.seungchan.domain;

import back.springbootdeveloper.seungchan.constant.regexp.RegexpConstant;

import java.security.SecureRandom;

public class TemporaryPassword {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$@$!%*#?&";
    private static final int PASSWORD_LENGTH = 12; // 원하는 비밀번호 길이로 조정 가능

    /**
     * 정규식에 맞는 임시 비밀번호를 생성한다.
     *
     * @return
     */
    public String generate() {
        String temporaryPassword = getTempPassword();
        String regexPassword = RegexpConstant.PASSWORD.get();
        while (!temporaryPassword.matches(regexPassword)) {
            temporaryPassword = getTempPassword();
        }

        return temporaryPassword;
    }

    /**
     * 임시 비밀 번호을 생성한다.
     *
     * @return
     */
    private String getTempPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        while (password.length() < PASSWORD_LENGTH) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            password.append(randomChar);
        }

        return password.toString();
    }
}
