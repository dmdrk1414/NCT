'use client';
import { axAuth } from '@/apis/axiosinstance';
import Header from '../../atoms/molecule/header';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useRouter } from 'next/navigation';
import Navigation from '../../atoms/template/navigation';

interface userDataPropsTypeZero {
  cntVacation: number;
  name: string;
  userId: number;
  weeklyData: number[];
}

interface userDataPropsTypeOne {
  phoneNum: string;
  name: string;
  userId: number;
  yearOfRegistration: string;
}

export default function Main() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [userList, setUserList] = useState([]);
  const [isKing, setIsKing] = useState(false);
  useEffect(() => {
    if (!userToken) {
      router.replace('/login');
    }
    if (type === 0) {
      axAuth(token)({
        method: 'get',
        url: '/main/ybs',
      })
        .then(res => {
          console.log(res.data);
          setUserList(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    } else {
      axAuth(token)({
        method: 'get',
        url: '/main/obs',
      })
        .then(res => {
          setUserList(res.data[0].obUserList);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }, [type]);

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <section>
        <div className="mx-[7.5%] grid grid-cols-2">
          <div className={`text-center font-bold text-xl' ${type === 0 ? 'text-black' : 'text-grey'}`} onClick={() => setType(0)}>
            현재 인원
          </div>
          <div className={`text-center font-bold text-xl' ${type === 1 ? 'text-black' : 'text-grey'}`} onClick={() => setType(1)}>
            누리 졸업자
          </div>
          <div className={`border-t-2 mt-[1rem] ${type === 0 ? 'border-black' : 'border-grey'}`}></div>
          <div className={`border-t-2 mt-[1rem] ${type === 1 ? 'border-black' : 'border-light-grey'}`}></div>
        </div>
        {type === 0 ? (
          <article>{userList && userList.map((item: userDataPropsTypeZero, idx) => <div key={idx}>{item.name}</div>)}</article>
        ) : (
          <article>{userList && userList.map((item: userDataPropsTypeOne, idx) => <div key={idx}>{item.name}</div>)}</article>
        )}
      </section>
      <footer>
        <Navigation now={1} />
      </footer>
    </main>
  );
}
