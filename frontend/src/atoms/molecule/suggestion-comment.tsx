import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { axAuth } from '@/apis/axiosinstance';
import { useEffect, useState } from 'react';
import { replaceRouterEachSuggestion } from '@/utils/RouteHandling';
import { HTTP_STATUS_OK } from '@/utils/constans/httpStatusEnum';

type Data = {
  commentId: number;
  name: string;
  content: string;
  date: string;
  author: boolean;
  articleId: number | undefined;
};

export default function SuggestionComment(data: Data) {
  const [token, setToken] = useRecoilState(userToken);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const justify = `flex justify-center`;
  const nameCss = `w-[18%] flex  mt-[0.2rem] ${justify} `;
  const commentCss = `w-[64%]  mt-[0.2rem] ${justify} `;
  const deleteCss = `w-[18%] mt-[0.2rem] ${justify} `;
  const titleLen = 20;

  // 댓글 삭제
  const deleteComment = (id: number | undefined) => {
    axAuth(token)({
      method: 'post',
      url: '/suggestion/' + id + '/comment',
      data: {
        id: data.articleId,
        commentId: data.commentId,
      },
    })
      .then(response => {
        const httpStatus = response.data.httpStatus;
        if (httpStatus === HTTP_STATUS_OK) {
          alert('댓글이 삭제되었습니다.');
        }
      })
      .catch(err => {
        console.log(err);
        alert('댓글 삭제에 실패하였습니다.');
      });
  };

  return (
    <>
      <div className={`${nameCss}`}>{data.name}</div>
      <div className={`${commentCss} flex justify-between ml-[0.2rem]`}>
        <span>{data.content.length <= titleLen ? data.content : data.content.substring(0, titleLen) + '...'}</span>
      </div>
      <div className={`${deleteCss}`}>
        {data.author ? (
          <div>
            <button type="button" className="mt-[0.2rem] mb-[0.6rem]" onClick={() => deleteComment(data.articleId)}>
              삭제
            </button>
            <div className="text-xs text-gray">{data.date}</div>
          </div>
        ) : (
          <div>
            <div className="mt-[3em] text-xs text-gray">{data.date}</div>
          </div>
        )}
      </div>
    </>
  );
}
