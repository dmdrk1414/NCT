'use client';
import { axAuth } from '@/apis/axiosinstance';
import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../../../states/index';
import { hasNotToken, isNotNuriKing } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize, replaceRouterMain } from '@/utils/RouteHandling';
import { useRouter } from 'next/navigation';
import CurrentNewMember from '@/atoms/molecule/current-new-member';
import NewMemberInformationModal from '@/atoms/molecule/new-member-infomation-modal';

type userData = {
  id: number;
  name: string;
  email: string;
  applicationDate: string;
};

export default function JoinApplicationForm() {
  const [token, setToken] = useRecoilState(userToken);
  const [data, setData] = useState<userData[]>();
  const [refresh, setRefresh] = useState(0);
  const router = useRouter();
  const [isNewMemberInfoOpen, setIsNewMemberInfoOpen] = useState(0);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }

    // 실장이 아니면 메인페이지으로 이동한다.
    if (isNotNuriKing(isKing)) {
      replaceRouterMain(router);
    }
  }, []);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/new-users',
    })
      .then(res => {
        console.log(res.data);
        setData(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  }, [refresh]);

  return (
    <div className="m-[2rem]">
      {isNewMemberInfoOpen !== 0 ? (
        <NewMemberInformationModal userId={isNewMemberInfoOpen} setIsNewMemberInfoOpen={setIsNewMemberInfoOpen} isKing={isKing} setRefresh={setRefresh} refresh={refresh} />
      ) : null}
      <div className="text-center font-bold text-2xl mb-[2rem]">신입 신청 리스트</div>
      {data?.map((item, idx) => <CurrentNewMember key={idx} userId={item.id} applicationDate={item.applicationDate} name={item.name} setIsNewMemberInfoOpen={setIsNewMemberInfoOpen} />)}
    </div>
  );
}
