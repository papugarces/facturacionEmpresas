package com.eam.IngSoft1.IRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Authority;

@Repository
public interface IAuthorityRepository extends CrudRepository<Authority, Integer>  {
    public Authority findByAuthority(String authority);
}