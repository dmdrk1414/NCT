type data = {
  text: string;
  addClass?: string;
};

export default function ThinButton({ text, addClass }: data) {
  return <button className={`w-[6.25rem] h-[1.1rem] bg-blue rounded-[0.5rem] text-white font-bold flex justify-center items-center text-sm ${addClass}`}>{text}</button>;
}
