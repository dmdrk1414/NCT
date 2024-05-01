import { axAuth } from '@/apis/axiosinstance';
import Button from '../atom/small-button';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useEffect, useState } from 'react';
import Slider from 'react-slick';
import ThinSmallButton from '../atom/thin-small-button';

interface NoticeInformationType {
  noticeId: number;
  title: string;
  content: string;
  date: string;
}

export default function KingControlNoticeModal(data: NoticeInformationType) {
  const [token, setToken] = useRecoilState(userToken);

  const noticePutHandler = () => {
    const noticeTitle = prompt('새로운 제목을 입력하세요', data.title);
    const noticeContent = prompt('새로운 내용을 입력하세요', data.content);

    axAuth(token)({
      method: 'put',
      url: '/control/notices/' + data.noticeId,
      data: {
        noticeTitle: noticeTitle,
        noticeContent: noticeContent,
      },
    })
      .then(res => {
        const data = res.data;
        alert(data.message);
      })
      .catch(err => {
        const message = err.response.data.message;
        alert(message);
      });
  };

  const noticeDeleteHandler = () => {
    const result = confirm(`"${data.title}"을 삭제 하겠습니까?`);
    if (result) {
      axAuth(token)({
        method: 'delete',
        url: '/control/notices/' + data.noticeId,
      })
        .then(res => {
          const data = res.data;
          alert(data.message);
        })
        .catch(err => {
          alert(err.message);
        });
    }
  };

  return (
    <div className="notice_content w-[90%] border rounded-md my-[0.5rem] p-[0.3rem]">
      <div className="truncate">
        <span className="notice_date whitespace-normal">{data.date}</span>
        <div className="notice_title whitespace-normal">
          {data.noticeId} : {data.title}
        </div>
        <div className="notice_content whitespace-normal">{data.content}</div>
      </div>
      <div className="flex mt-[1rem]">
        <div onClick={noticePutHandler}>
          <ThinSmallButton text={'수정'} />
        </div>
        <div onClick={noticeDeleteHandler}>
          <ThinSmallButton text={'삭제'} />
        </div>
      </div>
    </div>
  );
}
