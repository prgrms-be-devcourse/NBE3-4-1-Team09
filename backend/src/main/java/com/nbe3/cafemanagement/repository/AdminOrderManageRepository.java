package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.dto.OrderRequest;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface AdminOrderManageRepository extends JpaRepository<OrderDetail, BigInteger> {
    @Query("SELECT od FROM OrderDetail od\n" +
        "JOIN fetch od.order o\n" +
        "JOIN fetch od.product p\n" +
        "where o.email like %:userEmail% and\n" +
        "p.name like %:searchParam%")
    List<OrderDetail> findAll(@Param("userEmail") String userEmail,
                              @Param("searchParam") String searchParam
                              );

    @NonNull
    @Query("SELECT od FROM OrderDetail od\n" +
        "JOIN fetch od.order o\n" +
        "JOIN fetch od.product p\n")
    List<OrderDetail> findAll();

    List<OrderDetail> findByOrder_Id(Long orderId);
}
