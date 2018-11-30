package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;

import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.annotation.Transacional;
import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;

	private List<Livro> livros;

	
	@Inject
	AutorDao autorDao;
	@Inject
	LivroDao livroDao;
	
	@Inject
	FacesContext context;

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public void carregaLivroPeloId() {
		this.livro = livroDao.buscaPorId(this.livroId);
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}
		return livros;
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deve começar com 1"));
		}
	}

	public String formAutor() {
		return "autor?faces-redirect=true";
	}

	// CRUD (Create, Read, Update, Delete)
	
	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
			this.livros = livroDao.listaTodos();
		} else {
			livroDao.atualiza(this.livro);
		}

		livro = new Livro();
	}
	
	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro " + this.livro.getTitulo());
		livroDao.remove(livro);
	}

	public void carregar(Livro livro) {
		System.out.println("Carregando Livro");
		this.livro = this.livroDao.buscaPorId(livro.getId());
	}

	// REGISTRA AUTOR
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

}
