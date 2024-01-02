import { HTTP_STATUS_OK } from '@/utils/constans/httpStatus';
import { MODAL_TITLE_DANGER, MODAL_TITLE_SUCCESS } from '@/utils/constans/modalTitle';

/**
 *  httpStatusMessage을 판단한다 OK이면 true을 반환하라.
 * @param httpMessage
 */
export function checkHttpStatus(httpStatusMessage: string): boolean {
  if (httpStatusMessage === HTTP_STATUS_OK) {
    return true;
  }
  return false;
}
