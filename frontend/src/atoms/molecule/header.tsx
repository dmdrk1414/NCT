import MiddleButton from '../atom/middle-button';
import Link from 'next/link';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { axAuth } from '@/apis/axiosinstance';
import AllertModal from '../../atoms/atom/allert-modal';
import { useState, useEffect } from 'react';
import { MODAL_VACATION_TITLE_SUCCESS, MODAL_VACATION_TITLE_DANGER } from '@/utils/constans/modalTitle';
import { MODAL_VACATION_CONTEXT_SUCCESS, MODAL_VACATION_CONTEXT_DANGER } from '@/utils/constans/modalContext';
import { MODAL_TYPE_SUCCESS, MODAL_TYPE_DANGER } from '@/utils/constans/modalType';

type Data = {
  isVisible: boolean;
};

export default function Header(Data: Data) {
  const [token, setToken] = useRecoilState(userToken);
  const [AllertModalstatus, setAllertModalStatus] = useState(0);
  const [isLogin, setIsLogin] = useState(false);

  useEffect(() => {
    // 휴가 신청 모달창
    if (AllertModalstatus !== 0) {
      setTimeout(() => {
        setAllertModalStatus(0); // 2초 후에 AllertModal 닫기
      }, 2000);
    }
  }, [AllertModalstatus]);

  useEffect(() => {
    // 휴가 신청 모달창
    if (token) {
      setIsLogin(true);
    }
  }, [token]);

  const textOfAllert = [
    // 휴가 신청 모달창 텍스트 입력
    { title: MODAL_VACATION_TITLE_SUCCESS, context: MODAL_VACATION_CONTEXT_SUCCESS, type: MODAL_TYPE_SUCCESS },
    { title: MODAL_VACATION_TITLE_DANGER, context: MODAL_VACATION_CONTEXT_DANGER, type: MODAL_TYPE_DANGER },
  ];

  const useVacation = () => {
    axAuth(token)({
      method: 'post',
      url: '/vacations/request/each',
    })
      .then(res => {
        const availableApplyVacation = res.data.availableApplyVacation;

        // 0이 닫기, 1이 성공, 2가 실패
        if (availableApplyVacation) {
          setAllertModalStatus(1);
          console.log('휴가 사용 완료');
        } else {
          setAllertModalStatus(2);
        }
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <>
      {AllertModalstatus !== 0 ? (
        <AllertModal title={textOfAllert[AllertModalstatus - 1].title} context={textOfAllert[AllertModalstatus - 1].context} type={textOfAllert[AllertModalstatus - 1].type} />
      ) : null}
      <div className="flex items-center py-[1.3rem]">
        <div className="w-[8rem] h-[3rem] ms-[0.5rem]">
          <Link href={'/'}>
            <img className="w-[100%] h-[100%]" src="/Images/NCT_Logo.png" alt="로고 이미지" />
          </Link>
        </div>
        {Data.isVisible === true ? (
          <Link href="/login" className="absolute right-[0.5rem]">
            <MiddleButton addClass="text-xl" text="로그인/지원서 작성" />
          </Link>
        ) : null}
        {isLogin ? (
          <div className="absolute right-[0.5rem]">
            <div onClick={useVacation}>
              <MiddleButton addClass="text-xl" text="휴가 사용하기" />
            </div>
          </div>
        ) : null}
      </div>
    </>
  );
}
