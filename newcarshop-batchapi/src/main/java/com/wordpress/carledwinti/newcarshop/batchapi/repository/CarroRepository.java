package com.wordpress.carledwinti.newcarshop.batchapi.repository;

import com.wordpress.carledwinti.newcarshop.batchapi.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
}

