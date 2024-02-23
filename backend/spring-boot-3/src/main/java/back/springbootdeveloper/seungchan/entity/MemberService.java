package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Member findMemberById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(); // TODO: Exception
    }

    // 로그인 시 토큰 조회를 위한 함수
    // email이 없는 경우에는 새로운 member를 만들어야 하므로 orElse를 null로 처리
    public Member findByEmailForJwtToken(String email){
        return memberRepository.findByEmail(email).orElse(null);
    }

    // TODO: Error Handling
    public Member findByEmail(String email){
        return  memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email Does Not Exist"));
    }

    public Member createMemberByEmail(String email){
        return memberRepository.save(new Member(email));
    }
}
