package com.enigma.wmb_sb.model.entity;

import com.enigma.wmb_sb.constant.ConstantTable;
import com.enigma.wmb_sb.constant.EnumTransactionType;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.TRANS_TYPE)
public class TransactionType {
    @Id
    @Column(name = "id")
    @Enumerated(EnumType.STRING)
    private EnumTransactionType id;

    @Column(name = "description")
    private String description;
}
