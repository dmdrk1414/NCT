type data = {
  title: string;
  context: string;
  type: string;
};

export default function AllertModal({ title, context, type }: data) {
  return (
    <div className="absolute z-10 w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center">
      {type === 'danger' ? (
        <>
          <div className="bg-[#FFC3C3] w-[80%] h-[15%] rounded-[7%] flex flex-col justify-center items-center" onClick={e => e.stopPropagation()}>
            <div className="text-center font-bold text-2xl text-red">{title}</div>
            <div className="text-center font-semibold text-lg text-dark-grey mt-[0.5rem]">{context}</div>
          </div>
        </>
      ) : type === 'success' ? (
        <>
          <div className="bg-white w-[80%] h-[15%] rounded-[7%] flex flex-col justify-center items-center" onClick={e => e.stopPropagation()}>
            <div className="text-center font-bold text-2xl">{title}</div>
            <div className="text-center font-semibold text-lg text-dark-grey mt-[0.5rem]">{context}</div>
          </div>
        </>
      ) : null}
    </div>
  );
}
