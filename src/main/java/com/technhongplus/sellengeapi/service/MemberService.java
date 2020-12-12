package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.ApiName;
import com.technhongplus.sellengeapi.SellengeApiApplication;
import com.technhongplus.sellengeapi.dto.nh.CreateVirtualAccountDto;
import com.technhongplus.sellengeapi.dto.nh.FinAccountDto;
import com.technhongplus.sellengeapi.dto.nh.NhApiHeader;
import com.technhongplus.sellengeapi.entity.Member;
import com.technhongplus.sellengeapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static com.technhongplus.sellengeapi.service.NhTransactionService.NH_API_SUCCESS;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final NhTransactionService nhTransactionService;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    @Value("${nh.juntaek.birthday}")
    private String birthday;
    @Value("${nh.juntaek.seller_account_no}")
    private String sellerAccountNo;
    @Value("${nh.juntaek.member_account_no}")
    private String accountNo;

    // 회원가입, 로그인 구현

    @Transactional
    public void init() {
        signUp("seller", "판매자", "0000000000", sellerAccountNo, true);
        signUp("user", "일반회원", "0000000000", accountNo, false);
    }

    /**
     * 회원가입
     */
    public Long signUp(String loginId, String name, String businessNumber, String accountNo, boolean isSeller) {
        Member member = Member.of(loginId, name, businessNumber, birthday, accountNo, isSeller);
        HttpHeaders httpHeaders = SellengeApiApplication.getHttpHeaders();

        // 핀 어카운트 발급 => 가상계좌 입금 안되서 쓸데가 없을 듯... 일단 냅둠
        setFinAccount(member, httpHeaders);

        setVirtualAccount(isSeller, member, httpHeaders);

        Member saveSeller = memberRepository.save(member);

        return saveSeller.getId();
    }

    private void setVirtualAccount(boolean isSeller, Member member, HttpHeaders httpHeaders) {
        NhApiHeader headerForVirtualAccount = nhTransactionService.buildApiHeader(ApiName.P2PNVirtualAccountNumberRequest.name());
        CreateVirtualAccountDto virtualAccountResponse = getCreateVirtualAccountDto(httpHeaders, headerForVirtualAccount, isSeller);
        if (!virtualAccountResponse.getHeader().getRpcd().equals(NH_API_SUCCESS)) {
            throw new IllegalStateException(virtualAccountResponse.getHeader().getRsms());
        }

        member.setVirtualAccount(virtualAccountResponse.getVran());
        member.setP2pCmtmNo(virtualAccountResponse.getP2PCmtmNo());
    }

    /**
     * 핀어카운트 발급 및 등록 (지금 핀어카운트 쓰는데 없음)
     */
    private void setFinAccount(Member member, HttpHeaders httpHeaders) {
        NhApiHeader headerForCreateFindAccount = nhTransactionService.buildApiHeader(ApiName.OpenFinAccountDirect.name());
        FinAccountDto createFinAccountResponse = getFinAccountDtoForCreate(httpHeaders, headerForCreateFindAccount, birthday, sellerAccountNo);
//        if (!createFinAccountResponse.getHeader().getRpcd().equals(NH_API_SUCCESS)) {
//            throw new IllegalStateException(createFinAccountResponse.getHeader().getRsms());
//        }

        NhApiHeader headerForGetFindAccount = nhTransactionService.buildApiHeader(ApiName.CheckOpenFinAccountDirect.name());
        FinAccountDto getFinAccountResponse = getFinAccountDtoForCheck(httpHeaders, headerForGetFindAccount, birthday, createFinAccountResponse.getRgno());
//        if (!getFinAccountResponse.getHeader().getRpcd().equals(NH_API_SUCCESS)) {
//            throw new IllegalStateException(createFinAccountResponse.getHeader().getRsms());
//        }

        member.setFinAccount(getFinAccountResponse.getFinAcno());
    }

    private FinAccountDto getFinAccountDtoForCreate(
            HttpHeaders httpHeaders,
            NhApiHeader headerForFinAccount,
            String birthday,
            String accountNo
    ) {
        FinAccountDto finAccountDto = FinAccountDto.ofForCreate(headerForFinAccount, birthday, accountNo);

        HttpEntity<FinAccountDto> request = new HttpEntity<>(finAccountDto, httpHeaders);

        return restTemplate.postForObject(ApiName.OpenFinAccountDirect.getUri(), request, FinAccountDto.class);
    }

    private FinAccountDto getFinAccountDtoForCheck(
            HttpHeaders httpHeaders,
            NhApiHeader headerForFinAccount,
            String birthday,
            String accountNo
    ) {
        FinAccountDto finAccountDto = FinAccountDto.ofForGet(headerForFinAccount, birthday, accountNo);

        HttpEntity<FinAccountDto> request = new HttpEntity<>(finAccountDto, httpHeaders);

        return restTemplate.postForObject(ApiName.CheckOpenFinAccountDirect.getUri(), request, FinAccountDto.class);
    }

    private CreateVirtualAccountDto getCreateVirtualAccountDto(HttpHeaders httpHeaders, NhApiHeader nhApiHeader, boolean b) {
        CreateVirtualAccountDto virtualAccountDto = buildVirtualAccountDto(nhApiHeader, b);

        HttpEntity<CreateVirtualAccountDto> request = new HttpEntity<>(virtualAccountDto, httpHeaders);
        return restTemplate.postForObject(ApiName.P2PNVirtualAccountNumberRequest.getUri(), request, CreateVirtualAccountDto.class);
    }



    private CreateVirtualAccountDto buildVirtualAccountDto(NhApiHeader header, Boolean isSeller) {
        return CreateVirtualAccountDto.builder()
                .Header(header)
                .P2PCmtmNo("0000000000")
                .ChidSqno("0000000000")
                .P2PVractUsg(isSeller ? "2" : "1")
                .InvstBrwNm("오준택")
                .build();
    }
}
