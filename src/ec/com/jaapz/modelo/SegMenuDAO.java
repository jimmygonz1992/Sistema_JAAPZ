package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SegMenuDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<SegMenu> getListaMenu(){
		List<SegMenu> resultado = new ArrayList<SegMenu>();
		Query query = getEntityManager().createNamedQuery("SegMenu.findAll");
		resultado = (List<SegMenu>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<SegMenu> getListaMenuAccesos(){
		List<SegMenu> resultado = new ArrayList<SegMenu>();
		Query query = getEntityManager().createNamedQuery("SegMenu.buscarMenu");
		resultado = (List<SegMenu>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<SegMenu> getMenuPadre(){
		List<SegMenu> resultado = new ArrayList<SegMenu>();
		Query query = getEntityManager().createNamedQuery("SegMenu.BuscarPadre");
		resultado = (List<SegMenu>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<SegMenu> getMenuPadreByIdMenu(Integer idMenu){
		List<SegMenu> resultado = new ArrayList<SegMenu>();
		Query query = getEntityManager().createNamedQuery("SegMenu.BuscarPadrePorId");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", idMenu);
		resultado = (List<SegMenu>) query.getResultList();
		return resultado;
	}
}
