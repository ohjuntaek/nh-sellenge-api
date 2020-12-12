package com.technhongplus.sellengeapi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "MEMBERS")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String loginId;
    private String name;
    private String businessNumber;
    private String birthday;
    private Boolean isSeller;

    private String virtualAccount;
    private String p2pCmtmNo; // P2P약정번호
    private String finAccount;
    private String accountNo;

    public static Member of(
            String loginId,
            String name,
            String businessNumber,
            String birthday,
            String accountNo,
            Boolean isSeller
    ) {
        return new Member(null, loginId, name, businessNumber, birthday, isSeller, null, null, null, accountNo);
    }
}
