'use client';
import React, { useState, useEffect } from 'react';
import Header from '../../atoms/molecule/header';
import Input from '../../atoms/atom/input-form-value';
import Textarea from '../../atoms/atom/text-area-form-value';
import SubmitButton from '../../atoms/atom/large-button';

// 박승찬 추가
import { axAuth } from '@/apis/axiosinstance';
import { userToken } from '../../states/index';
import { useRouter } from 'next/navigation';
import { useRecoilState } from 'recoil';
import Navigation from '../../atoms/template/navigation';

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
        url: '/mypage',
      }).then(res => {
        const data = res.data;
        setUserData(data);
      });
    }
  }, []);

  const isLoginAndSignupButton = false;

  return (
    <main>
      <header>
        <Header isVisible={isLoginAndSignupButton} />
      </header>
      <article className="px-[7.5%]">
        <div className="mypage-content-one">
          <div className="text-3xl font-extrabold">내 정보</div>
          <div className="flex items-center">
            <div className="mr-3 bg-black w-20 h-20 rounded-full"></div>
            <div className="font-bold">
              <div>
                이름(학번): {userData.name} ({userData.studentId})
              </div>
              <div>
                학과(학점): {userData.major} ({userData.gpa})
              </div>
              <div>연락처: {userData.phoneNum}</div>
              <div>주소: {userData.address}</div>
            </div>
          </div>
        </div>
        <div>
          <div className="mt-2">MBTI: {userData.mbti}</div>
          <div className="mt-2">특기: {userData.specialtySkill}</div>
          <div className="mt-2">생년월일: {userData.birthDate}</div>
          <div className="mt-2">취미: {userData.hobby}</div>
          <div className="mt-2">장점: {userData.advantages}</div>
          <div className="mt-2">단점: {userData.disadvantage}</div>
          <div className="mt-2">자기소개: {userData.selfIntroduction}</div>

          <a href="http://203.237.107.65:80/mypage/update">
            <div className="mt-2 font-bold">나의 페이지 수정하기 이동</div>
          </a>
        </div>
      </article>
      <footer>
        <Navigation now={1} />
      </footer>
    </main>
  );
}
