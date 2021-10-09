package com.calculo.frete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calculo.frete.model.CalculoCep;
/**
 *
 * @author Léo
 */
@Repository
public interface CalculoCepRepository extends JpaRepository<CalculoCep, Long>{

}
