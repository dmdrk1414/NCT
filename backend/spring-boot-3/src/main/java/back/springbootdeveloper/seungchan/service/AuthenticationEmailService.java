package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.authentication.AuthenticationEmailMessageConstant;
import back.springbootdeveloper.seungchan.domain.TemporaryPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
@RequiredArgsConstructor
public class AuthenticationEmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String senderOfDeveloper;

    /**
     * 고객의 이메일에 이메일을 보내고 임시 비밀번호를 반환한다.
     *
     * @param emailOfClient 고객(클라이언트) email
     * @param email         고객(클라이언트)의 ID가 되는 email
     * @return
     */
    public String sendSimpleMessage(String emailOfClient, String email) {
        TemporaryPassword temporaryPassword = new TemporaryPassword();
        String tempPassword = temporaryPassword.generate();
        String subjectOfEmail = AuthenticationEmailMessageConstant.SUBJECT_OF_EMAIL();
        String contentOfEmail = AuthenticationEmailMessageConstant.CONTENT_OF_EMAIL(email, tempPassword);

        //메세지를 생성하고 보낼 메일 설정 저장
        SimpleMailMessage messageFormOfEmail = getMessageFormOfEmail(emailOfClient, senderOfDeveloper, contentOfEmail, subjectOfEmail);

        mailSender.send(messageFormOfEmail);
        return tempPassword;
    }

    /**
     * 메세지 서비스를 사용하기 위한 환경 설정
     *
     * @param emailOfClient     고객(클라이언트)의 이메일
     * @param senderOfDeveloper 보내는 개발자의 이메일
     * @param contentOfEmail    이메일 FORM의 내용
     * @param subjectOfEmail    이메일 FORM의 제목
     * @return
     */
    private SimpleMailMessage getMessageFormOfEmail(String emailOfClient, String senderOfDeveloper, String contentOfEmail, String subjectOfEmail) {
        SimpleMailMessage messageOfEmail = new SimpleMailMessage();
        messageOfEmail.setTo(emailOfClient); // 회원 이메일
        messageOfEmail.setFrom(senderOfDeveloper); // 계발자의 이메일
        messageOfEmail.setText(contentOfEmail); // 이메일 내용
        messageOfEmail.setSubject(subjectOfEmail); // 이메일 제목
        return messageOfEmail;
    }


    /**
     * 고객의 이메일에 찾고자하는 고객의 이메일을 보낸다.
     *
     * @param email
     */
    public void sendUserEmailMessage(String authenticationEmail, String email) {
        String subjectOfEmail = AuthenticationEmailMessageConstant.SUBJECT_OF_FIND_USER_EMAIL();
        String contentOfEmail = AuthenticationEmailMessageConstant.CONTENT_OF_FIND_USER_EMAIL(email);

        //메세지를 생성하고 보낼 메일 설정 저장
        SimpleMailMessage messageFormOfEmail = getMessageFormOfEmail(authenticationEmail, senderOfDeveloper, contentOfEmail, subjectOfEmail);

        mailSender.send(messageFormOfEmail);
    }
}
