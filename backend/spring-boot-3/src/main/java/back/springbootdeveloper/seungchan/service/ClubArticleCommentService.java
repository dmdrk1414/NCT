package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.ClubMemberCommentsResDto;
import back.springbootdeveloper.seungchan.dto.response.MyClubArticleComment;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubArticleCommentRepository;
import back.springbootdeveloper.seungchan.repository.ClubArticleRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubArticleCommentService {

}
