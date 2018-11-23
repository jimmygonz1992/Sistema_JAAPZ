package ec.com.jaapz.util;

public abstract class Constantes {
	public static int ID_USU_ADMINISTRADOR = 1;
	public static int ID_USU_INSPECCION = 2;
	public static int ID_USU_LECTURA = 3;
	
	public static String EST_INSPECCION_REALIZADO = "REALIZADO";
	public static String EST_INSPECCION_PENDIENTE = "PENDIENTE";
	public static String EST_FACTIBLE = "FACTIBLE";
	public static String EST_NO_FACTIBLE = "NO_FACTIBLE";
	public static String EST_APERTURA_PROCESO = "EN PROCESO";
	public static String EST_APERTURA_REALIZADO = "REALIZADO";
	public static String CONVENIO_NO = "NO";
	
	public static String CAT_VIVIENDA = "VIVIENDA";
	public static String CAT_COMERCIAL = "COMERCIAL";
	public static String CAT_ESTABLECIMIENTO = "ESTABLECIMIENTO PÚBLICO";
	
	public static String ESTADO_ACTIVO = "A";
	public static String ESTADO_INACTIVO = "I";
	
	public static String EST_FAC_CANCELADO = "CANCELADO";
	public static String EST_FAC_PENDIENTE = "PENDIENTE";
	
	
	
	//acciones para la sincronizacion
	public static Integer ACCION_MODIFICAR = 1;
	public static Integer ACCION_INSERTAR = 2;
	public static Integer ACCION_ELIMINAR = 3; //solo para casos especiales
}
