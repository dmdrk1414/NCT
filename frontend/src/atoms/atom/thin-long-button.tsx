type data = {
  text: string;
  addClass?: string;
};

export default function SmallButton({ text, addClass }: data) {
  return <button className={`w-[100%] h-[1.88rem] bg-blue rounded-[0.5rem] text-white font-bold ${addClass}`}>{text}</button>;
}
