'use client';
import { axAuth } from '@/apis/axiosinstance';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken } from '../../../states/index';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize } from '@/utils/RouteHandling';
import { useRouter } from 'next/navigation';

export default function AttendanceNumber() {
  const [number, setNumber] = useState();
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();

  useEffect(() => {
    // if (hasNotToken(token)) {
    //   replaceRouterInitialize(router);
    // }

    axAuth(token)({
      method: 'get',
      url: `attendance/find/number`,
    })
      .then(res => {
        setNumber(res.data.attendanceNum);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  return (
    <>
      <div className="flex flex-col items-center h-[100vh] bg-[#F3F7FF]">
        <div className="bg-[url('/celebration-2153_512.gif')] bg-cover w-[100%] aspect-[3/4]"></div>
        <div className="font-bold text-5xl mb-[3rem]">{number}</div>
      </div>
    </>
  );
}
