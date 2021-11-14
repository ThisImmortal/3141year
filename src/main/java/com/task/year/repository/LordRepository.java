package com.task.year.repository;

import com.task.year.entity.Lord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LordRepository extends JpaRepository<Lord, Long> {

    @Query(value = "SELECT * FROM lords ORDER BY age ASC LIMIT 10", nativeQuery = true)
    public List<Lord> getTenTheYoungestLords();

    public Lord getByName(String name);

    Boolean existsByName(String lordName);
}
