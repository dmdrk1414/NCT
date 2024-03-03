import { axAuth } from '@/apis/axiosinstance';
import { useRecoilState } from 'recoil';
import { useEffect, useState } from 'react';
import Button from '../atom/small-button';
import { userToken, isNuriKing } from '../../states/index';

type props = {
  userId: number;
  userName: string;
  setIsGiveNuriKingModalOpen: (isOpen: false) => void;
};

export default function GiveNuriKingModal(item: props) {
  const [token, setToken] = useRecoilState(userToken);
  const [message, setMessage] = useState<string>('');
  const contentCss = ' border-b mt-[0.5rem]';
  const [isOpen, setIsOpen] = useState<boolean>(true);

  const handleModal = () => {
    item.setIsGiveNuriKingModalOpen(false);
  };

  // 멤버 삭제 버튼 POST 버튼
  const submitGiveKingUser = () => {
    setIsOpen(false);

    if (isNuriKing) {
      axAuth(token)({
        method: 'post',
        url: `/main/detail/${item.userId}/control/give/king`,
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
      item.setIsGiveNuriKingModalOpen(false);
    }, 600000);
  };

  return (
    <div className="absolute   w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center" onClick={handleModal}>
      {isOpen ? (
        <div className="bg-white w-[80%] h-[20%] rounded-[7%] flex flex-col justify-center items-center" onClick={e => e.stopPropagation()}>
          <div className="p-[0.5rem] font-bold text-xl">
            <span className="text-red">{item.userName}</span>
            <span>님 에게 실장 권한을 드리겠습니까?</span>
          </div>
          <div onClick={submitGiveKingUser}>
            <Button text="권한 주기" addClass="text-xl" />
          </div>
        </div>
      ) : (
        <>
          <div className="bg-white w-[80%] h-[80%] rounded-[7%] flex flex-col justify-center items-center" onClick={e => e.stopPropagation()}>
            <div className="p-[2rem]">
              <div className="font-bold text-red">{item.userName}에게 실장 권한이 넘어 갔습니다. </div>
              <div className="font-bold">어플리 케이션을 종료하면 적용이 됩니다.</div>
              <div className="font-bold">지금까지 고생 많았습니다.</div>
              <div>앱을 만든 누리 고시원</div>
              <div className="font-bold">선배 박승찬 입니다.</div>
              <div>이 글을 보고 있다면 저에게 연락 바랍니다.</div>
              <div>그때 까지 이 웹사이트가 살아 있을지 궁금하네요...</div>
              <div>하지만 누리고시원 사람들은 모든일을 잘 할 수있을꺼라 믿고 있습니다.</div>
              <div>저도 이 고시원을 운영할 때 정말 힘들 었던 것 같이요....</div>
              <div>하지만 많은 추억이 있던 장소 입니다. 화이팅입니다!!!</div>
              <div>이글은 10분후에 끝납니다.</div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}
