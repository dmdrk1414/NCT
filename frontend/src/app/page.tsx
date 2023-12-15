'use client';
import Header from '../atoms/molecule/header';
import MainCarousel from '../atoms/template/main-carousel';
import { useEffect } from 'react';
import { userToken } from '../states/index';
import { useRouter } from 'next/navigation';

export default function Home() {
  const router = useRouter();
  return (
    <>
      <header>
        <Header isVisible={true} />
      </header>
      <main>
        <article>
          <MainCarousel />
        </article>
      </main>
    </>
  );
}
