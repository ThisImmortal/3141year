package com.task.year.repository;

import com.task.year.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Boolean existsByName(String planetName);
}
