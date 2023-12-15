/**
 * token여부 판별
 * @param token
 * @returns 있으면 true
 */
export function hasToken(token: string): boolean {
  return token !== null && token !== undefined && token !== '';
}
