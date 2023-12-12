import { data } from 'autoprefixer';
import { imageConfigDefault } from 'next/dist/shared/lib/image-config';

type data = {
  type: number;
};

export default function CheckBox({ type }: data) {
  console.log('Data:', { type }); // Log the 'data' object

  return (
    <>
      {type === 1 ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_check.png" alt="checkbox" />
      ) : type === 0 ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_default.png" alt="checkbox" />
      ) : type === 2 ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_vacation.png" alt="checkbox" />
      ) : (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_red.png" alt="checkbox" />
      )}
    </>
  );
}
