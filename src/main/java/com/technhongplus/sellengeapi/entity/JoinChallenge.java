package com.technhongplus.sellengeapi.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "JOIN_CHALLENGE")
public class JoinChallenge {
    @Id @Column(name = "JOIN_CHALLENGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @NaturalId
    @EqualsAndHashCode.Include
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHALLENGE_ID")
    @NaturalId
    @EqualsAndHashCode.Include
    private Challenge challenge;

    public static JoinChallenge of(Member member, Challenge challenge) {
        return new JoinChallenge(null, member, challenge);
    }
}
