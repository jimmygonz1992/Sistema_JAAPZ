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
<<<<<<< HEAD
	
	@SuppressWarnings("unchecked")
	public List<Ingreso> getBuscaFactura(String numIngreso, int idProveedor){
		List<Ingreso> resultado = new ArrayList<Ingreso>();
		Query query = getEntityManager().createNamedQuery("Ingreso.BuscaFactura");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("numIngreso", numIngreso);
		query.setParameter("idProveedor", idProveedor);
		resultado = (List<Ingreso>) query.getResultList();
		return resultado;
	}
	
=======
>>>>>>> f9ef634870c894870b8fb7a18264f9bd1cbefe4f
}