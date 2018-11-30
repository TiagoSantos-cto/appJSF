package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.annotation.Transacional;
import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@Named
@ViewScoped
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	private UsuarioDao dao;
	
	@Inject
	FacesContext context;
	
	public Usuario getUsuario() {
		return usuario;
	}
	

	@Transacional
	public String gravar() {
		System.out.println("Gravando usuário " + this.usuario.getNome());		
		this.dao.adiciona(this.usuario);
		return "index?faces-redirect=true";
	}

}
