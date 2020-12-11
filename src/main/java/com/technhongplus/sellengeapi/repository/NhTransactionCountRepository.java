package com.technhongplus.sellengeapi.repository;

import com.technhongplus.sellengeapi.entity.NhTransactionCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NhTransactionCountRepository extends JpaRepository<NhTransactionCount, Long> {
    @Query("select t.count from NhTransactionCount t where t.id = 1")
    Long getCount();

    @Modifying(clearAutomatically = true)
    @Query("update NhTransactionCount t set t.count = t.count + 1 where t.id = 1")
    void updateCount();
}
