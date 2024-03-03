import { axAuth } from '@/apis/axiosinstance';
import { useRecoilState } from 'recoil';
import { useEffect, useState } from 'react';
import Button from '../atom/small-button';
import { userToken, isNuriKing } from '../../states/index';

type props = {
  userId: number;
  userName: string;
  setIsDeleteModalInfoOpen: (isOpen: false) => void;
};

export default function UserDeleteModal(item: props) {
  const [token, setToken] = useRecoilState(userToken);
  const [message, setMessage] = useState<string>('');
  const contentCss = ' border-b mt-[0.5rem]';
  const [isOpen, setIsOpen] = useState<boolean>(true);
  const handleModal = () => {
    item.setIsDeleteModalInfoOpen(false);
  };

  // 멤버 삭제 버튼 POST 버튼
  const submitDeleteUser = () => {
    setIsOpen(false);

    if (isNuriKing) {
      axAuth(token)({
        method: 'post',
        url: `/main/detail/${item.userId}/control/delete`,
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
      item.setIsDeleteModalInfoOpen(false);
    }, 2000);
  };

  return (
    <div className="absolute   w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center" onClick={handleModal}>
      <div className="bg-white w-[80%] h-[20%] rounded-[7%] flex flex-col justify-center items-center" onClick={e => e.stopPropagation()}>
        {isOpen ? (
          <>
            <div className="font-bold text-xl">
              <span className="text-red">{item.userName}</span>
              <span>님을 추방 하시겠습니까?</span>
            </div>
            <div onClick={submitDeleteUser}>
              <Button text="추방" addClass="text-xl" />
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
