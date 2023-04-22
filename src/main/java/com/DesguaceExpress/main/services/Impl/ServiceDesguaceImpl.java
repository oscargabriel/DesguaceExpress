package com.DesguaceExpress.main.services.Impl;

import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.repositories.dao.Impl.RepositoryPostgreImpl;
import com.DesguaceExpress.main.repositories.dao.RepositoryDesguace;
import com.DesguaceExpress.main.repositories.jpa.MembersRepository;
import com.DesguaceExpress.main.services.ServiceDesguace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDesguaceImpl implements ServiceDesguace {

    RepositoryDesguace repositoryDesguace;

    @Autowired
    MembersRepository membersRepository;

    public ServiceDesguaceImpl(RepositoryPostgreImpl repositoryDesguace) {
        this.repositoryDesguace = repositoryDesguace;
    }

    @Override
    public String crearSocio(Members members) {
        membersRepository.save(members);
        return "creacion exitosa";
    }
}
