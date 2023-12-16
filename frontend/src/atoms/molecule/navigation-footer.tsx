import React from 'react';
import Navigation from '../template/navigation';

type data = {
  isKing: boolean;
};

export default function NavigationFooter({ isKing }: data) {
  return (
    <footer className="mt-[10rem]">
      <Navigation now={1} isNuriKing={isKing} />
    </footer>
  );
}
