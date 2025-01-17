package com.nbe3.cafemanagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String description;

    private String imageUrl;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean delFlag = false;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean active_flag = false;
}
