package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Usuario;


public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<Usuario> dao;
	
	@PostConstruct
	void init() {
		dao = new DAO<Usuario>(em, Usuario.class);
	}

	public void adiciona(Usuario t) {
		dao.adiciona(t);
	}

	public boolean existe(Usuario usuario) {
		return dao.existe(usuario);
	}

	
}
