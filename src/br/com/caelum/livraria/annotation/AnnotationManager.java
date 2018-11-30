package br.com.caelum.livraria.annotation;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class AnnotationManager implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager manager;
	
	@AroundInvoke
	public Object executaTX(InvocationContext contexto) throws Exception {
	    manager.getTransaction().begin();
	    Object resultado = contexto.proceed(); // chama os daos que precisam de uma transação
	    manager.getTransaction().commit();

	    return resultado;    
	}

}
