'use client';
import React, { useState } from 'react';
import Header from '../../atoms/molecule/header';
import Button from '../../atoms/atom/middle-button';
import { axBase } from '../../apis/axiosinstance';

export default function Login() {
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');

  const inputUserName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(e.target.value);
  };

  const inputPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const login = () => {
    axBase()({
      method: 'post',
      url: '/login',
      data: {
        email: username,
        password: password,
      },
    })
      .then(response => {
        console.log(response);
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <article className="flex flex-col items-center">
        <p className="font-bold text-5xl my-[6rem]">Login</p>
        <input className="border-b-2 w-[85%] mb-[3rem]" type="text" name="username" placeholder="  E_MAIL" onChange={inputUserName} />
        <input className="border-b-2 w-[85%] mb-[5rem]" type="text" name="password" placeholder="  PASSWORD" onChange={inputPassword} />
        <div className="mb-[1rem]" onClick={login}>
          <Button text={'로그인'} addClass="text-2xl" />
        </div>
        <p></p>
        <div className="mb-[5rem]">
          <Button text={'지원서 작성'} addClass="text-2xl" />
        </div>
      </article>
      <footer className="flex justify-center">
        <a href="" onClick={() => alert('멍청한ㅅㄲ')}>
          비밀번호를 잊어버리셨나요?
        </a>
      </footer>
    </main>
  );
}
