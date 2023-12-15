import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
type data = {
  now: number;
  isNuriKing: boolean;
};

export default function Navigation({ now, isNuriKing }: data) {
  const router = useRouter();
  const [page, setPage] = useState(1);
  const [king, setKing] = useState(false);

  useEffect(() => {
    if (isNuriKing) {
      setKing(true);
    } else {
      setKing(false);
    }
  }, [isNuriKing]);

  useEffect(() => {
    setPage(now);
  }, [now]);

  return (
    <div className="w-[100vw] flex justify-around bg-[#F3F3F3] h-[3rem]  bottom-0 fixed">
      <img className={`icon ${page === 1 ? 'fill-[#5E5E5E]' : 'fill-[#A7A7A7]'}`} src="/Images/home.svg" alt="" onClick={() => router.push('/main')} />
      {king ? <img className={`icon ${page === 2 ? 'fill-dark-grey' : 'fill-grey'}`} src="/Images/crown.png" alt="" onClick={() => router.push('/users/control')} /> : null}
      <img className={page === 3 ? 'text-dark-grey' : 'text-grey'} src="/Images/pencil.svg" alt="" onClick={() => router.push('/main')} />
      <img className={page === 4 ? 'text-dark-grey' : 'text-grey'} src="/Images/check.svg" alt="" onClick={() => router.push('/main')} />
      <img className={page === 5 ? 'text-dark-grey' : 'text-grey'} src="/Images/person.svg" alt="" onClick={() => router.push('/mypage')} />
    </div>
  );
}
