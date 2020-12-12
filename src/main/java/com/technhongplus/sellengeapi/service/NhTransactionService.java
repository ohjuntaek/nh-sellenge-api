package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.dto.nh.NhApiHeader;
import com.technhongplus.sellengeapi.entity.NhTransactionCount;
import com.technhongplus.sellengeapi.repository.NhTransactionCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NhTransactionService {
    public static final String NH_API_SUCCESS = "00000";

    @Value("${nh.juntaek.code}")
    private String iscd;

    @Value("${nh.juntaek.accessToken}")
    private String accessToken;

    private final NhTransactionCountRepository nhTransactionCountRepository;

    @Transactional
    public Long getCountNhTx() {
        nhTransactionCountRepository.updateCount();
        return nhTransactionCountRepository.getCount();
    }

    public NhApiHeader buildApiHeader(String apiName) {
        Long nhTxCount = getCountNhTx();
        return NhApiHeader.of(
                apiName,
                iscd,
                nhTxCount,
                accessToken
        );
    }
}
