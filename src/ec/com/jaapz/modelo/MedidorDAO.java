package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class MedidorDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Medidor> getListaMedidores(){
		List<Medidor> resultado = new ArrayList<Medidor>();
		Query query = getEntityManager().createNamedQuery("Medidor.medidoresDisponibles");
		resultado = (List<Medidor>) query.getResultList();
		return resultado;
	}
}
