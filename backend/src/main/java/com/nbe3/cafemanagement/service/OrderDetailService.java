package com.nbe3.cafemanagement.service;

import com.nbe3.cafemanagement.domain.Order;
import com.nbe3.cafemanagement.domain.OrderDetail;
import com.nbe3.cafemanagement.domain.Product;
import com.nbe3.cafemanagement.repository.MainRepository;
import com.nbe3.cafemanagement.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final MainRepository mainRepository;

    public void save(Order order, String products) {
        JSONArray jsonArray = new JSONArray(products);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Product product = mainRepository.findById(jsonObject.getLong("id"))
                    .orElseThrow();
            int quantity = Integer.parseInt(jsonObject.get("quantity").toString());
            int price = Integer.parseInt(jsonObject.get("price").toString());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .quantity(quantity)
                    .price(quantity * price)
                    .build();

            orderDetailRepository.save(orderDetail);
        }
    }
}
