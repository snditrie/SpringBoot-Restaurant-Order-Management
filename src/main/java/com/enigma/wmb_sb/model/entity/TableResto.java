package com.enigma.wmb_sb.model.entity;

import com.enigma.wmb_sb.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.TABLE)
public class TableResto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "table_name")
    private String name;
}
