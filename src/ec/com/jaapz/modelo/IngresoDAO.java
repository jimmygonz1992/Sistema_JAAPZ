package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class IngresoDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Ingreso> getListaIngresos(){
		List<Ingreso> resultado = new ArrayList<Ingreso>();
		Query query = getEntityManager().createNamedQuery("Ingreso.findAll");
		resultado = (List<Ingreso>) query.getResultList();
		return resultado;
	}
	
	//para recuperar proveedor
	@SuppressWarnings("unchecked")
	public List<Ingreso> getRecuperaIngreso(String numIngreso){
		List<Ingreso> resultado = new ArrayList<Ingreso>();
		Query query = getEntityManager().createNamedQuery("Ingreso.recuperaIngreso");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("numIngreso", numIngreso);
		resultado = (List<Ingreso>) query.getResultList();
		return resultado;
	}
}