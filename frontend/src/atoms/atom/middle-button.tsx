type data = {
  text: string;
};

export default function MiddleButton({ text }: data) {
  return <button className="text-3xl">{text}</button>;
}
