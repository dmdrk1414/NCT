/**
 * token여부 판별
 * @param token
 * @returns 있으면 true
 */
export function hasToken(token: string): boolean {
  return token !== null && token !== undefined && token !== '';
}

/**
 * token여부 판변
 * @param token
 * @returns 없으면 true
 */
export function hasNotToken(token: string): boolean {
  return !hasToken(token);
}

/**
 * 실장확인 검증
 * @param isNuriKing
 * @returns 실장이 아니면 true
 */
export function isNotNuriKing(isNuriKing: boolean): boolean {
  return !isNuriKing;
}
