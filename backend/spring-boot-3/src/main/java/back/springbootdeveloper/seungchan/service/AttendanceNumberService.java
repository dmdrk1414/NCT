package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceNumber;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.AttendanceNumberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceNumberService {

}
