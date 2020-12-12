package com.technhongplus.sellengeapi.dto;

import com.technhongplus.sellengeapi.entity.Mission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionDto {
    private Long id;
    private Long challengeId; //챌린지 아이디
    private String imageUrl; // 인증 이미지
    private LocalDateTime proofDate; // 인증 날짜

    public static MissionDto from(Mission mission) {
        return MissionDto.builder()
            .id(mission.getId())
            .challengeId(mission.getJoinChallenge().getId())
            .imageUrl(mission.getImageUrl())
            .proofDate(mission.getProofDate())
            .build();
    }
}
