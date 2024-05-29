package com.enigma.wmb_sb.model.entity;

import com.enigma.wmb_sb.constant.ConstantTable;
import com.enigma.wmb_sb.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = ConstantTable.USER_ROLE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
