package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_perfil database table.
 * 
 */
@Entity
@Table(name="seg_perfil")
@NamedQueries({
	@NamedQuery(name="SegPerfil.findAll", query="SELECT s FROM SegPerfil s WHERE s.estado = 'A'"),
	@NamedQuery(name="SegPerfil.findAllPerfiles", query="SELECT s FROM SegPerfil s WHERE s.estado = 'A'"),
	@NamedQuery(name="SegPerfil.findPatron", query="SELECT s FROM SegPerfil s WHERE s.idPerfil = (:idPerfil)"),
	@NamedQuery(name="SegPerfil.BuscarUltimoPerfil", query="SELECT s FROM SegPerfil s order by s.idPerfil desc")
})
public class SegPerfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_perfil")
	private Integer idPerfil;

	private Integer accion;

	private String descripcion;

	private String estado;

	private String nombre;

	//bi-directional many-to-one association to SegPermiso
	@OneToMany(mappedBy="segPerfil", cascade = CascadeType.ALL)
	private List<SegPermiso> segPermisos;

	//bi-directional many-to-one association to SegUsuario
	@OneToMany(mappedBy="segPerfil", cascade = CascadeType.ALL)
	private List<SegUsuario> segUsuarios;

	public SegPerfil() {
	}

	public Integer getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Integer getAccion() {
		return this.accion;
	}

	public void setAccion(Integer accion) {
		this.accion = accion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<SegPermiso> getSegPermisos() {
		return this.segPermisos;
	}

	public void setSegPermisos(List<SegPermiso> segPermisos) {
		this.segPermisos = segPermisos;
	}

	public SegPermiso addSegPermiso(SegPermiso segPermiso) {
		getSegPermisos().add(segPermiso);
		segPermiso.setSegPerfil(this);

		return segPermiso;
	}

	public SegPermiso removeSegPermiso(SegPermiso segPermiso) {
		getSegPermisos().remove(segPermiso);
		segPermiso.setSegPerfil(null);

		return segPermiso;
	}

	public List<SegUsuario> getSegUsuarios() {
		return this.segUsuarios;
	}

	public void setSegUsuarios(List<SegUsuario> segUsuarios) {
		this.segUsuarios = segUsuarios;
	}

	public SegUsuario addSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().add(segUsuario);
		segUsuario.setSegPerfil(this);

		return segUsuario;
	}

	public SegUsuario removeSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().remove(segUsuario);
		segUsuario.setSegPerfil(null);

		return segUsuario;
	}
	@Override
	public String toString() {
		return this.nombre;
	}
}