'use client';
import { axAuth } from '@/apis/axiosinstance';
import Header from '../../../atoms/molecule/header';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../../states/index';
import { useRouter } from 'next/navigation';
import Navigation from '../../../atoms/template/navigation';
import UserListOnControl from '@/atoms/molecule/user-list-control';
import { hasNotToken, isNotNuriKing } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize, replaceRouterMain } from '@/utils/RouteHandling';

interface userDataPropsTypeOne {
  name: string;
  userId: number;
}

export default function Main() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [userList, setUserList] = useState([]);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [isAttendanceModalOpen, setIsAttendanceModalOpen] = useState(false);
  const [isTodayAttendance, setIsTodayAttendance] = useState(false);
  const [isMemberInfoOpen, setIsMemberInfoOpen] = useState(0);

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }

    // 실장이 아니면 메인페이지으로 이동
    if (isNotNuriKing(isKing)) {
      replaceRouterMain(router);
    }
  }, []);

  useEffect(() => {
    if (type === 0) {
      axAuth(token)({
        method: 'get',
        url: '/main/ybs',
      })
        .then(res => {
          setUserList(res.data.ybUserInfomationList);
          setIsTodayAttendance(res.data.passAttendanceOfSearchUse);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }, [type, isAttendanceModalOpen]);

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <section>
        <div className="mx-[7.5%] grid grid-cols-2 mb-[2rem]">
          <div className={`text-center font-bold text-xl' ${type === 0 ? 'text-black' : 'text-grey'}`} onClick={() => setType(0)}>
            현재 인원 개인별 설정
          </div>
        </div>
        <article className="mx-[7.5%]">
          {userList.map((item: userDataPropsTypeOne, index) => (
            <UserListOnControl key={index} name={item.name} isKing={isKing} userId={item.userId} setIsMemberInfoOpen={setIsMemberInfoOpen} />
          ))}
        </article>
      </section>
      <footer>
        <Navigation now={1} isNuriKing={isKing} />
      </footer>
    </main>
  );
}
