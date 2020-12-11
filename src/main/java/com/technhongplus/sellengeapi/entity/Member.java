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
    private Boolean isSeller;

    private String virtualAccount;

    public static Member of(String loginId, String name, String businessNumber, Boolean isSeller) {
        return new Member(null, loginId, name, businessNumber, isSeller, null);
    }
}
