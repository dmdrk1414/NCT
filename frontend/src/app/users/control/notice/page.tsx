'use client';
import { axAuth } from '@/apis/axiosinstance';
import Header from '../../../../atoms/molecule/header';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../../../states/index';
import { useRouter } from 'next/navigation';
import Navigation from '../../../../atoms/template/navigation';
import UserListOnControl from '@/atoms/molecule/user-list-control';
import { hasNotToken, isNotNuriKing } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize, replaceRouterMain } from '@/utils/RouteHandling';
import NavigationFooter from '@/atoms/molecule/navigation-footer';
import { RouteUrl } from '@/utils/constans/routeEnum';
import KingControllerNavigationModal from '@/atoms/molecule/king-controller-navigation';
import SmallButton from '@/atoms/atom/thin-long-button';
import ThinButton from '@/atoms/atom/thin-button';
import ThinSmallButton from '@/atoms/atom/thin-small-button';
import KingControlNoticeModal from '@/atoms/molecule/king-control-notice-modal';

interface NoticeInformationType {
  noticeId: number;
  title: string;
  content: string;
  date: string;
}

export default function Main() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [userList, setUserList] = useState([]);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [noticeInformations, setNoticeInformations] = useState<NoticeInformationType[]>([]);

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
    axAuth(token)({
      method: 'get',
      url: `/main/notices`,
    })
      .then(res => {
        const data = res.data.result;
        const noticeInformations = data.noticeInformations;
        setNoticeInformations(noticeInformations);
      })
      .catch(err => {
        console.log(err);
      });
  }, [noticeInformations]);

  const addNoticeHandler = () => {
    const noticeTitle = prompt('새로운 제목을 입력하세요');
    const noticeContent = prompt('새로운 내용을 입력하세요');

    axAuth(token)({
      method: 'post',
      url: '/control/notices/write',
      data: {
        noticeTitle: noticeTitle,
        noticeContent: noticeContent,
      },
    })
      .then(res => {
        const data = res.data;
        alert(data.message);
      })
      .catch(err => {
        const message = err.response.data.message;
        alert(message);
      });
  };

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <section>
        <div>
          <h1 className="flex justify-center font-bold text-xl">공지 사항</h1>
        </div>
        <div className="flex flex-col items-center h-[27rem] overflow-y-auto">
          {noticeInformations.map((item, idx) => (
            <KingControlNoticeModal key={idx} noticeId={item.noticeId} title={item.title} content={item.content} date={item.date}></KingControlNoticeModal>
          ))}
        </div>
        <div className="flex justify-center">
          <div className=" w-[10rem]" onClick={addNoticeHandler}>
            <SmallButton text={'공지 사항 추가'} />
          </div>
        </div>
      </section>

      <KingControllerNavigationModal></KingControllerNavigationModal>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
