package com.promo.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "activepromotion")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
