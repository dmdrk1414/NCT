import { axAuth } from '@/apis/axiosinstance';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { useRouter } from 'next/navigation';
import { InputControlTimeFrom } from '../atom/input-control-time-form';
import { formatNumberWithLeadingZero, validateInputAttendanceTime } from '@/utils/validate/numberValidate';

type data = {
  name: string;
  isKing: boolean;
  userId: number;
  setIsMemberInfoOpen: (state: number) => void;
};

export default function UserListOnControl({ name, isKing, userId, setIsMemberInfoOpen }: data) {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [isAttendanceModalOpen, setIsAttendanceModalOpen] = useState(false);
  const [isExceptionAttendance, setIsExceptionAttendance] = useState(false);
  const [attendanceTimeData, setAttendanceTimeData] = useState({
    // get으로 얻는 현재 출석 정보
    mondayAttendanceTime: '',
    tuesdayAttendanceTime: '',
    wednesdayAttendanceTime: '',
    thursdayAttendanceTime: '',
    fridayAttendanceTime: '',
  });
  const attendanceTimeCss = 'font-semibold flex justify-around bg-[#ececec] w-[15vw]  rounded-lg ';
  const [hopeAttendanceTimes, setHopeAttendanceTimes] = useState({
    // 희망하는 출석 시간 번호
    newMondayAttendanceTime: '',
    newTuesdayAttendanceTime: '',
    newWednesdayAttendanceTime: '',
    newThursdayAttendanceTime: '',
    newFridayAttendanceTime: '',
  });

  const handleMondayAttendanceTimeChange = (newMondayAttendanceTime: string) => {
    // 월요일 출석 시간 핸들러
    let formatTime = attendanceTimeData.mondayAttendanceTime;

    if (validateInputAttendanceTime(newMondayAttendanceTime)) {
      formatTime = formatNumberWithLeadingZero(newMondayAttendanceTime);

      setHopeAttendanceTimes({
        ...hopeAttendanceTimes,
        newMondayAttendanceTime: formatTime,
      });
    }
  };

  const handleTuesdayAttendanceTimeChange = (newTuesdayAttendanceTime: string) => {
    // 화요일 출석 시간 핸들러
    let formatTime = attendanceTimeData.tuesdayAttendanceTime;
    if (validateInputAttendanceTime(newTuesdayAttendanceTime)) {
      formatTime = formatNumberWithLeadingZero(newTuesdayAttendanceTime);

      setHopeAttendanceTimes({
        ...hopeAttendanceTimes,
        newTuesdayAttendanceTime: formatTime,
      });
    }
  };

  const handleWednesdayAttendanceTimeChange = (newWednesdayAttendanceTime: string) => {
    // 수요일 출석 시간 핸들러
    let formatTime = attendanceTimeData.wednesdayAttendanceTime;
    if (validateInputAttendanceTime(newWednesdayAttendanceTime)) {
      formatTime = formatNumberWithLeadingZero(newWednesdayAttendanceTime);

      setHopeAttendanceTimes({
        ...hopeAttendanceTimes,
        newWednesdayAttendanceTime: formatTime,
      });
    }
  };

  const handleThursdayAttendanceTimeChange = (newThursdayAttendanceTime: string) => {
    // 목요일 출석 시간 핸들러
    let formatTime = attendanceTimeData.thursdayAttendanceTime;
    if (validateInputAttendanceTime(newThursdayAttendanceTime)) {
      formatTime = formatNumberWithLeadingZero(newThursdayAttendanceTime);

      setHopeAttendanceTimes({
        ...hopeAttendanceTimes,
        newThursdayAttendanceTime: formatTime,
      });
    }
  };

  const handleFridayAttendanceTimeChange = (newFridayAttendanceTime: string) => {
    // 금요일 출석 시간 핸들러
    let formatTime = attendanceTimeData.fridayAttendanceTime;
    if (validateInputAttendanceTime(newFridayAttendanceTime)) {
      formatTime = formatNumberWithLeadingZero(newFridayAttendanceTime);

      setHopeAttendanceTimes({
        ...hopeAttendanceTimes,
        newFridayAttendanceTime: formatTime,
      });
    }
  };

  useEffect(() => {
    // 출석시간 변경시 hopeAttendanceTimes 설정
    setHopeAttendanceTimes({
      ...hopeAttendanceTimes,
      newMondayAttendanceTime: attendanceTimeData.mondayAttendanceTime,
      newTuesdayAttendanceTime: attendanceTimeData.tuesdayAttendanceTime,
      newWednesdayAttendanceTime: attendanceTimeData.wednesdayAttendanceTime,
      newThursdayAttendanceTime: attendanceTimeData.thursdayAttendanceTime,
      newFridayAttendanceTime: attendanceTimeData.fridayAttendanceTime,
    });
  }, [attendanceTimeData]);

  useEffect(() => {
    if (!userToken) {
      router.replace('/login');
    }
    if (!isKing) {
      router.replace('/main');
    }
    if (type === 0) {
      axAuth(token)({
        method: 'get',
        url: `/main/detail/${userId}/control`,
      })
        .then(res => {
          const data = res.data;

          setAttendanceTimeData(data);
        })
        .catch(err => {
          console.log(err);
        });

      // 장기 휴가신청의 여부
      axAuth(token)({
        method: 'get',
        url: `/main/detail/${userId}/control/exception/attendance`,
      })
        .then(res => {
          const data = res.data;
          setIsExceptionAttendance(data.exceptionAttendance);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }, []);

  const submitUserData = () => {
    // 출석시간 변경 버튼
    setAttendanceTimeData(prevData => ({
      ...prevData, // 기존 데이터를 복사
      mondayAttendanceTime: hopeAttendanceTimes.newMondayAttendanceTime, // 새로운 값을 할당
      tuesdayAttendanceTime: hopeAttendanceTimes.newTuesdayAttendanceTime, // 새로운 값을 할당
      wednesdayAttendanceTime: hopeAttendanceTimes.newWednesdayAttendanceTime, // 새로운 값을 할당
      thursdayAttendanceTime: hopeAttendanceTimes.newThursdayAttendanceTime, // 새로운 값을 할당
      fridayAttendanceTime: hopeAttendanceTimes.newFridayAttendanceTime, // 새로운 값을 할당
    }));

    if (!isKing) {
      router.replace('/main');
    }
    if (!userToken) {
      router.replace('/login');
    } else {
      axAuth(token)({
        method: 'post',
        url: `/main/detail/${userId}/control`,
        data: {
          mondayAttendanceTime: hopeAttendanceTimes.newMondayAttendanceTime,
          tuesdayAttendanceTime: hopeAttendanceTimes.newTuesdayAttendanceTime,
          wednesdayAttendanceTime: hopeAttendanceTimes.newWednesdayAttendanceTime,
          thursdayAttendanceTime: hopeAttendanceTimes.newThursdayAttendanceTime,
          fridayAttendanceTime: hopeAttendanceTimes.newFridayAttendanceTime,
        },
      })
        .then(res => {})
        .catch(err => {
          console.log(err);
        });
    }
  };

  // 장기휴가 신청 버튼 POST 버튼
  const submitExceptionAttendance = () => {
    if (isExceptionAttendance) {
      setIsExceptionAttendance(false);
    } else {
      setIsExceptionAttendance(true);
    }

    if (!isKing) {
      router.replace('/main');
    }
    if (!userToken) {
      router.replace('/login');
    } else {
      axAuth(token)({
        method: 'post',
        url: `/main/detail/${userId}/control/exception/attendance`,
        data: {
          exceptionAttendance: isExceptionAttendance,
        },
      })
        .then(res => {
          // console.log(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    }
  };

  return (
    <>
      <div>
        <div className="font-semibold">{name}</div>
        <div className="font-semibold">현제출석 시간</div>
        <div className="flex justify-around ">
          <div className={attendanceTimeCss}>월요일: {attendanceTimeData.mondayAttendanceTime}시</div>
          <div className={attendanceTimeCss}>화요일: {attendanceTimeData.tuesdayAttendanceTime}시</div>
          <div className={attendanceTimeCss}>수요일: {attendanceTimeData.wednesdayAttendanceTime}시</div>
          <div className={attendanceTimeCss}>목요일: {attendanceTimeData.thursdayAttendanceTime}시</div>
          <div className={attendanceTimeCss}>금요일: {attendanceTimeData.fridayAttendanceTime}시</div>
        </div>
        {isExceptionAttendance === true ? <div className="font-semibold">장기휴가 신청 : 신청 완료</div> : <div className="font-semibold">장기휴가 신청 : 신청 안함</div>}

        <div className="mr-[0.5rem] font-semibold">원하는 출석 시간 [숫자만 입력 (1~24 까지), 글자 반영안됩니다.]:</div>
        <div className="flex justify-around">
          <div className={attendanceTimeCss}>
            월요일:
            <InputControlTimeFrom time={attendanceTimeData.mondayAttendanceTime} onChange={handleMondayAttendanceTimeChange} />
          </div>
          <div className={attendanceTimeCss}>
            화요일:
            <InputControlTimeFrom time={attendanceTimeData.tuesdayAttendanceTime} onChange={handleTuesdayAttendanceTimeChange} />
          </div>
          <div className={attendanceTimeCss}>
            수요일:
            <InputControlTimeFrom time={attendanceTimeData.wednesdayAttendanceTime} onChange={handleWednesdayAttendanceTimeChange} />
          </div>
          <div className={attendanceTimeCss}>
            목요일:
            <InputControlTimeFrom time={attendanceTimeData.thursdayAttendanceTime} onChange={handleThursdayAttendanceTimeChange} />
          </div>
          <div className={attendanceTimeCss}>
            금요일:
            <InputControlTimeFrom time={attendanceTimeData.fridayAttendanceTime} onChange={handleFridayAttendanceTimeChange} />
          </div>
        </div>
      </div>
      <button
        type="button"
        className="mr-[0.5rem] px-2 py-1 text-xs font-medium text-center text-white bg-blue rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        onClick={submitUserData}
      >
        출석시간 수정하기
      </button>

      <button
        type="button"
        className="px-2 py-1 text-xs font-medium text-center text-white bg-blue rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        onClick={submitExceptionAttendance}
      >
        장기휴가
      </button>

      <div className="border border-light-grey my-[0.5rem]"></div>
    </>
  );
}
