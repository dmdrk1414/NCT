import { axAuth } from '@/apis/axiosinstance';
import Button from '../atom/thin-long-button';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useEffect, useState } from 'react';
import { data } from 'autoprefixer';

type props = {
  userId: number;
  setIsNewMemberInfoOpen: (isOpen: number) => void;
  isKing: boolean;
  setRefresh: (refresh: number) => void;
  refresh: number;
};

type newUserDataType = {
  address: string;
  advantages: string;
  applicationDate: string;
  birthDate: string;
  disadvantage: string;
  email: string;
  gpa: string;
  hobby: string;
  id: number;
  major: string;
  mbti: string;
  name: string;
  ob: boolean;
  password: string;
  phoneNum: string;
  photo: string;
  regularMember: boolean;
  selfIntroduction: string;
  specialtySkill: string;
  studentId: string;
  yearOfRegistration: string;
};

export default function NewMemberInformationModal({ userId, setIsNewMemberInfoOpen, isKing, setRefresh, refresh }: props) {
  const [token, setToken] = useRecoilState(userToken);
  const [newUserData, setNewUserData] = useState<newUserDataType>();
  const [modalHeight, setModalHeight] = useState<string>('80vh'); // 초기 높이 설정
  const contentCss = ' border-b mt-[0.5rem]';

  const handleModal = () => {
    setIsNewMemberInfoOpen(0);
  };

  const openMemberInfo = () => {
    setIsNewMemberInfoOpen(userId);
  };

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: `/new-users/${userId}`,
    })
      .then(res => {
        console.log(res.data.tempUser);
        setNewUserData(res.data.tempUser);

        const contentHeight = document.getElementById('modal-content')?.clientHeight;
        if (contentHeight) {
          const newHeight = contentHeight + 20; // 내용 높이 + 여백 등을 더해서 조절
          setModalHeight(`${newHeight}px`);
        }
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  function accept(id: number) {
    axAuth(token)({
      method: 'post',
      url: `/new-users/${id}/acceptance`,
    })
      .then(res => {
        console.log(res);
        setRefresh(refresh + 1);
      })
      .catch(err => {
        console.log(err);
      });
  }

  function reject(id: number) {
    axAuth(token)({
      method: 'post',
      url: `/new-users/${id}/reject`,
    })
      .then(res => {
        console.log(res);
        setRefresh(refresh + 1);
      })
      .catch(err => {
        console.log(err);
      });
  }

  return (
    <div className="absolute z-100 w-[100vw] h-[100vh]  " onClick={handleModal}>
      <div className={`w-[80vw] h-${modalHeight} bg-[#deecfc] rounded-[10px] p-[0.5rem]`} id="modal-content">
        <div className="mypage-content-one ">
          <div className="text-3xl font-extrabold">신입 정보</div>
          <div className="flex items-center">
            <div className="mr-3 bg-black w-20 h-20 rounded-full"></div>
            <div className="font-bold">
              <div>
                이름(학번): {newUserData?.name} ({newUserData?.studentId})
              </div>
              <div>
                학과(학점): {newUserData?.major} ({newUserData?.gpa})
              </div>
              <div>연락처: {newUserData?.phoneNum}</div>
              <div className="border-b">주소: {newUserData?.address}</div>
            </div>
          </div>
        </div>

        <div>
          <div className={contentCss}>MBTI: {newUserData?.mbti}</div>
          <div className={contentCss}>특기: {newUserData?.specialtySkill}</div>
          <div className={contentCss}>생년월일: {newUserData?.birthDate}</div>
          <div className={contentCss}>취미: {newUserData?.hobby}</div>
          <div className={contentCss}>
            <div>장점</div>
            <div>{newUserData?.advantages}</div>
          </div>
          <div className={contentCss}>
            <div>단점</div>
            <div>{newUserData?.disadvantage}</div>
          </div>
          <div className={contentCss}>
            <div>자기소개</div>
            <div>{newUserData?.selfIntroduction}</div>
          </div>
        </div>
        <div className="flex justify-around  mt-[2rem] mb-[5rem]">
          <button className="border w-[30vw] bg-[#aacbf4] p-[.3rem] mx-3 rounded-md" onClick={() => accept(userId)}>
            수락
          </button>
          <button className="border w-[30vw] bg-[#d92912] p-[.3rem] mx-3 rounded-md" onClick={() => reject(userId)}>
            거절
          </button>
        </div>
      </div>
    </div>
  );
}
