package br.com.caelum.livraria.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

public class PopulaBanco {

	public static void main(String[] args) {

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();

		Autor a1 = geraAutor("Machado de Assis");
		em.persist(a1);
		Autor a2 = geraAutor("Jorge Amado");
		em.persist(a2);
		Autor a3 = geraAutor("Paulo Coelho");
		em.persist(a3);

		Livro casmurro = geraLivro("178-8-52-504464-8", "Dom Casmurro",
				"10/01/1899", 24.90, a1);
		Livro memorias = geraLivro("178-8-50-815415-9",
				"Memorias Postumas de Bras Cubas", "01/01/1881", 19.90, a1);
		Livro quincas = geraLivro("178-8-50-804084-1", "Quincas Borba",
				"01/01/1891", 16.90, a1);
		Livro alquemista = geraLivro("178-8-57-542758-3", "O Alquimista",
				"01/01/1988", 19.90, a3);
		Livro brida = geraLivro("178-8-50-567258-7", "Brida", "01/01/1990",
				12.90, a3);
		
		em.persist(casmurro);
		em.persist(memorias);
		em.persist(quincas);
		em.persist(alquemista);
		em.persist(brida);
	
		em.getTransaction().commit();
		em.close();

	}

	private static Autor geraAutor(String nome) {
		Autor autor = new Autor();
		autor.setNome(nome);
		return autor;
	}

	private static Livro geraLivro(String isbn, String titulo, String data,
			double preco, Autor autor) {
		Livro livro = new Livro();
		livro.setIsbn(isbn);
		livro.setTitulo(titulo);
		livro.setDataLancamento(parseData(data));
		livro.setPreco(preco);
		livro.adicionaAutor(autor);
		return livro;
	}

	@SuppressWarnings("unused")
	private static Calendar parseData(String data) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
