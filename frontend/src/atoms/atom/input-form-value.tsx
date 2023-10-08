type data = {
  title: string;
  size?: string;
  type?: string;
  dataname: string;
  placeholder?: string;
  value?: string;
};

export default function InputForm({ title, size, type, userData, setUserData, dataname, placeholder, value }: data & { userData: any; setUserData: any }) {
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUserData((prevData: any) => ({ ...prevData, [dataname]: value }));
  };

  return (
    <div className="w-[100%] mb-[0.5rem]">
      <label htmlFor={dataname} className="font-bold">
        <span className="text-[#FF0A0A]">* </span>
        {title}
      </label>
      <br />
      <input name={dataname} className={`border border-grey rounded w-[100%] ${size}`} type={type} onChange={handleChange} placeholder={placeholder} value={value} />
    </div>
  );
}
