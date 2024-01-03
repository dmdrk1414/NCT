import ThinButton from './thin-button';
import { useEffect, useState } from 'react';
import { axAuth, axBase } from '@/apis/axiosinstance';
import { userToken, isNuriKing } from '../../states/index';
import { useRecoilState } from 'recoil';
import { HttpStatusCode } from 'axios';
import { HTTP_STATUS_OK } from '@/utils/constans/httpStatusEnum';

interface SuggestionAltertModalProps {
  setSuggestionWriteModalOpen: (isOpen: boolean) => void;
}

export default function SuggestionAltertModal(data: SuggestionAltertModalProps) {
  const [isSeleckClassification, setIsSeleckClassification] = useState(true);
  const [classification, setClassification] = useState('건의');
  const [token, setToken] = useRecoilState(userToken);
  const [suggestionTitle, setSuggestionTitle] = useState('');

  const writeSuggestion = () => {
    axAuth(token)({
      method: 'post',
      url: '/suggestion/write',
      data: {
        classification: classification,
        title: suggestionTitle,
        holidayPeriod: '',
      },
    })
      .then(response => {
        const httpStatus = response.data.httpStatus;
        if (httpStatus === HTTP_STATUS_OK) {
          handleModal();
        }
      })
      .catch(err => {
        console.log(err);
      });
  };

  const handleModal = () => {
    data.setSuggestionWriteModalOpen(false);
  };

  const toggleIsClassification = () => {
    if (isSeleckClassification) {
      setIsSeleckClassification(!isSeleckClassification);
    } else {
      setIsSeleckClassification(!isSeleckClassification);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    setSuggestionTitle(value);
  };

  useEffect(() => {
    if (isSeleckClassification) {
      setClassification('건의');
    } else {
      setClassification('자유');
    }
  }, [isSeleckClassification]);

  return (
    <div className="absolute z-10 w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center " onClick={handleModal}>
      <div className="bg-white w-[80%] h-[23%] rounded-[0.63rem]" onClick={e => e.stopPropagation()}>
        <div>
          <div className="flex border-b">
            <div className="w-[3rem] flex justify-center">분류</div>
            <div>
              <span className={`border rounded-[0.1rem] mr-[0.5rem] ${isSeleckClassification ? 'bg-blue text-white' : ''}`} onClick={toggleIsClassification}>
                건의
              </span>
              <span className={`border rounded-[0.1rem] ${!isSeleckClassification ? 'bg-blue text-white' : ''}`} onClick={toggleIsClassification}>
                자유
              </span>
            </div>
          </div>
          <div className="mt-[1rem]">
            <textarea name="suggestion_content" onChange={handleChange} placeholder="제목" className={`border border-grey  w-[100%] h-[100%]`} maxLength={500} />
          </div>
          <div className="flex h-[100%]" onClick={writeSuggestion}>
            <ThinButton text={'글쓰기'} />
          </div>
        </div>
      </div>
    </div>
  );
}
