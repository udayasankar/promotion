package com.promo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Items implements Serializable {
    @Id
    private String itemId;
    private BigDecimal itemPrice;
}
