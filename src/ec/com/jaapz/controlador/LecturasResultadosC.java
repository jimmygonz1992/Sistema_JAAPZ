package ec.com.jaapz.controlador;

import java.util.HashMap;
import java.util.Map;

import ec.com.jaapz.modelo.ClaseDAO;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.PrintReport;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LecturasResultadosC {
	@FXML private AnchorPane ventana;
	@FXML private VBox vboxGroup;
	@FXML private Button btnVisualizarInforme;
	final ToggleGroup group = new ToggleGroup();
	RadioButton rb1 = new RadioButton("Lecturas no Ingresadas");
	RadioButton rb2 = new RadioButton("Lecturas con inconsistencia");
	RadioButton rb3 = new RadioButton("Problemas de lecturas");
	ClaseDAO claseDAO = new ClaseDAO();
	String estilo = "-fx-font-family: 'Yu Mincho Demibold'; -fx-font-size: 14px;-fx-font-weight: bold;";
	public void initialize() {
		try {
			rb1.setToggleGroup(group);
			rb1.setStyle(estilo);
			rb1.setSelected(true);
			rb2.setStyle(estilo);
			rb2.setToggleGroup(group);
			rb3.setStyle(estilo);
			rb3.setToggleGroup(group);
			vboxGroup.getChildren().addAll(rb1,rb2,rb3);
			vboxGroup.setSpacing(25);
			vboxGroup.setPadding(new Insets(20, 10, 10, 20));
			//ventana.getChildren().add(vboxGroup);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public void visualizarInforme(){
		try {
			if(rb1.isSelected()) {
				PrintReport reporte = new PrintReport();
				Map<String,Object> parametros = new HashMap<String,Object>();
				parametros.put("ID_APERTURA", Context.getInstance().getIdApertura());
				reporte.crearReporte("/recursos/informes/clientes_no_registrados.jasper", claseDAO, parametros);
				reporte.showReport("Clientes sin ingreso de lecturas");
			}
			if(rb2.isSelected()) {
				System.out.println("Lecturas con inconsistencias");
			}
			if(rb3.isSelected()) {
				System.out.println("Problemas de lecturas");
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
