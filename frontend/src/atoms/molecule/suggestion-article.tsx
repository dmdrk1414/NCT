import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { axAuth } from '@/apis/axiosinstance';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { replaceRouterEachSuggestion } from '@/utils/RouteHandling';
import Link from 'next/link';

type Data = {
  id: number;
  classification: string;
  title: string;
  check: boolean;
};

export default function SuggestionArticle(data: Data) {
  const [token, setToken] = useRecoilState(userToken);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const router = useRouter();

  const font = `text-white font-bold`;
  const justify = `flex justify-center`;
  const numberCss = `w-[20%] flex ${justify} `;
  const classificationCss = `w-[18%] ${justify} `;
  const titleCss = `w-[72%] ${justify} `;
  const titleLen = 10;

  const pushCheck = (id: number) => {
    if (isKing) {
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
    }
  };

  return (
    <>
      <div className={`${numberCss}`}>{data.id}</div>
      <div className={`${classificationCss}`}>{data.classification}</div>
      <div className={`${titleCss} flex justify-between ml-[0.2rem]`}>
        <span
          onClick={() => replaceRouterEachSuggestion(router, data.id)}
          style={{
            cursor: 'pointer', // 포인터 모양 커서를 추가하여 클릭 가능성을 나타냅니다.
            transition: 'background-color 0.3s', // 배경색 변경에 대한 부드러운 전환을 설정합니다.
          }}
        >
          {data.title.length <= titleLen ? data.title : data.title.substring(0, titleLen) + '...'}
        </span>
        <input className="w-[1.1rem] mr-[0.2rem]" type="checkbox" checked={data.check} onChange={() => pushCheck(data.id)} id="myCheckbox" />
      </div>
    </>
  );
}
