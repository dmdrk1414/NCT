'use client';
import React, { useState, useEffect } from 'react';
import Header from '../../../atoms/molecule/header';
import Input from '../../../atoms/atom/input-form-value';
import Textarea from '../../../atoms/atom/text-area-form-value';
import SubmitButton from '../../../atoms/atom/large-button';

// 박승찬 추가
import { axAuth } from '@/apis/axiosinstance';
import { userToken } from '../../../states/index';
import { useRouter } from 'next/navigation';
import { useRecoilState } from 'recoil';

export default function SignUp() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [userData, setUserData] = useState({
    name: '',
    phoneNum: '',
    major: '',
    gpa: '',
    address: '',
    specialtySkill: '',
    hobby: '',
    mbti: '',
    studentId: '',
    birthDate: '',
    advantages: '',
    disadvantage: '',
    selfIntroduction: '',
    photo: '',
    yearOfRegistration: '',
    email: '',
  });

  useEffect(() => {
    if (!userToken) {
      router.replace('/login');
    } else {
      axAuth(token)({
        method: 'get',
        url: '/mypage/update',
      }).then(res => {
        const data = res.data;
        setUserData(data);
      });
    }
  }, []);

  const validation = () => {
    const phonenumbervalidation = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;
    if (!userData.name) {
      alert('성함을 확인해주세요.');
    } else if (!userData.phoneNum || !phonenumbervalidation.test(userData.phoneNum)) {
      alert('핸드폰 번호를 확인해주세요.');
    } else if (!userData.major) {
      alert('전공을 확인해주세요.');
    } else if (!userData.gpa) {
      alert('학점을 확인해주세요.');
    } else if (!userData.address) {
      alert('주소를 확인해주세요.');
    } else if (!userData.specialtySkill) {
      alert('특기를 확인해주세요.');
    } else if (!userData.hobby) {
      alert('취미를 확인해주세요.');
    } else if (!userData.mbti) {
      alert('mbti를 확인해주세요.');
    } else if (!userData.studentId) {
      alert('학번을 확인해주세요.');
    } else if (!userData.birthDate) {
      alert('생년월일을 확인해주세요.');
    } else if (userData.advantages.length < 100) {
      alert('장점을 200자 이상 입력해주세요.');
    } else if (userData.disadvantage.length < 100) {
      alert('단점을 200자 이상 입력해주세요.');
    } else if (userData.selfIntroduction.length < 200) {
      alert('자기소개를 300자 이상 입력해주세요.');
    } else if (!userData.email) {
      alert('이메일을 확인해주세요.');
    } else {
      submitUserData();
    }
  };

  const submitUserData = () => {
    if (!userToken) {
      router.replace('/login');
    } else {
      axAuth(token)({
        method: 'put',
        url: '/mypage/update',
        data: {
          name: userData.name,
          phoneNum: userData.phoneNum,
          major: userData.major,
          gpa: userData.gpa,
          address: userData.address,
          specialtySkill: userData.specialtySkill,
          hobby: userData.hobby,
          mbti: userData.mbti,
          studentId: userData.studentId,
          birthDate: userData.birthDate,
          advantages: userData.advantages,
          disadvantage: userData.disadvantage,
          selfIntroduction: userData.selfIntroduction,
          photo: userData.photo,
          email: userData.email,
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
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <article className="px-[7.5%]">
        <Input title={'성함'} userData={userData} setUserData={setUserData} dataname="name" value={userData.name} />
        <Input title={"핸드폰 번호('-' 포함)"} userData={userData} setUserData={setUserData} dataname="phoneNum" value={userData.phoneNum} />
        <Input title={'전공'} userData={userData} setUserData={setUserData} dataname="major" value={userData.major} />
        <Input title={'학점'} userData={userData} setUserData={setUserData} dataname="gpa" value={userData.gpa} />
        <Input title={'주소'} userData={userData} setUserData={setUserData} dataname="address" value={userData.address} />
        <Input title={'특기'} userData={userData} setUserData={setUserData} dataname="specialtySkill" value={userData.specialtySkill} />
        <Input title={'취미'} userData={userData} setUserData={setUserData} dataname="hobby" value={userData.hobby} />
        <Input title={'MBTI'} userData={userData} setUserData={setUserData} dataname="mbti" value={userData.mbti} />
        <Input title={'학번'} userData={userData} setUserData={setUserData} dataname="studentId" value={userData.studentId} />
        <Input title={'생년월일 (예 960415)'} userData={userData} setUserData={setUserData} dataname="birthDate" value={userData.birthDate} />
        <Textarea title={'장점(100자 이상)'} userData={userData} setUserData={setUserData} dataname="advantages" value={userData.advantages} />
        <Textarea title={'단점(100자 이상)'} userData={userData} setUserData={setUserData} dataname="disadvantage" value={userData.disadvantage} />
        <Textarea title={'자기소개(200자 이상)'} userData={userData} setUserData={setUserData} dataname="selfIntroduction" value={userData.selfIntroduction} />
        <Input title={'이메일'} userData={userData} setUserData={setUserData} dataname="email" value={userData.email} />
      </article>
      <footer className="flex justify-center px-[7.5%] mt-[2rem] mb-[3rem]">
        <div className="w-[100%]" onClick={validation}>
          <SubmitButton text={'수정하기'} addClass="text-2xl" />
        </div>
      </footer>
    </main>
  );
}
