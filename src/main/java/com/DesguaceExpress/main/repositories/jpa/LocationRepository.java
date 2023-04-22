package com.DesguaceExpress.main.repositories.jpa;

import com.DesguaceExpress.main.entities.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

}
