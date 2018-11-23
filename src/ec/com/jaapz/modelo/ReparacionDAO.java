package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class ReparacionDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public Integer getIdReparacion(){
		Integer i;
		List<Reparacion> resultado = new ArrayList<Reparacion>();
		Query query = getEntityManager().createNamedQuery("Reparacion.buscarIDRepar");
		resultado = (List<Reparacion>) query.getResultList();
		if(resultado.size() == 0)
			i = 0;
		else
			i = resultado.get(0).getIdReparacion();
		return i;
	}
}