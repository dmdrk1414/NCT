'use client';
import { axAuth } from '@/apis/axiosinstance';
import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { userToken } from '../../../../states/index';

type userData = {
  id: number;
  name: string;
};

export default function JoinApplicationForm() {
  const [token, setToken] = useRecoilState(userToken);
  const [data, setData] = useState<userData[]>();
  const [refresh, setRefresh] = useState(0);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/new-users',
    })
      .then(res => {
        console.log(res);
        setData(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  }, [refresh]);

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
    <div>
      {data?.map((item, idx) => (
        <div key={idx}>
          <span>{item.name}</span>
          <button className="border bg-blue mx-3" onClick={() => accept(item.id)}>
            수락
          </button>
          <button className="border bg-red" onClick={() => reject(item.id)}>
            거절
          </button>
        </div>
      ))}
    </div>
  );
}
