package com.DesguaceExpress.main.repositories.jpa;

import com.DesguaceExpress.main.entities.Members;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends CrudRepository<Members,Long> {
}
