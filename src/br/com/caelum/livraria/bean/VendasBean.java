package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.VendaDao;

import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	VendaDao dao;

	public BarChartModel getVendasModel() {

	    BarChartModel model = new BarChartModel();
	    model.setAnimate(true);
	    List<Venda> vendas;
	     
	    ChartSeries serieVendas = new ChartSeries();
	    serieVendas.setLabel("Vendas Totais");
	    
	    vendas = getVendas();
	    for (Venda venda : vendas) {
	        serieVendas.set(venda.getLivro().getTitulo(), venda.getQuantidade());
	    }
	        
	    model.addSeries(serieVendas);

	    return model;
	}
	
	public List<Venda> getVendas() {
	   List<Venda> vendas = dao.listaTodos();
	    return vendas;
	}
	
}
