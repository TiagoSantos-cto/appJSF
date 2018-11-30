package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class DAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<T> classe;
	
	private EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) {
		this.em = manager;
		this.classe = classe;
	}

	//CRUD
	public void adiciona(T t) {
		em.persist(t);
	}
	public void remove(T t) {
		em.remove(em.merge(t));
	}
	public void atualiza(T t) {
		em.merge(t);
	}

	//FUNCIONALIDADES
	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public T buscaPorId(Integer id) {
		T instancia = em.find(classe, id);
		return instancia;
	}

	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		return lista;
	}
	
	public boolean existe(Usuario usuario) {			
		try {
			Usuario result = (Usuario) em.createQuery("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha", Usuario.class)
				.setParameter("pEmail", usuario.getEmail())
				.setParameter("pSenha", usuario.getSenha())
				.getSingleResult();
			
			System.out.println("EXISTE: "+ result);
			
		}catch (NoResultException ex) {
			return false;
		}
		return true;
	}

}
