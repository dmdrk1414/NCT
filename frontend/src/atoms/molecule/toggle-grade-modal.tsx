import { axAuth } from '@/apis/axiosinstance';
import { useRecoilState } from 'recoil';
import { useEffect, useState } from 'react';
import Button from '../atom/small-button';
import { userToken, isNuriKing } from '../../states/index';

type props = {
  userId: number;
  userName: string;
  setIsTogelGradeModalOpen: (isOpen: false) => void;
};

export default function ToggleGradeModal(item: props) {
  const [token, setToken] = useRecoilState(userToken);
  const [message, setMessage] = useState<string>('');
  const contentCss = ' border-b mt-[0.5rem]';
  const [isOpen, setIsOpen] = useState<boolean>(true);

  const handleModal = () => {
    item.setIsTogelGradeModalOpen(false);
  };

  // 멤버 삭제 버튼 POST 버튼
  const submitToggleGradeUser = () => {
    setIsOpen(false);

    if (isNuriKing) {
      axAuth(token)({
        method: 'post',
        url: `/main/detail/${item.userId}/control/toggle/grade`,
      })
        .then(res => {
          const data = res.data;
          const message = data.message;

          setMessage(message);
        })
        .catch(err => {
          console.log(err);
        });
    }
    setTimeout(() => {
      item.setIsTogelGradeModalOpen(false);
    }, 2000);
  };

  return (
    <div className="absolute   w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center" onClick={handleModal}>
      <div className="bg-white w-[80%] h-[20%] rounded-[7%] flex flex-col justify-center items-center" onClick={e => e.stopPropagation()}>
        {isOpen ? (
          <>
            <div className="p-[0.5rem] font-bold text-xl">
              <span className="text-red">{item.userName}</span>
              <span>의 등급을 전환 하시겠습니까?</span>
            </div>
            <div onClick={submitToggleGradeUser}>
              <Button text="등급 전환" addClass="text-xl" />
            </div>
          </>
        ) : (
          <>
            <div className="text-red font-bold">{message}</div>
            <div className="text-red font-bold"> - 이창은 2초뒤에 없어 집니다.</div>
          </>
        )}
      </div>
    </div>
  );
}
