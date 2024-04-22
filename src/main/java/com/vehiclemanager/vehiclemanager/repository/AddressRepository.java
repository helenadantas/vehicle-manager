package com.vehiclemanager.vehiclemanager.repository;

import com.vehiclemanager.vehiclemanager.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
