package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.ApiName;
import com.technhongplus.sellengeapi.SellengeApiApplication;
import com.technhongplus.sellengeapi.dto.nh.CreateVirtualAccountDto;
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

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final NhTransactionCountService nhTransactionCountService;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    @Value("${nh.juntaek.accessToken}")
    private String accessToken;

    // 회원가입, 로그인 구현

    @Transactional
    public void init() {
        sellerSignUp();
        signUp();
    }

    @Transactional
    public Long sellerSignUp() {
        Member seller = Member.of("seller", "판매자", "0000000000", true);

        HttpHeaders httpHeaders = SellengeApiApplication.getHttpHeaders();
        NhApiHeader header = buildApiHeader();
        CreateVirtualAccountDto dto = buildDto(header, true);

        URI uri = URI.create(ApiName.P2PNVirtualAccountNumberRequest.getRequestUrl());
        HttpEntity<CreateVirtualAccountDto> request = new HttpEntity<>(dto, httpHeaders);
        CreateVirtualAccountDto response = restTemplate.postForObject(uri, request, CreateVirtualAccountDto.class);

        System.out.println(response);

        seller.setVirtualAccount(response.getVran());

        Member saveSeller = memberRepository.save(seller);

        return saveSeller.getId();
    }

    @Transactional
    public Long signUp() {
        Member seller = Member.of("user", "일반회원", "0000000000", false);

        HttpHeaders httpHeaders = SellengeApiApplication.getHttpHeaders();
        NhApiHeader header = buildApiHeader();
        CreateVirtualAccountDto dto = buildDto(header, false);

        URI uri = URI.create(ApiName.P2PNVirtualAccountNumberRequest.getRequestUrl());
        HttpEntity<CreateVirtualAccountDto> request = new HttpEntity<>(dto, httpHeaders);
        CreateVirtualAccountDto response = restTemplate.postForObject(uri, request, CreateVirtualAccountDto.class);

        System.out.println(response);

        seller.setVirtualAccount(response.getVran());

        Member saveSeller = memberRepository.save(seller);

        return saveSeller.getId();
    }

    private NhApiHeader buildApiHeader() {
        Long nhTxCount = nhTransactionCountService.getCountNhTx();
        return NhApiHeader.of(
                ApiName.P2PNVirtualAccountNumberRequest.name(),
                "000710",
                nhTxCount,
                accessToken
        );
    }

    private CreateVirtualAccountDto buildDto(NhApiHeader header, Boolean isSeller) {
        return CreateVirtualAccountDto.builder()
                .Header(header)
                .P2PCmtmNo("0000000000")
                .ChidSqno("0000000000")
                .P2PVractUsg(isSeller ? "2" : "1")
                .InvstBrwNm("오준택")
                .build();
    }
}
