package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.CustomerOrder;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.repository.MainRepository;
import com.nbe3.cafemanagement.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final MainRepository mainRepository;

    public void save(CustomerOrder order, String products) {
        JSONArray jsonArray = new JSONArray(products);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Product product = mainRepository.findById(jsonObject.getLong("id"))
                    .orElseThrow();
            int quantity = Integer.parseInt(jsonObject.get("quantity").toString());
            int price = Integer.parseInt(jsonObject.get("price").toString());

            OrderDetail orderDetail = OrderDetail.builder()
                    .customerOrder(order)
                    .product(product)
                    .quantity(quantity)
                    .price(quantity * price)
                    .build();

            orderDetailRepository.save(orderDetail);
        }
    }

    public List<OrderDetail> findByCustomerOrderId(Long id) {
        return orderDetailRepository.findByCustomerOrderId(id);
    }

    public Page<OrderDetail> pagination(List<OrderDetail> orderDetails, int page) {

        int pageSize = 5;
        int start = page * pageSize;
        int end = Math.min(start + pageSize, orderDetails.size());

        return new PageImpl<>(
                orderDetails.subList(start, end),
                Pageable.ofSize(pageSize).withPage(page),
                orderDetails.size()
        );
    }
}
