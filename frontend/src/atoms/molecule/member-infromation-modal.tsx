import { axAuth } from '@/apis/axiosinstance';
import Button from '../atom/thin-long-button';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useEffect, useState } from 'react';
import { data } from 'autoprefixer';

type props = {
  userId: number;
  setIsMemberInfoOpen: (isOpen: number) => void;
};

type data = {
  name: string;
  major: string;
  studentId: string;
  phoneNum: string;
  hobby: string;
  specialtySkill: string;
  mbti: string;
  nuriKing: boolean;
};

export default function MemberInformationModal({ userId, setIsMemberInfoOpen }: props) {
  const [token, setToken] = useRecoilState(userToken);
  const [userInfo, setUserInfo] = useState<data>();

  const handleModal = () => {
    setIsMemberInfoOpen(0);
  };

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: `/main/detail/${userId}`,
    })
      .then(res => {
        setUserInfo(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  });
  return (
    <div className="absolute z-10 w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center" onClick={handleModal}>
      <div className="bg-white w-[85%] h-[30%] rounded-[4%] flex flex-col p-[1rem]" onClick={e => e.stopPropagation()}>
        {userInfo ? (
          <>
            <div className="font-bold text-xl text-left mb-[1rem]">{userInfo.name}</div>
            <div className="flex place-content-between">
              <span className="font-semibold text-base">전공: {userInfo.major}</span>
              <span className="font-semibold text-base">학번: {userInfo.studentId}</span>
            </div>
            <div className="border border-light-grey mt-[0.3rem]"></div>
            <span className="font-semibold text-base">핸드폰 번호: {userInfo.phoneNum}</span>
            <div className="border border-light-grey mt-[0.3rem]"></div>
            <div className="flex place-content-between">
              <span className="font-semibold text-base">취미: {userInfo.hobby}</span>
              <span className="font-semibold text-base mr-5">특기: {userInfo.specialtySkill}</span>
            </div>
            <div className="border border-light-grey mt-[0.3rem]"></div>
            <span className="font-semibold text-base mb-auto">MBTI: {userInfo.mbti}</span>
          </>
        ) : null}
        <div className="w-[100%] flex justify-center" onClick={handleModal}>
          <Button text="확인" addClass="text-sm" />
        </div>
      </div>
    </div>
  );
}
