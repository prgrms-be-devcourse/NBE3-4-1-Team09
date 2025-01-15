package com.nbe3.cafemanagement.repository;

import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.dto.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface AdminOrderManageRepository extends JpaRepository<OrderDetail, BigInteger> {
    @Query("select OrderDetail od from OrderDetail od \n" +
        "inner join `Order` o ON order_id = o.id\n" +
        "inner join Product p on product_id = p.id")
    Page<OrderDetail> findAll(@Param("userEmail") String userEmail,
                              @Param("searchParam") String searchParam,
                              Pageable pageable);
}
