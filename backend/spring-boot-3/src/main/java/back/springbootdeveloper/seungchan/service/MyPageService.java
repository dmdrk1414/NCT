package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS;
import back.springbootdeveloper.seungchan.dto.response.MyAllClubMembersAttendance;
import back.springbootdeveloper.seungchan.dto.response.MyAttendanceCount;
import back.springbootdeveloper.seungchan.dto.response.MyAttendanceState;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final VacationTokenRepository vacationTokenRepository;
    private final AttendanceStateRepository attendanceStateRepository;
    private final AttendanceWeekDateRepository attendanceWeekDateRepository;
    private final AttendanceWeekRepository attendanceWeekRepository;
    private final ClubControlRepository clubControlRepository;


    public List<MyAllClubMembersAttendance> getMyAllClubMembersAttendance(Long memberId, Long clubMemberId) {
        List<MyAllClubMembersAttendance> myAllClubMembersAttendances = new ArrayList<>();
        List<ClubMember> clubMembers = clubMemberRepository.findAllByMemberId(memberId);

        for (ClubMember clubMember : clubMembers) {
            Club club = clubRepository.findById(clubMember.getClubId()).orElseThrow(EntityNotFoundException::new);
            AttendanceState attendanceState = attendanceStateRepository.findById(clubMember.getClubMemberId()).orElseThrow(EntityNotFoundException::new);
            VacationToken vacationToken = attendanceState.getVacationToken();
            // 역정렬을 해서 최근정보 가져오기.
            List<AttendanceWeekDate> attendanceWeekDates = getLastAttendanceWeekDate(attendanceState);

            String attendanceCount = getCountOfAttendanceWeekDates(attendanceWeekDates, ATTENDANCE_STATE.ATTENDANCE, club);
            String vacationCount = getCountOfAttendanceWeekDates(attendanceWeekDates, ATTENDANCE_STATE.VACATION, club);
            String absenceCount = getCountOfAttendanceWeekDates(attendanceWeekDates, ATTENDANCE_STATE.ABSENCE, club);
            String totalCount = getTotalCount(attendanceCount, vacationCount, absenceCount);

            String clubName = club.getClubName();
            Integer vacationTokenCount = vacationToken.getVacationToken();
            MyAttendanceState myAttendanceState = new MyAttendanceState(attendanceWeekDates.get(0));
            MyAttendanceCount myAttendanceCount = MyAttendanceCount.builder()
                    .attendance(attendanceCount)
                    .vacation(vacationCount)
                    .absence(absenceCount)
                    .totalCount(totalCount)
                    .build();
            MyAllClubMembersAttendance myAllClubMembersAttendance = MyAllClubMembersAttendance.builder()
                    .clubName(clubName)
                    .vacationToken(vacationTokenCount)
                    .myAttendanceState(myAttendanceState)
                    .myAttendanceCount(myAttendanceCount)
                    .build();
            myAllClubMembersAttendances.add(myAllClubMembersAttendance);
        }

        return myAllClubMembersAttendances;
    }

    private String getTotalCount(String attendanceCount, String vacationCount, String absenceCount) {
        Integer totalCount = Integer.valueOf(attendanceCount) + Integer.valueOf(vacationCount) + Integer.valueOf(absenceCount);

        return String.valueOf(totalCount);
    }

    private String getCountOfAttendanceWeekDates(List<AttendanceWeekDate> attendanceWeekDates, ATTENDANCE_STATE attendanceState, Club club) {
        ClubControl clubControl = club.getClubControl();
        AttendanceWeek attendanceWeek = clubControl.getAttendanceWeek();
        Integer count = 0;
        for (AttendanceWeekDate attendanceWeekDate : attendanceWeekDates) {
            if (attendanceWeekDate.getMonday() == attendanceState && isPossibleAttendance(attendanceWeek.getMonday(), POSSIBLE_STATUS.POSSIBLE)) {
                count = count + 1;
            }
            if (attendanceWeekDate.getTuesday() == attendanceState && isPossibleAttendance(attendanceWeek.getTuesday(), POSSIBLE_STATUS.POSSIBLE)) {
                count = count + 1;
            }
            if (attendanceWeekDate.getWednesday() == attendanceState && isPossibleAttendance(attendanceWeek.getWednesday(), POSSIBLE_STATUS.POSSIBLE)) {
                count = count + 1;
            }
            if (attendanceWeekDate.getThursday() == attendanceState && isPossibleAttendance(attendanceWeek.getThursday(), POSSIBLE_STATUS.POSSIBLE)) {
                count = count + 1;
            }
            if (attendanceWeekDate.getFriday() == attendanceState && isPossibleAttendance(attendanceWeek.getFriday(), POSSIBLE_STATUS.POSSIBLE)) {
                count = count + 1;
            }
            if (attendanceWeekDate.getSaturday() == attendanceState && isPossibleAttendance(attendanceWeek.getSaturday(), POSSIBLE_STATUS.POSSIBLE)) {
                count = count + 1;
            }
            if (attendanceWeekDate.getSunday() == attendanceState && isPossibleAttendance(attendanceWeek.getSunday(), POSSIBLE_STATUS.POSSIBLE)) {
                count = count + 1;
            }
        }
        return String.valueOf(count);
    }

    private boolean isPossibleAttendance(POSSIBLE_STATUS targetPossibleStatus, POSSIBLE_STATUS possibleStatus) {
        return targetPossibleStatus == possibleStatus;
    }

    private List<AttendanceWeekDate> getLastAttendanceWeekDate(AttendanceState attendanceState) {
        List<AttendanceWeekDate> attendanceWeekDates = attendanceState.getAttendanceWeekDates();
        Collections.reverse(attendanceWeekDates);
        return attendanceWeekDates;
    }
}
