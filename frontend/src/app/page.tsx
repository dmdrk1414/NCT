import Header from '../atoms/molecule/header';
import MainCarousel from '../atoms/template/main-carousel';

export default function Home() {
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
