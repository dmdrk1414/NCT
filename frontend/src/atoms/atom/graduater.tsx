type data = {
  name: string;
  year: string;
  phoneNum?: string;
  isKing: boolean;
  userId: number;
  setIsMemberInfoOpen: (state: number) => void;
};

export default function Graduater({ name, year, phoneNum, isKing, userId, setIsMemberInfoOpen }: data) {
  const handleModal = () => {
    setIsMemberInfoOpen(userId);
  };
  return (
    <>
      <div className={`${isKing ? 'flex place-content-between' : 'text-center'}`} onClick={handleModal}>
        <div className="font-semibold">
          {name}
          <span>({year ? year.substring(2) : null}년)</span>
        </div>
        {isKing ? <div>{phoneNum}</div> : null}
      </div>
      <div className="border border-light-grey my-[0.5rem]"></div>
    </>
  );
}
