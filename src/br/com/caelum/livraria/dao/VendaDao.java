package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Venda;



public class VendaDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<Venda> dao;
	
	@PostConstruct
	void init() {
		dao = new DAO<Venda>(em, Venda.class);
	}

	public List<Venda> listaTodos() {
		return dao.listaTodos();
	}

}
