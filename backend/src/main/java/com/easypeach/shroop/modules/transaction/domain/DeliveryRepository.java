package com.easypeach.shroop.modules.transaction.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

	Optional<Delivery> findByTrackingNumber(String trackingNumber);
}
