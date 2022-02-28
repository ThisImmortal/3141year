package com.task.year.repository;

import com.task.year.entity.Lord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LordRepository extends JpaRepository<Lord, Long> {


    Page<Lord> findAll(Pageable sortedByAge);

    Boolean existsByName(String lordName);
}
