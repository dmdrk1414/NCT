import { useRouter } from 'next/navigation';
type data = {
  now: number;
  isNuriKing: boolean;
};

export default function Navigation({ now, isNuriKing }: data) {
  const router = useRouter();
  return (
    <div className="w-[100vw] flex justify-around bg-[#F3F3F3] h-[4rem] absolute bottom-0 fixed">
      <img className={`icon ${now === 1 ? 'fill-[#5E5E5E]' : 'fill-[#A7A7A7]'}`} src="/Images/home.svg" alt="" onClick={() => router.push('/main')} />
      {isNuriKing ? <img className={`icon ${now === 2 ? 'fill-dark-grey' : 'fill-grey'}`} src="/Images/crown.png" alt="" onClick={() => router.push('/main')} /> : null}
      <img className={now === 3 ? 'text-dark-grey' : 'text-grey'} src="/Images/pencil.svg" alt="" onClick={() => router.push('/main')} />
      <img className={now === 4 ? 'text-dark-grey' : 'text-grey'} src="/Images/check.svg" alt="" onClick={() => router.push('/main')} />
      <img className={now === 5 ? 'text-dark-grey' : 'text-grey'} src="/Images/person.svg" alt="" onClick={() => router.push('/mypage')} />
    </div>
  );
}
