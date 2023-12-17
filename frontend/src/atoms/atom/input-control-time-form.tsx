import React, { ChangeEvent } from 'react';

interface InputControlTimeFromProps {
  time: string;
  onChange: (newValue: string) => void;
}

export const InputControlTimeFrom: React.FC<InputControlTimeFromProps> = ({ time, onChange }) => {
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const newValue = e.target.value;
    onChange(newValue);
  };
  return (
    <div className="flex items-center">
      <input type="text" className="border border-gray-300 rounded-md focus:border-blue-500 w-[5vw]" defaultValue={time} onChange={handleChange} />
      <span>시</span>
    </div>
  );
};
