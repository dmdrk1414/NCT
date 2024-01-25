import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { axAuth } from '@/apis/axiosinstance';
import { HTTP_STATUS_OK } from '@/utils/constans/httpStatusEnum';

type Data = {
  commentId: number;
  name: string;
  content: string;
  date: string;
  author: boolean;
  articleId: number | undefined;
};

export default function SuggestionComment(data: Data) {
  const [token, setToken] = useRecoilState(userToken);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const justify = `flex justify-center`;
  const nameCss = `w-[18%] flex  mt-[0.2rem] ${justify} `;
  const commentCss = `w-[64%]  mt-[0.2rem] ${justify} `;
  const deleteCss = `w-[18%] mt-[0.2rem] ${justify} `;
  const titleLen = 30;

  // 댓글 삭제
  const deleteComment = (id: number | undefined) => {
    axAuth(token)({
      method: 'post',
      url: '/suggestion/' + id + '/comment', // api 제작시 url 변경 필요
      data: {
        id: data.articleId,
        commentId: data.commentId,
      },
    })
      .then(response => {
        const httpStatus = response.data.httpStatus;
        if (httpStatus === HTTP_STATUS_OK) {
          alert('댓글이 삭제되었습니다.');
        }
      })
      .catch(err => {
        console.log(err);
      });
  };

  // 댓글의 글자수를 계산하여 30자를 초과하면 댓글창 크기를 증가 시킨다.
  const calculateHeight = () => {
    const additionalHeight = Math.ceil(data.content.length / titleLen) - 1;
    const totalHeight = 4 + additionalHeight * 3;
    return `h-[${totalHeight}rem]`;
  };

  const dynamicHeightClass = calculateHeight();

  return (
    <div className={`flex justify-center items-center border border-blue ${dynamicHeightClass}`}>
      <div className={`${nameCss}`}>{data.name}</div>
      <div className={`${commentCss} flex justify-between ml-[0.2rem]`}>
        <span>{data.content}</span>
      </div>
      <div className={`${deleteCss}`}>
        {data.author ? (
          <div>
            <button type="button" className="mt-[0.2rem] mb-[0.6rem]" onClick={() => deleteComment(data.articleId)}>
              삭제
            </button>
            <div className="text-xs text-grey">{data.date}</div>
          </div>
        ) : (
          <div>
            <div className="mt-[3em] text-xs text-grey">{data.date}</div>
          </div>
        )}
      </div>
    </div>
  );
}
