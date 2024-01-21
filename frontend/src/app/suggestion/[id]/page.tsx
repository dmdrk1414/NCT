'use client';
import Header from '@/atoms/molecule/header';
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
  name: string;
  comment: string;
}

export default function Main() {
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();
  const [suggestionData, setSuggestionData] = useState<SuggestionDataType>();
  const [userData, setUserData] = useState({ name: '' });
  const [suggestionComment, setSuggestionComment] = useState('');
  const [commentData, setCommentData] = useState<CommentDataType>();
  const [commentLists, setCommentLists] = useState([]);
  const pathName: string = usePathname();
  let id: string | undefined;

  const font = `text-white font-bold`;
  const justify = `flex justify-center`;
  const classificationCss = `w-[18%] ${justify} `;
  const titleCss = `w-[82%] ${justify} `;

  const classificationFirstCss = `${classificationCss} ${font}`;
  const titleFirstCss = `${titleCss} ${font}`;

  if (typeof pathName === 'string') {
    id = pathName.split('/').pop();
  } else {
    // pathName이 undefined인 경우에 대한 처리
    id = undefined;
  }

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

  const writeComment = () => {
    axAuth(token)({
      method: 'post',
      url: '/suggestion/Comment',
      data: {
        name: userData,
        content: suggestionComment,
        holidayPeriod: '',
      },
    })
      .then(response => {
        const httpStatus = response.data.httpStatus;
        if (httpStatus === HTTP_STATUS_OK) {
          alert('댓글이 작성되었습니다.');
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

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/mypage/' + id,
    }).then(res => {
      const data = res.data;
      setUserData(data);
    });
  }, []);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/suggestion/' + id,
    }).then(res => {
      const data = res.data;
      setSuggestionData(data);
    });
  }, [suggestionData]);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/suggestion/' + id + '/comment',
    }).then(res => {
      const data = res.data;
      setCommentData(data);
    });
  }, [commentData]);

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
          <div className="flex justify-center border border-blue h-[10rem] rounded-b-[0.63rem] ">
            <div className={`${classificationCss} items-center`}>{suggestionData?.classification}</div>
            <div className={`${titleCss} items-center`}>{suggestionData?.title}</div>
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
                <div key={idx} className="flex justify-center border border-blue h-[2rem]">
                  <SuggestionComment name={item.name} comment={item.comment} />
                </div>
              ))}
          </div>
          <div className="flex justify-center border border-blue h-[1.5rem] rounded-b-[0.63rem] "></div>
        </div>
        <div className=" w-[95%] mb-[1rem] h-[5rem]">
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
