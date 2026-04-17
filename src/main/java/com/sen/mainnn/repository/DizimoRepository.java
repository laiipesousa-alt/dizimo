package com.sen.mainnn.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sen.mainnn.model.Dizimo;

import java.util.List;
public interface DizimoRepository extends JpaRepository<Dizimo, Long> {
    List<Dizimo> findByNomeMembroContainingIgnoreCase(String nome);
}