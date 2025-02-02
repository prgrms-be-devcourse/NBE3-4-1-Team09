package com.nbe3.cafemanagement.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(nullable = false)
    private int totalPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdAt;


    @Getter
    @AllArgsConstructor
    public enum OrderStatus {
        PREPARING("배송준비중"),
        SHIPPING("배송중"),
        DELIVERED("배송완료");

        private final String status;

    }

    @OneToMany(mappedBy = "customerOrder")
    @BatchSize(size = 100)
    private List<OrderDetail> orderDetails;
}
