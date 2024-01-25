'use client';
import Link from 'next/link';
import NavigationFooter from '@/atoms/molecule/navigation-footer';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../../states/index';
import React, { useState, useEffect } from 'react';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize } from '@/utils/RouteHandling';
import { useRouter } from 'next/navigation';
import { axAuth } from '@/apis/axiosinstance';
import { usePathname } from 'next/navigation';
import Button from '../../../atoms/atom/small-button';
import { HTTP_STATUS_OK } from '@/utils/constans/httpStatusEnum';
import SuggestionComment from '@/atoms/molecule/suggestion-comment';

interface SuggestionDataType {
  check: boolean;
  classification: string;
  id: number;
  title: string;
}

interface CommentDataType {
  commentId: number;
  name: string;
  content: string;
  date: string;
  author: boolean;
}

export default function Main() {
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();
  const [suggestionData, setSuggestionData] = useState<SuggestionDataType>();
  const [suggestionComment, setSuggestionComment] = useState('');
  const [commentLists, setCommentLists] = useState([]);
  const pathName: string = usePathname();
  let id: string | undefined;

  const font = `text-white font-bold`;
  const justify = `flex justify-center`;
  const classificationCss = `w-[18%] ${justify} `;
  const titleCss = `w-[82%] ${justify} `;
  const titleContentCss = `w-[64%] ${justify} `;

  const classificationFirstCss = `${classificationCss} ${font}`;
  const titleFirstCss = `${titleCss} ${font}`;

  if (typeof pathName === 'string') {
    id = pathName.split('/').pop();
  } else {
    // pathName이 undefined인 경우에 대한 처리
    id = undefined;
  }

  // 글 확인 체크
  const pushCheck = (id: number | undefined) => {
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

  // 댓글 쓰기
  const writeComment = () => {
    axAuth(token)({
      method: 'post',
      url: '/suggestion/' + id + '/comment', // api 제작시 url 변경 필요
      data: {
        content: suggestionComment,
      },
    })
      .then(response => {
        const httpStatus = response.data.httpStatus;
        if (httpStatus === HTTP_STATUS_OK) {
          alert(response.data.message + ' : 댓글 작성에 성공했습니다.\n' + response.data.timestamp);
        }
      })
      .catch(err => {
        console.log(err);
      });
  };

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    setSuggestionComment(value);
  };

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }

    // 해당 유저가 아니면 페이지 접근 불가능
  }, []);

  // 현재 본문 글 정보 불러오기
  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/suggestion/' + id,
    }).then(res => {
      const data = res.data;
      setSuggestionData(data);
    });
  }, [suggestionData]);

  // 현재 본문 글의 댓글 정보 불러오기
  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/suggestion/' + id + '/comment', // api 제작시 url 변경 필요
    }).then(res => {
      const data = res.data;
      setCommentLists(data.commentLists);
    });
  }, [commentLists]);

  return (
    <main>
      <header>
        <Link href={'/suggestion'}>
          <p className="font-bold text-3xl ml-[1.5rem] mt-[2rem] mb-[2rem]">{suggestionData?.id}</p>
        </Link>
      </header>
      <article className="flex flex-col items-center m-[1.5rem] ">
        <div className=" w-[95%] mb-[2rem]">
          <div className="flex justify-center">
            <div className={`${classificationFirstCss} rounded-tl-[0.63rem] bg-blue`}>분류</div>
            <div className={`${titleFirstCss} rounded-tr-[0.63rem] bg-blue`}>제목</div>
          </div>
          <div className="flex justify-center border border-blue h-[8rem] rounded-b-[0.63rem] ">
            <div className={`${classificationCss} font-bold items-center`}>{suggestionData?.classification}</div>
            <div className={`${titleContentCss} font-bold items-center`}>{suggestionData?.title}</div>
            <div className={`${classificationCss} items-center`}>
              <input className="w-[1.1rem] mr-[0.2rem]" type="checkbox" checked={suggestionData?.check} onChange={() => pushCheck(suggestionData?.id)} id="myCheckbox" />
            </div>
          </div>
        </div>
        <div className=" w-[95%] mb-[2rem]">
          <div className="flex justify-center">
            <div className={`${classificationFirstCss} rounded-tl-[0.63rem] bg-blue`}>이름</div>
            <div className={`${titleFirstCss} rounded-tr-[0.63rem] bg-blue`}>내용</div>
          </div>
          <div>
            {commentLists
              .slice()
              .reverse()
              .map((item: CommentDataType, idx) => (
                <SuggestionComment key={idx} commentId={item.commentId} name={item.name} content={item.content} date={item.date} author={item.author} articleId={suggestionData?.id} />
              ))}
          </div>
          <div className="flex justify-center border border-blue h-[1.5rem] rounded-b-[0.63rem] "></div>
        </div>
        <div className=" w-[95%] mb-[2rem] h-[5rem]">
          <textarea name="suggestion_content" onChange={handleChange} placeholder="댓글을 입력하세요." className={`border border-grey w-[100%] h-[100%]`} maxLength={100} />
        </div>
        <div className="flex mb-[2rem]" onClick={writeComment}>
          <Button text={'댓글 쓰기'} addClass="text-xl" />
        </div>
      </article>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
