import { axAuth } from '@/apis/axiosinstance';
import Button from '../atom/small-button';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useEffect, useState } from 'react';
import Slider from 'react-slick';

interface NoticeInformationType {
  noticeId: number;
  title: string;
  content: string;
  date: string;
}

export default function NoticeModal() {
  const [token, setToken] = useRecoilState(userToken);
  const [noticeInformations, setNoticeInformations] = useState<NoticeInformationType[]>([]);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: `/main/notices`,
    })
      .then(res => {
        const data = res.data.result;
        const noticeInformations = data.noticeInformations;
        setNoticeInformations(noticeInformations);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

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

  const handleItemClick = () => {
    let noticeInformationString = '';
    for (let i = 0; i < noticeInformations.length; i++) {
      const notice = noticeInformations[i];
      noticeInformationString += `날짜: ${notice.date}\n제목: ${notice.title}\n내용: ${notice.content} \n\n`;
    }
    alert(noticeInformationString);
  };

  return (
    <ul className="notice overflow-y-auto h-[1.5rem]  bg-[#4b89cc4b] font-medium mb-[0.5rem]" onClick={() => handleItemClick()}>
      <Slider {...settings} className="h-[1.5rem]">
        {noticeInformations.map((item, idx) => (
          <li className="notice_content" key={idx}>
            <div>
              <span className="notice_header">공지 : </span>
              <span className="notice_title">{item.title}</span>
            </div>
          </li>
        ))}
      </Slider>
    </ul>
  );
}
