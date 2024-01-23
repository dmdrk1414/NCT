import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { axAuth } from '@/apis/axiosinstance';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { replaceRouterEachSuggestion } from '@/utils/RouteHandling';

type Data = {
  name: string;
  comment: string;
};

export default function SuggestionComment(data: Data) {
  const [token, setToken] = useRecoilState(userToken);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const router = useRouter();

  const justify = `flex justify-center`;
  const nameCss = `w-[18%] flex ${justify} `;
  const commentCss = `w-[82%] ${justify} `;

  const deleteComment = (id: number) => {
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
      <div className={`${nameCss}`}>{data.name}</div>
      <div className={`${commentCss} flex justify-between ml-[0.2rem]`}>
        <span>{data.comment}</span>
      </div>
    </>
  );
}
