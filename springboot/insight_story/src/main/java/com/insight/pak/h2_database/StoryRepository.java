package com.insight.pak.h2_database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StoryRepository extends JpaRepository<Story, Integer>{
}
