package com.promo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="activepromotion")
public class ActivePromotion implements Serializable {
    @Id
    @Column(name = "ITEM_ID")
    private String itemId;
    @Column(name = "PROMO_ITEM_ID")
    private String promoItemId;
    @Column(name = "PROMO_NO")
    private Integer promoNo;
    @Column(name = "PROMO_ITEM_PRICE")
    private BigDecimal promoItemPrice;
    @Column(name = "PROMO_ITEM_IDS")
    private String getPromoItemIds;
}
