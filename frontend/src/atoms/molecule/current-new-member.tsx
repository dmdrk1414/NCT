type data = {
  name: string;
  applicationDate: string;
  userId: number;
  setIsNewMemberInfoOpen: (state: number) => void;
};

export default function CurrentNewMember({ userId, applicationDate, name, setIsNewMemberInfoOpen }: data) {
  const openNewMemberInfo = () => {
    setIsNewMemberInfoOpen(userId);
  };

  return (
    <div className="border-b  mb-[1rem]">
      <div className="mb-[0.3rem]">
        <div className="flex justify-around ">
          <span>{userId} : </span>
          <div>
            <div>{applicationDate}</div>
            <div>{name}</div>
          </div>
          <button className="border bg-[#aacbf4] p-[.3rem] mx-3 rounded-md" onClick={openNewMemberInfo}>
            정보 확인
          </button>
        </div>
      </div>
    </div>
  );
}
