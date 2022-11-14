package com.promo.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Items implements Serializable {
    @Id
    private String itemId;
    private BigDecimal itemPrice;
}
