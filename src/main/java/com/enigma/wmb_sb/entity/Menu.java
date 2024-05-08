package com.enigma.wmb_sb.entity;

import com.enigma.wmb_sb.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = ConstantTable.MENU)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "menu_name")
    private String name;

    @Column(name = "price")
    private Long price;
}
