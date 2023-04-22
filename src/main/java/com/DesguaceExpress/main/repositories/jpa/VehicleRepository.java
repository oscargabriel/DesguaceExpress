package com.DesguaceExpress.main.repositories.jpa;

import com.DesguaceExpress.main.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Long> {
}
