'use client';
import React, { useState, useEffect } from 'react';
import Header from '../../atoms/molecule/header';
import SubmitButton from '../../atoms/atom/large-button';
import MyPageButton from '@/atoms/atom/mypage_list';

// 박승찬 추가
import { axAuth } from '@/apis/axiosinstance';
import { userToken, isNuriKing } from '../../states/index';
import { useRouter } from 'next/navigation';
import { useRecoilState } from 'recoil';
import Navigation from '../../atoms/template/navigation';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize, replaceRouterPassword } from '@/utils/RouteHandling';
import NavigationFooter from '@/atoms/molecule/navigation-footer';

export default function SignUp() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [isKing, setISKing] = useRecoilState(isNuriKing);
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
    // 토큰이 없을시 초기화면으로 이동
    /*if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }*/

    // 해당 유저가 아니면 페이지 접근 불가능
  }, []);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/mypage',
    }).then(res => {
      const data = res.data;
      setUserData(data);
    });
  }, []);

  const isLoginAndSignupButton = false;
  return (
    <main>
      <div className="mb-10">
        <header>
          <Header isVisible={isLoginAndSignupButton} />
        </header>
        <article className="px-[7.5%]">
          <div className="mypage-content-one">
            <div className="text-3xl font-bold">마이페이지</div>
            <div className="flex items-center">
              <div className="mr-4 bg-white w-20 h-20 border-2 rounded-xl"></div>
              <div className="font-bold">
                <div>
                  소속 동아리: {userData.name} ({userData.studentId})
                </div>
                <div>
                  학과(학점): {userData.major} ({userData.gpa})
                </div>
                <div>소속 동아리: {userData.phoneNum}</div>
                <div>상태메세지: {userData.address}</div>
              </div>
            </div>
          </div>
          <div>
            
              <div className="grid-cols-1 devide-y mt-5 bg-black border-grey text-xl font-regular rounded-md">
                <a onClick={() => router.push('/main')}><MyPageButton text={'내가 쓴 글 보기'} addClass="text-xl mt-5"/></a>
                <a onClick={() => router.push('/main')}><MyPageButton text={'내가 쓴 댓글 보기'} addClass="text-xl mt-5"/></a>
                <a onClick={() => router.push('/main')}><MyPageButton text={'나의 출석 현황'} addClass="text-xl mt-5"/></a>
                <a onClick={() => router.push('/main')}><MyPageButton text={'휴면 전환하기'} addClass="text-xl mt-5"/></a>
                <a onClick={() => router.push('/main')}><MyPageButton text={'동아리 탈퇴하기'} addClass="text-xl mt-5"/></a>
                {/* <div className="border-t-8 border-lightgrey bg-lightgrey">내가 쓴 글 보기 {userData.mbti}</div>
                <div className="mt-0.5 border-lightgrey bg-lightgrey">내가 쓴 댓글 보기 {userData.specialtySkill}</div>
                <div className="mt-0.5 border-lightgrey bg-lightgrey">나의 출석 현황 {userData.birthDate}</div>
                <div className="mt-0.5 border-lightgrey bg-lightgrey">휴면 전환하기 {userData.hobby}</div>
                <div className="mt-0.5 border-lightgrey bg-lightgrey">동아리 탈퇴하기{userData.advantages}</div>
             {/* /*<div className="mt-2">단점: {userData.disadvantage}</div> 
             <div className="mt-2">자기소개: {userData.selfIntroduction}</div> */}
              </div>
          </div>
        </article>
      </div>

      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
