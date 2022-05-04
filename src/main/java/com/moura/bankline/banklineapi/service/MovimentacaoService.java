package com.moura.bankline.banklineapi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moura.bankline.banklineapi.dto.NovaMovimentacao;
import com.moura.bankline.banklineapi.model.Correntista;
import com.moura.bankline.banklineapi.model.Movimentacao;
import com.moura.bankline.banklineapi.model.MovimentacaoTipo;
import com.moura.bankline.banklineapi.repository.CorrentistaRepository;
import com.moura.bankline.banklineapi.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {

	@Autowired 
	private MovimentacaoRepository repository;
	
	@Autowired
	private CorrentistaRepository correntistaRepository;
	
	public void save(NovaMovimentacao novaMovimentacao) {
		Movimentacao movimentacao = new Movimentacao();
		
		Double valor = novaMovimentacao.getValor();
		if(novaMovimentacao.getTipo() == MovimentacaoTipo.DESPESA)
			valor = valor * -1;
		
		movimentacao.setDataHora(LocalDateTime.now());
		movimentacao.setDescricao(novaMovimentacao.getDescricao());
		movimentacao.setIdConta(novaMovimentacao.getIdConta());
		movimentacao.setTipo(novaMovimentacao.getTipo());
		movimentacao.setValor(valor);
		
		Correntista correntista = correntistaRepository.findById(novaMovimentacao.getIdConta()).orElse(null);
		if(correntista != null) {
			correntista.getConta().setSaldo(correntista.getConta().getSaldo() + valor);
			correntistaRepository.save(correntista);
		}
		
		repository.save(movimentacao);
	}
}
