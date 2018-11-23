package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class BarrioDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Barrio> getListaBarrios(){
		List<Barrio> resultado = new ArrayList<Barrio>();
		Query query = getEntityManager().createNamedQuery("Barrio.findAll");
		resultado = (List<Barrio>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Barrio> getListaBarriosActivos(){
		List<Barrio> resultado = new ArrayList<Barrio>();
		Query query = getEntityManager().createNamedQuery("Barrio.findAllActivo");
		resultado = (List<Barrio>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Barrio> getListaBarriosPatron(String patron){
		List<Barrio> resultado = new ArrayList<Barrio>();
		Query query = getEntityManager().createNamedQuery("Barrio.findBarrios");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Barrio>) query.getResultList();
		return resultado;
	}
}
