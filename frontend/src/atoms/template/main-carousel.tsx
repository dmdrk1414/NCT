'use client'; // SSL이 아닌 CSL로 바꾸어 주는 명령어 => Carousel은 동적이라 SSL 작동이 불가한가 봄. 정확한 이유 모르겠음
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './carousel.css';
export default function MainCarousel() {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 5000,
    pauseOnHover: true,
    vertical: false,
  };
  return (
    <div className="overflow-x-hidden">
      <Slider {...settings} className="h-[60vh] mb-[5rem]">
        <div className="bg-[url('/Images/main_one.jpg')] bg-cover w-[100%] aspect-[3/4]"></div>
        <div className="bg-[url('/Images/main_two.jpg')] bg-cover w-[100vw] aspect-[3/4]"></div>
        <div className="bg-[url('/Images/main_three.jpg')] bg-cover w-[100vw] aspect-[3/4]"></div>
        <div className="bg-[url('/Images/main_four.jpg')] bg-cover w-[100vw] aspect-[3/4]"></div>
        <div className="bg-[url('/Images/main_five.jpg')] bg-cover w-[100vw] aspect-[3/4]"></div>
      </Slider>
    </div>
  );
}
