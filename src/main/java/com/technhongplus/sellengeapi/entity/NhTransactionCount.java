package com.technhongplus.sellengeapi.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "NH_TRANSACTION_COUNT")
@AllArgsConstructor
@NoArgsConstructor
public class NhTransactionCount {
    @Id @GeneratedValue
    private Long id;
    private Long count;
}
