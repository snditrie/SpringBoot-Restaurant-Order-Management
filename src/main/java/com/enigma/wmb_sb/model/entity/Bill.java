package com.enigma.wmb_sb.model.entity;

import com.enigma.wmb_sb.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = ConstantTable.BILL)
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trans_date")
    private Date transDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableResto tableRestoId;

    @ManyToOne
    @JoinColumn(name = "trans_type_id")
    private TransactionType transactionTypeId;

    @OneToMany(mappedBy = "billId")
    @JsonManagedReference
    private List<BillDetail> billDetails;
}
