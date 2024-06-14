package com.example.quiz.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository

public interface QuizDao extends JpaRepository<Quiz,Integer>{
	//Containing:¼Ò½k·j´M
	public List<Quiz> findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String name,
			LocalDate start, LocalDate end);

}
