import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { axAuth } from '@/apis/axiosinstance';
import { useEffect, useState } from 'react';

type Data = {
  id: number;
  classification: string;
  title: string;
  check: boolean;
};

export default function SuggestionArticle(data: Data) {
  const [token, setToken] = useRecoilState(userToken);

  const font = `text-white font-bold`;
  const justify = `flex justify-center`;
  const numberCss = `w-[20%] flex ${justify} `;
  const classificationCss = `w-[18%] ${justify} `;
  const titleCss = `w-[72%] ${justify} `;

  const pushCheck = (id: number) => {
    axAuth(token)({
      method: 'post',
      url: '/suggestion/check',
      data: {
        suggestionId: id,
      },
    })
      .then(res => {
        res.data;
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <>
      <div className={`${numberCss}`}>{data.id}</div>
      <div className={`${classificationCss}`}>{data.classification}</div>
      <div className={`${titleCss} flex justify-between ml-[0.2rem]`}>
        {data.title.length <= 15 ? data.title : data.title.substring(0, 15) + '...'}
        <input className="w-[1.1rem] mr-[0.2rem]" type="checkbox" checked={data.check} onChange={() => pushCheck(data.id)} id="myCheckbox" />
      </div>
    </>
  );
}
