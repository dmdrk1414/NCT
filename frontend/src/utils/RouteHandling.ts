import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context';

/**
 * 메인페이지로 redirect을 해준다.
 * @param router
 */
export function replaceRouterMain(router: AppRouterInstance): void {
  router.replace('/main');
}
