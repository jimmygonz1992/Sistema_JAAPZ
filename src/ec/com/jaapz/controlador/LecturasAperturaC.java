package ec.com.jaapz.controlador;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import ec.com.jaapz.modelo.Anio;
import ec.com.jaapz.modelo.AnioDAO;
import ec.com.jaapz.modelo.AperturaLectura;
import ec.com.jaapz.modelo.AperturaLecturaDAO;
import ec.com.jaapz.modelo.CuentaCliente;
import ec.com.jaapz.modelo.CuentaClienteDAO;
import ec.com.jaapz.modelo.Me;
import ec.com.jaapz.modelo.MeDAO;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.modelo.PlanillaDAO;
import ec.com.jaapz.modelo.PlanillaDetalle;
import ec.com.jaapz.modelo.ResponsableLecturaDAO;
import ec.com.jaapz.modelo.SegUsuarioDAO;
import ec.com.jaapz.util.Constantes;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class LecturasAperturaC {
	@FXML private ComboBox<Me> cboMes;
    @FXML private TableView<AperturaLectura> tvAperturas;
    @FXML private ComboBox<Anio> cboAnio;
    @FXML private Button btnGrabarApertura;

    MeDAO mesDAO = new MeDAO();
	AnioDAO anioDAO = new AnioDAO();
	ControllerHelper helper = new ControllerHelper();
	CuentaClienteDAO cuentaDAO = new CuentaClienteDAO();
	AperturaLecturaDAO aperturaDAO = new AperturaLecturaDAO();
	AperturaLectura aperturaActual = new AperturaLectura();
	PlanillaDAO planillaDAO = new PlanillaDAO();
	SegUsuarioDAO usuarioDAO = new SegUsuarioDAO();
	ResponsableLecturaDAO responsableDAO = new ResponsableLecturaDAO();

	public void initialize() {
		try {
			cargarCombos();
			recuperarDatos();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	void recuperarDatos() {
		try {
			tvAperturas.getItems().clear();
			tvAperturas.getColumns().clear();
			List<AperturaLectura> listaPrecios;
			ObservableList<AperturaLectura> datos = FXCollections.observableArrayList();
			listaPrecios = aperturaDAO.getListaAperturas();
			datos.setAll(listaPrecios);

			//llenar los datos en la tabla
			TableColumn<AperturaLectura, String> idColum = new TableColumn<>("Id Apertura");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(80);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdApertura()));
				}
			});

			TableColumn<AperturaLectura, String> mesColum = new TableColumn<>("Mes");
			mesColum.setMinWidth(10);
			mesColum.setPrefWidth(100);
			mesColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getMe().getDescripcion()));
				}
			});

			TableColumn<AperturaLectura, String> anioColum = new TableColumn<>("A�o");
			anioColum.setMinWidth(10);
			anioColum.setPrefWidth(90);
			anioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getAnio().getDescripcion()));
				}
			});
			TableColumn<AperturaLectura, String> clienteColum = new TableColumn<>("No. Clientes");
			clienteColum.setMinWidth(10);
			clienteColum.setPrefWidth(90);
			clienteColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getPlanillas().size()));
				}
			});
			TableColumn<AperturaLectura, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AperturaLectura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AperturaLectura, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getEstadoApertura());
				}
			});

			tvAperturas.getColumns().addAll(idColum,mesColum,anioColum,clienteColum,estadoColum);
			tvAperturas.setItems(datos);

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	/*
	 * controlador de la vista de Inicio de Ciclo de Facturacion
	 */
	private void cargarCombos() {
		try {
			ObservableList<Anio> listaAnios = FXCollections.observableArrayList();
			List<Anio> datAn = anioDAO.getListaAnios();
			listaAnios.setAll(datAn);
			cboAnio.setItems(listaAnios);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void buscarMeses() {
		try {
			cboMes.getItems().clear();
			ObservableList<Me> listaMeses = FXCollections.observableArrayList();
			List<Me> datMes = mesDAO.getListaMeses();
			
			List<AperturaLectura> listadoMes = aperturaDAO.getAperturaByAnio(cboAnio.getSelectionModel().getSelectedItem().getIdAnio());
			for(int i = 0 ; i < listadoMes.size() ; i ++) {
				for(int j = 0 ; j < datMes.size() ; j ++) {
					if(listadoMes.get(i).getMe().getIdMes() == datMes.get(j).getIdMes())
						datMes.remove(j);
				}
			}
			listaMeses.addAll(datMes);
			cboMes.setItems(listaMeses);
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	//Grabar aperturas de facturacion realizando algunas validaciones
	//faltan validaciones ------------------------------

	public void grabarApertura(ActionEvent event) {
		try {
			if(validarApertura() == true)
				return;
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				
				//para obtener la hora
				java.util.Date utilDate = new java.util.Date(); 
				long lnMilisegundos = utilDate.getTime();
				java.sql.Time sqlTime = new java.sql.Time(lnMilisegundos);
				
				//declarar el objeto a grabar
				AperturaLectura aperturaGrabar = new AperturaLectura();
				aperturaGrabar.setEstado("A");
				aperturaGrabar.setEstadoApertura(Constantes.EST_APERTURA_PROCESO);
				Date date = new Date();
				Timestamp fecha = new Timestamp(date.getTime());
				aperturaGrabar.setFecha(fecha);
				aperturaGrabar.setUsuarioCrea(Context.getInstance().getIdUsuario());
				aperturaGrabar.setAnio(cboAnio.getSelectionModel().getSelectedItem());
				aperturaGrabar.setIdApertura(null);
				aperturaGrabar.setMe(cboMes.getSelectionModel().getSelectedItem());

				//aperturar todos los clientes 
				List<CuentaCliente> listaCuentasActivas = new ArrayList<CuentaCliente>();
				listaCuentasActivas = cuentaDAO.getListaCuentasActivas();
				aperturaDAO.getEntityManager().getTransaction().begin();
				//recorrer las cuentas para asignar las aperturas
				for(CuentaCliente cuentas : listaCuentasActivas) {
					//solo se genera las  planillas a los clientes que tienen medidores
					if(cuentas.getMedidor() != null){
						List<Planilla> listaAdd = new ArrayList<Planilla>();
						Planilla planilla = new Planilla(); // planilla nueva para todos los clientes
						planilla.setIdPlanilla(null);
						planilla.setUsuarioCrea(Context.getInstance().getIdUsuario());
						planilla.setFecha(fecha);
						planilla.setHora(sqlTime);
						planilla.setConvenio(Constantes.CONVENIO_NO);
						//obtener el consumo del mes anterior
						planilla.setConsumo(0);
						planilla.setConsumoMinimo(0);
						List<Planilla> listaPlanillasCuenta = new ArrayList<Planilla>(); //lista que guarda las planillas de la cuenta del cliente
						listaPlanillasCuenta = planillaDAO.getPlanillaCuenta(cuentas.getIdCuenta());
						if(listaPlanillasCuenta.size() != 0) {
							planilla.setLecturaAnterior(listaPlanillasCuenta.get(0).getLecturaActual());//la lectura anterior y la lectura actual de la planilla
							planilla.setLecturaActual(listaPlanillasCuenta.get(0).getLecturaActual());//son iguales de la lectura actual de la planilla anterior
						}else {
							planilla.setLecturaAnterior(0);//Caso contrario los dos son cero
							planilla.setLecturaActual(0);
						}

						planilla.setEstado(Constantes.ESTADO_ACTIVO);
						//enlace entre planilla y apertura
						planilla.setAperturaLectura(aperturaGrabar);
						listaAdd.add(planilla);
						aperturaGrabar.setPlanillas(listaAdd);
						//enlace entre cliente y planilla
						planilla.setCuentaCliente(cuentas);
						cuentas.setPlanillas(listaAdd);

						//enlace entre detalle de planilla y planilla
						PlanillaDetalle detallePlanilla = new PlanillaDetalle();
						detallePlanilla.setIdPlanillaDet(null);
						detallePlanilla.setDescripcion("Por consumo del mes de: " + cboMes.getSelectionModel().getSelectedItem().getDescripcion() + " del a�o: " + cboAnio.getSelectionModel().getSelectedItem().getDescripcion());
						detallePlanilla.setEstado("A");
						detallePlanilla.setCantidad(0);
						detallePlanilla.setPlanilla(planilla);
						List<PlanillaDetalle> det = new ArrayList<PlanillaDetalle>();
						det.add(detallePlanilla);
						planilla.setPlanillaDetalles(det);

						aperturaDAO.getEntityManager().persist(aperturaGrabar);
					}
				}
				aperturaDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				recuperarDatos();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			aperturaDAO.getEntityManager().getTransaction().rollback();
		}
	}
	private boolean validarApertura() {
		try {
			boolean bandera = false;
			if(cboAnio.getSelectionModel().getSelectedIndex() == -1) {
				helper.mostrarAlertaAdvertencia("Debe Seleccionar el a�o a aperturar", Context.getInstance().getStage());
				bandera = true;
				return bandera;
			}
			if(cboMes.getSelectionModel().getSelectedIndex() == -1) {
				helper.mostrarAlertaAdvertencia("Debe Seleccionar el mes a aperturar", Context.getInstance().getStage());
				bandera = true;
				return bandera;
			}
			List<AperturaLectura> listaApertura = tvAperturas.getItems();
			for(AperturaLectura apertura : listaApertura) {
				if(apertura.getAnio().getIdAnio() == cboAnio.getSelectionModel().getSelectedItem().getIdAnio() 
						&& apertura.getMe().getIdMes() == cboMes.getSelectionModel().getSelectedItem().getIdMes()) {
					bandera = true;
					helper.mostrarAlertaAdvertencia("Apertura ya realizada", Context.getInstance().getStage());
					return bandera;
				}

			}
			for(AperturaLectura apertura : listaApertura) {
				if(apertura.getEstadoApertura().equals(Constantes.EST_APERTURA_PROCESO)) {
					bandera = true;
					helper.mostrarAlertaAdvertencia("Existe una apertura en proceso \nCierre la apertura antes de emitir una nueva", Context.getInstance().getStage());
					return bandera;
				}

			}
			return bandera;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
}
