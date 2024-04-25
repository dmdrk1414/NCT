import { axAuth } from '@/apis/axiosinstance';
import { useRecoilState } from 'recoil';
import { useEffect, useState } from 'react';
import Button from '../atom/small-button';
import { userToken, isNuriKing } from '../../states/index';
import { useRouter } from 'next/navigation';
import { RouteUrl } from '@/utils/constans/routeEnum';

export default function KingControllerNavigationModal() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [message, setMessage] = useState<string>('');
  const contentCss = ' border-b mt-[0.5rem]';
  const [isOpen, setIsOpen] = useState<boolean>(true);

  const kingManagePageCss = 'flex justify-around items-center w-[25vw] bg-[#ffffff] rounded-lg h-[2.5rem] font-bold';
  const giveVacation2AllMember = () => {
    const vacationDays = window.prompt('모든 구성원에게 부여할 휴가 일수를 입력하세요:', '0');

    if (vacationDays !== null) {
      const parsedVacationDays = parseInt(vacationDays);
      if (!isNaN(parsedVacationDays) && parsedVacationDays > 0) {
        if (confirm(`모든 구성원에게 ${parsedVacationDays}일의 휴가를 부여하겠습니까?`)) {
          axAuth(token)({
            method: 'post',
            url: '/control/give/vacations',
            data: {
              vacationToken: parsedVacationDays,
            },
          })
            .then(res => {
              const data = res.data;
              console.log(data);
            })
            .catch(err => {
              console.log(err);
            });
        }
      } else {
        alert('올바른 휴가 일수를 입력하세요.');
      }
    }
  };

  return (
    <div>
      <div className="w-[100vw] flex justify-around items-center bg-[#F3F3F3] h-[3rem]  bottom-[3rem] fixed">
        <div className={kingManagePageCss} onClick={() => router.push(RouteUrl.ROUTE_JOIN_APPLICATION_LIST)}>
          신입 신청 확인
        </div>
        <div className={kingManagePageCss} onClick={() => router.push(RouteUrl.ROUTE_ATTENDANCE_NUMBER)}>
          출석 번호
        </div>
        <div className={kingManagePageCss} onClick={giveVacation2AllMember}>
          전체 휴가 부여
        </div>
      </div>
    </div>
  );
}
