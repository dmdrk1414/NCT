import MiddleButton from '../atom/middle-button';
import Link from 'next/link';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { axAuth } from '@/apis/axiosinstance';
type Data = {
  isVisible: boolean;
};

export default function Header(Data: Data) {
  const [token, setToken] = useRecoilState(userToken);

  const useVacation = () => {
    axAuth(token)({
      method: 'post',
      url: '/vacations/request/each',
    })
      .then(res => {
        console.log('휴가 사용 완료');
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <div className="flex items-center py-[1.3rem] relative">
      <div className="w-[8rem] h-[3rem] ms-[0.5rem]">
        <Link href={'/'}>
          <img className="w-[100%] h-[100%]" src="/Images/NCT_Logo.png" alt="로고 이미지" />
        </Link>
      </div>
      {Data.isVisible === true ? (
        <Link href="/login" className="absolute right-[0.5rem]">
          <MiddleButton addClass="text-xl" text="로그인/지원서 작성" />
        </Link>
      ) : null}
      {token ? (
        <div className="absolute right-[0.5rem]">
          <div onClick={useVacation}>
            <MiddleButton addClass="text-xl" text="휴가 사용하기" />
          </div>
        </div>
      ) : null}
    </div>
  );
}
