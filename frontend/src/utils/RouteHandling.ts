import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context';
import { RouteUrl } from './constans/routeEnum';

/**
 * 메인페이지로 redirect을 해준다.
 * @param router
 */
export function replaceRouterMain(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_MAIN);
}

/**
 * 초기 페이지로 redirect을 해준다.
 * @param router
 */
export function replaceRouterInitialize(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_INITIALIZE);
}

/**
 * 이메일 찾기 페이지로 redirect을 한다.
 * @param router
 */
export function replaceRouterFindEmail(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_FIND_EMAIL);
}

/**
 * 이메일 찾기 페이지로 redirect을 한다.
 * @param router
 */
export function replaceRouterFindPassword(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_FIND_PASSWORD);
}
