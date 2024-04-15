import { axAuth } from '@/apis/axiosinstance';
import Button from '../atom/small-button';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useState } from 'react';
import Slider from 'react-slick';

interface NoticeModalType {
  text: string;
}

export default function NoticeModal(props: NoticeModalType) {
  const notice = [
    {
      no: 1,
      title: '휴가 기능 경고창 추가.',
      content: '휴가 사용할시 경고 창 추가.',
      date: '2024-04-14',
    },
    {
      no: 2,
      title: '웹-프로그래머 모집',
      content: '웹-프로그래머 모집 Back-End, Front-End',
      date: '2024-04-14',
    },
  ];

  const settings = {
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 5000,
    pauseOnHover: true,
    vertical: true,
  };

  return (
    <ul className="notice overflow-y-auto h-[1.5rem] bg-[#4b89cc4b] font-medium mb-[0.5rem]">
      <Slider {...settings} className="h-[1.5rem]">
        {notice.map((item, idx) => (
          <li className="notice_content" key={idx}>
            <div>
              <span className="notice_header">공지 : </span>
              <span className="notice_title">{item['title']}</span>
            </div>
          </li>
        ))}
      </Slider>
    </ul>
  );
}
