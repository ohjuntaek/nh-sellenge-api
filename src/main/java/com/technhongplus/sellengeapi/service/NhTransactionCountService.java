package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.entity.NhTransactionCount;
import com.technhongplus.sellengeapi.repository.NhTransactionCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NhTransactionCountService {
    private final NhTransactionCountRepository nhTransactionCountRepository;

    @Transactional
    public Long getCountNhTx() {
        nhTransactionCountRepository.updateCount();
        return nhTransactionCountRepository.getCount();
    }
}
