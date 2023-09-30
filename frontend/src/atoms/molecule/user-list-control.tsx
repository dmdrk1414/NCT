import { axAuth } from '@/apis/axiosinstance';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { useRouter } from 'next/navigation';

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
    attendanceTime: '',
    name: '',
  });
  const [attendanceHopeTime, setAttendanceHopeTime] = useState<string>(''); // 초기값을 빈 문자열로 설정

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let { value } = e.target; // 입력 요소의 값을 가져옴

    if (!isNaN(Number(value)) && Number(value) <= 10) {
      value = value.padStart(2, '0');
    }

    setAttendanceHopeTime(
      value, // attendanceTime을 업데이트
    );
  };

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
          attendanceTime: attendanceHopeTime,
        },
      })
        .then(res => {
          console.log(res.data);
        })
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
          console.log(res.data);
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
        <div className="font-semibold">현제출석 시간: {attendanceTimeData.attendanceTime}시</div>
        {isExceptionAttendance === true ? <div className="font-semibold">장기휴가 신청 : 신청 완료</div> : <div className="font-semibold">장기휴가 신청 : 신청 안함</div>}

        <div className="flex">
          <div className="mr-[0.5rem] font-semibold">원하는 출석 시간: </div>
          <input className="border border-gray-300 rounded-md  focus:border-blue-500" type="text" placeholder="Enter text" onChange={handleChange} />
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
