package com.DesguaceExpress.main.repositories.jpa;

import com.DesguaceExpress.main.entities.Parking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends CrudRepository<Parking,Long> {
}
