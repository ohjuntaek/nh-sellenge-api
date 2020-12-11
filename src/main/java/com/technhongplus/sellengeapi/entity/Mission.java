package com.technhongplus.sellengeapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime proofDate; // 인증일시

    private Boolean success; // 성공여부
}
