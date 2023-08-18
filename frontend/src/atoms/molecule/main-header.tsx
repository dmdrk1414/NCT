import MiddleButton from '../atom/middle-button';

export default function MainHeader() {
  return (
    <div className="flex items-center pt-[2rem] pb-[2rem] relative">
      <div className="w-[8rem] h-[3rem] ms-[0.5rem]">
        <img className="w-[100%] h-[100%]" src="/Images/NCT_Logo.png" alt="로고 이미지" />
      </div>
      <MiddleButton addClass="absolute right-[0.5rem]" text="로그인/지원서 작성" />
    </div>
  );
}
