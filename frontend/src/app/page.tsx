import MainHeader from '../atoms/molecule/main-header';
import MainCarousel from '../atoms/template/main-carousel';

export default function Home() {
  return (
    <main>
      <header>
        <MainHeader />
      </header>
      <article>
        <MainCarousel />
      </article>
    </main>
  );
}
