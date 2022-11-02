package com.promo.repository;

import com.promo.entities.ActivePromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivePromotionsRepository extends JpaRepository<ActivePromotion, String> {
}
