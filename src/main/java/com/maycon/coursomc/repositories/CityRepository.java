package com.maycon.coursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maycon.coursomc.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

}
