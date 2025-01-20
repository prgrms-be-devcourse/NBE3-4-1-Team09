package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;


public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByEmailOrderByCreatedAtDesc(String email);

    List<CustomerOrder> findByEmailAndOrderDateBetweenOrderByCreatedAtDesc(String email, LocalDate dayFrom, LocalDate dayUntil);

    @NonNull
    @Query(
        "select DISTINCT o from CustomerOrder o\n"+
        "JOIN FETCH o.orderDetails od\n" +
        "JOIN FETCH od.product p\n" +
        "where o.email like %:userEmail% and\n" +
        "o.orderDate between :dayFrom and :dayUntil\n"
    )
    Page<CustomerOrder> findAll(@Param("userEmail") String userEmail,
                                @Param("dayFrom") LocalDate dayFrom,
                                @Param("dayUntil") LocalDate dayUntil,
                                Pageable pageable);
}
