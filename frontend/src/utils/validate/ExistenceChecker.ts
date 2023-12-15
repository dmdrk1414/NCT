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
