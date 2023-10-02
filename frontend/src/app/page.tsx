'use client';
import Header from '../atoms/molecule/header';
import MainCarousel from '../atoms/template/main-carousel';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../states/index';
import { useRouter } from 'next/navigation';

export default function Home() {
  const router = useRouter();
  useEffect(() => {
    if (userToken) {
      router.replace('/main');
    }
  });
  return (
    <main>
      <header>
        <Header isVisible={true} />
      </header>
      <article>
        <MainCarousel />
      </article>
    </main>
  );
}
