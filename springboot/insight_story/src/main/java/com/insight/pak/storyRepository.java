package com.insight.pak;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface storyRepository extends JpaRepository<story, Integer> {
    Optional<story> findById(Integer id);
    List<story> findAll();
}
