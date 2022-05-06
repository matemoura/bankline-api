package com.moura.bankline.banklineapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moura.bankline.banklineapi.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {

	public List<Movimentacao>findByIdConta(Integer idConta);	
}
