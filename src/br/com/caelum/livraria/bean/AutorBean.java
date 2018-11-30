package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.annotation.Transacional;
import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;

@Named
@ViewScoped
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Autor autor = new Autor();
	private Integer autorId;
	
	@Inject
	private AutorDao dao;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}


	public void carregaAutorPeloId() {
		this.autor = this.dao.buscaPorId(autorId);
	}
	
	public Autor getAutor() {
		return autor;
	}
	
	
	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}
	
	@Transacional
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		
		if(this.autor.getId()==null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(this.autor);
			this.autor = new Autor();
			return "autor";
		}
		
		this.autor = new Autor();
		
		return "autor?faces-redirect=true";
	}
	
	@Transacional
	public void remover(Autor autor) {
		System.out.println("Removendo Autor");
		this.dao.remove(autor);
	}
	
	public void carregar(Autor autor) {
		System.out.println("Carregando Autor" );
		this.autor=autor;		
	}
}
