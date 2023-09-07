import CheckBox from '../atom/checkbox';

type data = {
  name: string;
  token: number;
  week: number[];
  userId: number;
  setIsMemberInfoOpen: (state: number) => void;
};

export default function CurrentMember({ name, token, week, userId, setIsMemberInfoOpen }: data) {
  const openMemberInfo = () => {
    setIsMemberInfoOpen(userId);
  };

  return (
    <>
      <div className="flex place-content-between items-center" onClick={openMemberInfo}>
        <div className="font-bold text-[1rem]">{name}</div>
        <div className="flex place-content-between w-[80%]">
          {week.map((item: number, idx) => (
            <CheckBox key={idx} type={item} />
          ))}
          <div className="font-semibold w-[3rem] text-center">{token}</div>
        </div>
      </div>
      <div className="border border-light-grey my-[0.5rem]"></div>
    </>
  );
}
