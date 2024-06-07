package com.insight.pak;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Story {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

	@Column(length = 30)
    private String name;
    
	@Column(length = 500)
    private String prompt;

    @Column(length = 500)
    private String summary;
    
    @Column(length = 300)
    private String link;
}