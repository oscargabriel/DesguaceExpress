package com.DesguaceExpress.main.repositories.jpa;

import com.DesguaceExpress.main.entities.VehicleParking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleParkingRepository extends CrudRepository<VehicleParking,Long> {
}
