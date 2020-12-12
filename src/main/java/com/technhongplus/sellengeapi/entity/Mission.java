package com.technhongplus.sellengeapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "MISSION")
public class Mission {
    @Id @Column(name = "MISSION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOIN_ID")
    private JoinChallenge joinChallenge;

    private String imageUrl; // 인증 이미지

    private LocalDate proofDate; // 인증일시

    private Boolean success; // 성공여부

    public static Mission of(JoinChallenge joinChallenge,String imageUrl ,LocalDate proofDate){
        return Mission.builder()
            .joinChallenge(joinChallenge)
            .imageUrl(imageUrl)
            .proofDate(proofDate)
            .success(false)
            .build();
    }
}
