package com.technhongplus.sellengeapi.repository;

import com.technhongplus.sellengeapi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
