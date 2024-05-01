import React, { useState } from 'react';

interface CustomAlertProps {
  message: string;
  onClose: () => void;
}

const CustomAlert: React.FC<CustomAlertProps> = ({ message, onClose }) => {
  return (
    <div className="custom-alert">
      <div className="overlay" onClick={onClose}></div>
      <div className="alert-box">
        <div className="message">{message}</div>
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default CustomAlert;
