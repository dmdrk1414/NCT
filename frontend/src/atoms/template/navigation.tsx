import { useRouter } from 'next/navigation';
type data = {
  now: number;
};

export default function Navigation(data: data) {
  const router = useRouter();
  return (
    <div className="w-[100vw] flex justify-around bg-[#F3F3F3] h-[4rem] absolute bottom-0 fixed">
      <img className={`icon ${data.now === 1 ? 'fill-[#5E5E5E]' : 'fill-[#A7A7A7]'}`} src="/Images/home.svg" alt="" onClick={() => router.push('/home')} />
      <img className={`icon ${data.now === 2 ? 'fill-dark-grey' : 'fill-grey'}`} src="/Images/dollar.svg" alt="" onClick={() => router.push('/home')} />
      <img className={data.now === 3 ? 'text-dark-grey' : 'text-grey'} src="/Images/pencil.svg" alt="" onClick={() => router.push('/home')} />
      <img className={data.now === 4 ? 'text-dark-grey' : 'text-grey'} src="/Images/check.svg" alt="" onClick={() => router.push('/home')} />
      <img className={data.now === 5 ? 'text-dark-grey' : 'text-grey'} src="/Images/person.svg" alt="" onClick={() => router.push('/mypage')} />
    </div>
  );
}
