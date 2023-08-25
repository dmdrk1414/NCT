'use client';
import { axAuth } from '@/apis/axiosinstance';
import Header from '../../atoms/molecule/header';
import { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useRouter } from 'next/navigation';

export default function Main() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  useEffect(() => {
    if (!userToken) {
      router.replace('/login');
    }
    axAuth(token)({
      method: 'get',
      url: '/main/ybs',
    })
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  });
  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <article></article>
    </main>
  );
}
