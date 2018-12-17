package ec.com.jaapz.controlador;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import ec.com.jaapz.modelo.AperturaLectura;
import ec.com.jaapz.modelo.AperturaLecturaDAO;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.modelo.PlanillaDetalle;
import ec.com.jaapz.util.Constantes;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class LecturasCierreC {
	@FXML private TextField txtTotalClientes;
	@FXML private Button btnCerrarCiclo;
	@FXML private TextField txtMes;
	@FXML private TextField txtAnio;
	@FXML private TextField txtClientesFaltantes;
	@FXML private TextField txtFecha;
	@FXML private TableView<AperturaLectura> tvApRealizadas;
	@FXML private TextField txtClientesRegistrados;
	@FXML private Button btnResultados;

	ControllerHelper helper = new ControllerHelper();
	AperturaLecturaDAO aperturaDAO = new AperturaLecturaDAO();
	AperturaLectura aperturaActual = new AperturaLectura();
	
	public void initialize() {
		try {
			Context.getInstance().setBarrio(null);
			obtenerCicloActual();
			cargarDatosAperturasRealizadas();
			tvApRealizadas.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					cargarAperturaSeleccionada(tvApRealizadas.getSelectionModel().getSelectedItem());
				}
			});


		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}


	private void obtenerCicloActual() {
		try {
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
			int numeroTomados = 0;
			int numApertura = 0;
			AperturaLectura apertura = new AperturaLectura();
			numApertura = aperturaDAO.getListaAperturasEnProceso().size(); 
			if(numApertura > 0) {
				apertura = aperturaDAO.getListaAperturasEnProceso().get(0);
				aperturaActual = apertura;
				txtAnio.setText(String.valueOf(apertura.getAnio().getDescripcion()));
				txtMes.setText(String.valueOf(apertura.getMe().getDescripcion()));
				txtFecha.setText(formateador.format(apertura.getFecha()));

				txtTotalClientes.setText(String.valueOf(apertura.getPlanillas().size()));
				for(Planilla planilla : apertura.getPlanillas()) {
					if(planilla.getConsumo() > 0)
						numeroTomados = numeroTomados + 1;
				}
				txtClientesRegistrados.setText(String.valueOf(numeroTomados));
				txtClientesFaltantes.setText(String.valueOf(apertura.getPlanillas().size() - numeroTomados));	
			}else {
				aperturaActual = null;
				txtAnio.setText("");
				txtMes.setText("");
				txtFecha.setText("");
				txtTotalClientes.setText("");
				txtClientesRegistrados.setText("");
				txtClientesFaltantes.setText("");
			}

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	private void cargarDatosAperturasRealizadas() {
		try {
			tvApRealizadas.getItems().clear();
			tvApRealizadas.getColumns().clear();
			List<AperturaLectura> listaPrecios;
			ObservableList<AperturaLectura> datos = FXCollections.observableArrayList();
			listaPrecios = aperturaDAO.getListaAperturas();
			datos.setAll(listaPrecios);

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

			tvApRealizadas.getColumns().addAll(anioColum,mesColum,clienteColum,estadoColum);
			tvApRealizadas.setItems(datos);

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void cargarAperturaSeleccionada(AperturaLectura apertura) {
		try {
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
			aperturaActual = apertura;
			int numeroTomados = 0;
			txtAnio.setText(String.valueOf(apertura.getAnio().getDescripcion()));
			txtMes.setText(String.valueOf(apertura.getMe().getDescripcion()));
			txtFecha.setText(formateador.format(apertura.getFecha()));

			txtTotalClientes.setText(String.valueOf(apertura.getPlanillas().size()));
			for(Planilla planilla : apertura.getPlanillas()) {
				if(planilla.getConsumo() > 0)
					numeroTomados = numeroTomados + 1;
			}
			txtClientesRegistrados.setText(String.valueOf(numeroTomados));
			txtClientesFaltantes.setText(String.valueOf(apertura.getPlanillas().size() - numeroTomados));

			if(apertura.getEstadoApertura().equals(Constantes.EST_APERTURA_PROCESO))
				btnCerrarCiclo.setDisable(false);
			else
				btnCerrarCiclo.setDisable(true);    		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public void cerrarCiclo(ActionEvent event) {
		try {
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procedera a cerrar el proceso de facturaci�n, desea continuar??",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				if (validarCierreCiclo() == false) {
					Optional<ButtonType> resultado = helper.mostrarAlertaConfirmacion("Existen inconvenientes en el proceso de lecturas, desea continuar??",Context.getInstance().getStage());
					if(resultado.get() == ButtonType.CANCEL){
						return;
					}
				}
				aperturaActual.setEstadoApertura(Constantes.EST_INSPECCION_REALIZADO);
				List<PlanillaDetalle> planillaDetalle;
				double total = 0.0;
				//tambien se necesita hacer los calculos para el total de la deuda
				for(int i = 0 ; i < aperturaActual.getPlanillas().size() ; i ++) {
					total = 0.0;
					planillaDetalle = aperturaActual.getPlanillas().get(i).getPlanillaDetalles(); 
					for(PlanillaDetalle det : planillaDetalle) {
						total = total + det.getSubtotal();
					}
					aperturaActual.getPlanillas().get(i).setTotalPagar(total);

					long iPart = (long) total;
					double fPart = total - iPart;
					String numLetras = "";
					numLetras = "(" + helper.cantidadConLetra(String.valueOf(iPart)).toUpperCase() + " D�LARES " + (long) (fPart * 100) + "/100)"; 
					System.out.println(numLetras);
					aperturaActual.getPlanillas().get(i).setTotalLetras(numLetras);
					
					aperturaActual.getPlanillas().get(i).setCancelado(Constantes.EST_FAC_PENDIENTE);
				}

				aperturaDAO.getEntityManager().getTransaction().begin();
				aperturaDAO.getEntityManager().merge(aperturaActual);
				aperturaDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Cierre ejecutado con exito!!!!", Context.getInstance().getStage());
				aperturaActual = null;
				obtenerCicloActual();
				cargarDatosAperturasRealizadas();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			aperturaDAO.getEntityManager().getTransaction().rollback();
		}
	}


	private boolean validarCierreCiclo() {
		try {
			boolean bandera = false;
			//boolean banValidaciones = false;
			for (Planilla planilla : aperturaActual.getPlanillas()) {
				if(planilla.getConsumo() == 0) {
					return false;
				}
			}
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	public void visualizarResultados() {
		try {
			Context.getInstance().setIdApertura(aperturaActual.getIdApertura());
			helper.abrirPantallaModal("/lecturas/LecturasResultados.fxml","Resultados de la Apertura", Context.getInstance().getStage());

		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
