import { axAuth } from '@/apis/axiosinstance';
import { userToken, isNuriKing } from '../../states/index';
import { useRecoilState } from 'recoil';
import { useEffect, useState } from 'react';
import ToggleGradeModal from '../molecule/toggle-grade-modal';

type data = {
  name: string;
  year: string;
  phoneNum?: string;
  isKing: boolean;
  userId: number;
  setIsMemberInfoOpen: (state: number) => void;
};

export default function Graduater({ name, year, phoneNum, isKing, userId, setIsMemberInfoOpen }: data) {
  const [token, setToken] = useRecoilState(userToken);
  const [isTogelGradeModalOpen, setIsTogelGradeModalOpen] = useState(false);
  const [isOkTogelGradeModalOpen, setOkTogelGradeModalOpen] = useState(false);

  // 회원 삭제 모달 감지
  useEffect(() => {
    setIsTogelGradeModalOpen(false);
  }, [isOkTogelGradeModalOpen]);

  // 졸업 회원 토글 기능 주기 버튼 핸들러
  const handlerTogleNomalButton = () => {
    setIsTogelGradeModalOpen(true);
  };

  const handleModal = () => {
    if (isKing) {
      setIsMemberInfoOpen(userId);
    } else {
      return;
    }
  };
  return (
    <>
      <div className={`${isKing ? 'flex place-content-between' : 'text-center'}`} onClick={handleModal}>
        {isTogelGradeModalOpen ? <ToggleGradeModal userName={name} userId={userId} setIsTogelGradeModalOpen={setIsTogelGradeModalOpen} /> : null}
        <div className="font-semibold">
          {name}
          <span>({year ? year.substring(2) : null}년)</span>
        </div>
        {isKing ? <div>{phoneNum}</div> : null}
      </div>
      {isKing ? (
        <div>
          <button
            type="button"
            className="mt-[0.5rem] px-2 py-1 text-xs font-medium text-center text-white bg-blue rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            onClick={handlerTogleNomalButton}
          >
            일반 유저 회원 전환
          </button>
        </div>
      ) : null}

      <div className="border border-light-grey my-[0.5rem]"></div>
    </>
  );
}
