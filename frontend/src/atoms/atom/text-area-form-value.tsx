import React, { useState } from 'react';

type data = {
  title: string;
  dataname: string;
  placeholder?: string;
  value?: string;
};

export default function TextAreaForm({ title, userData, setUserData, dataname, placeholder, value }: data & { userData: any; setUserData: any }) {
  const [textLength, setTextLength] = useState(0);

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setTextLength(value.length);
    setUserData((prevData: any) => ({ ...prevData, [dataname]: value }));
  };

  return (
    <div className="w-[100%] mb-[0.5rem] relative">
      <label htmlFor={dataname} className="font-bold">
        <span className="text-[#FF0A0A]">* </span>
        {title}
      </label>
      <span className="font-semibold absolute right-[1rem]">{textLength}자</span>
      <br />
      <textarea name={dataname} className={`border border-grey rounded w-[100%] h-[12rem]`} maxLength={500} onChange={handleChange} placeholder={placeholder} value={value} />
    </div>
  );
}
