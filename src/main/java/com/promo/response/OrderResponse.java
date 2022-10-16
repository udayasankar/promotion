package com.promo.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderResponse {
    private Integer orderTotal;
}
