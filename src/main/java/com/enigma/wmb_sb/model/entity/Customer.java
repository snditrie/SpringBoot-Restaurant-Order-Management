package com.enigma.wmb_sb.model.entity;

import com.enigma.wmb_sb.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.CUSTOMER)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_phone")
    private String phoneNumber;

    @Column(name = "member_status")
    private Boolean isMember;

    @OneToOne
    @JoinColumn(name = "user_account_id", unique = true)
    private UserAccount userAccount;

}
