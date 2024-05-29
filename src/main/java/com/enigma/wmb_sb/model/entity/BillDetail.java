package com.enigma.wmb_sb.model.entity;

import com.enigma.wmb_sb.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = ConstantTable.BILL_DETAIL)
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonBackReference
    private Bill billId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menuId;

    @Column(name = "qty", nullable = false)
    private Integer qty;
}
