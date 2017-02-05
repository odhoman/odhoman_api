package com.odhoman.api.utilities.config;

/**
 * 
 * Constantes de configuracion
 * 
 * @author Fabian Benitez (fb70883)
 * @date 23/11/2011
 * 
 */

public class ConfigConstants {

	/**
	 * Path del log del common
	 */
	public static final String LOG4J_CONFIG_PATH = "log4j.config.path";

	public static final String WEB_RES_IMMEDIATE = "IMMEDIATE";
	
	public static final String APP_DB_CONNECTION_TYPE   = "app.db.connection_type";  /** Modo de conexion a DB */
	
	
	/** Configuracion de base de datos - Modos de conexion */
	
	public static final String CONN_TYPE_SIMPLE   = "simple";  /** Modo de conexion a DB via JDBC estandar - simple */
	
	public static final String CONN_TYPE_DS   = "datasource";  /** Modo de conexion a DB via datasource */
	
	public static final String CONN_TYPE_POOL   = "pool";  /** Modo de conexion a DB via pool */
	
	/** CONEXIONES A BD - MANAGER */
	
	public static final String APP_DB_CONN_IDS   = "app.db.connection.ids";  /** Ids de las configuraciones de DB */
	
	public static final String APP_DB_CONN_CONFIG_PATHS   = "app.db.connection.config.paths";  /** Paths de las configuraciones de DB */
	
	

	public static final String COMMON_MENU_PATH = "common.menu.path";

	public static final String COMMON_HMENU_PATH = "common.hmenu.path";

	public static final String COMMON_APP_LOGIN_SSL = "common.app.login.ssl";

	public static final String CHANGE_PASSWORD_URL = "common.login.change_password_url";
	
	public static final String COMMON_APP_PATH_MODEL = "common.app.path.model";

	public static final String COMMON_APP_TABLES_PATH = "common.app.tables.path";

	public static final String COMMON_PRELOAD = "common.preload";
	
	public static final String CCM_PROFILES_CONFPATH = "ccm.app_profiles_confpath"; //TODO ver este parametro

	public static final String COMMON_EJB_WORKFLOWEJB = "ejb/common/WorkflowEjb"; /** ejb del servicio Workflow */

	public static final String COMMON_EJB_MENUEJB = null;

	public static final String COMMON_EJB_LOGINEJB = null;

	public static final String COMMON_EJB_GEAEJB = "ejb/common/GeaEjb"; /** ejb del servicio Gea */
	
	
	

		
		/** Conexion estandar - simple */
		
	    public static final String COMMON_DB_DRIVER   = "common.db.driver";  /** JDBC driver */
	    
		public static final String COMMON_DB_URL      = "common.db.url";     /** JDBC url database */
		
		public static final String COMMON_DB_USER     = "common.db.user";    /** JDBC user of database */
		
		public static final String COMMON_DB_PASSWORD = "common.db.password";/** JDBC password of user*/
	
		/** Conexion por data source */
		
		public static final String COMMON_DB_DATASOURCE = "app.db.dataSource";
	
	/** Configuracion de MQ */
	
		public static final String COMMON_MQ_PARENT_ID = "common.mq.parentID";
		
		public static final String COMMON_MQ_IDS = "common.mq.IDs";
		
		public static final String COMMON_MQ_IDS_DEFAULT = "common.mq.IDs.default";
	
		public static final String COMMON_MQ_TRANSACTIONS = "common.mq.transactions";
	
		public static final String COMMON_MQ_CONFIG = "common.mq.config";
		
		public static final String COMMON_MQ_MANAGER = "common.queue.manager";
	
		public static final String COMMON_MQ_PUT = "common.queue.put";
		
		public static final String COMMON_MQ_PUT_EXPIRY = "common.queue.put.expiry";
		
		public static final String COMMON_MQ_REPLYTO = "common.queue.replyto";
		
		public static final String COMMON_MQ_REPLYTO_QUEUE_MANAGER_NAME = "common.queue.replyto.queue.manager.name";
	
		public static final String COMMON_MQ_TIMEOUT = "common.queue.timeout";
	
		public static final String COMMON_MQ_GET = "common.queue.get";
	
		public static final String COMMON_MQ_MODE = "common.mq.mode";
	
		public static final String COMMON_MQ_CLIENT_HOSTNAME = "common.mq.client.hostname";
	
		public static final String COMMON_MQ_CLIENT_CHANNEL = "common.mq.client.channel";
	
		public static final String COMMON_MQ_CLIENT_PORT = "common.mq.client.port";

	/** Configuracion de GeA */

	/** ubicacion de archivos xml de GeA*/
	
	public static final String COMMON_GEA_CONF_PATH   = "common.gea.conf.path";
	
	/** Si es true GeA habilita su fucionalidad de auditor�a por tabla. Requiere colmnas adicionales **/
	
	public static final String COMMON_GEA_TABLECONTROL = "common.gea.tablecontrol"; 
	
	public static final String COMMON_GEA_TABLECONTROL_TRUEFLAG = "true";

	/** Configuracion de GeA. Cantidad de elementos en el listado **/
	
	public static final String COMMON_GEA_CANTELEMLIST = "common.gea.cantelemlist";
		
	/** ID de la aplicacion gea stand alone */
	
	public static final String COMMON_GEA_APP_ID = "common.gea.app.id";
	
	/** Path donde se encuentra la definicion del menu que usara Gea */
	
	public static final String COMMON_GEA_MENU_PATH = "common.gea.menu.path";		
	
	/** Url destino luego de salir de gea */
	
	public static final String COMMON_GEA_EXIT_URL = "common.gea.exit.url";
	
	/** Uso del campo de estado entre las tablas de gea */
	
	public static final String COMMON_GEA_STATUS_ACTIVE = "common.gea.status.active";
		
	/** Lista de usuarios validos / autorizados a entrar a GEA */
	
	public static final String COMMON_GEA_USER_WHITE_LIST = "common.gea.user.whitelist";
	
	public static final String COMMON_GEA_USER_DESC_WHITE_LIST = "common.gea.user.desc.whitelist";
	
	/** Gestion del timeout de Gea */
	
	public static final String COMMON_GEA_SESSION_TIMEOUT = "common.gea.session.timeout";
	
	public static final String COMMON_GEA_SESSION_VALIDATION_TIME = "common.gea.session.valtime";

	// Constantes para configuraci�n de mail
	
	public final static String MAIL_HOST = "mail.smtp.host";
	
	public final static String MAIL_HOST_PORT = "mail.smtp.port";
	
	public final static String MAIL_AUTH = "mail.smtp.auth";
	
	public final static String MAIL_USERNAME = "mail.username";
	
	public final static String MAIL_PASSWORD = "mail.password";

	// login handler
	
	public final static String COMMON_LOGIN_HANDLER = "common.login.handler";
	
	// Country specific variables
	
	public final static String DEFAULT_COUNTRY_CODE = "common.default.country.code";
	
	//Reporting
	
	public final static String REPORTING_PATH = "common.reporting.path";
	
	public final static String REPORTING_TEMP = "common.reporting.temp";

	//Reloadable Config
	
	public final static String CONFIG_RELOADABLE = "common.config.reloadable";	//Valores posibles: Y-SI; N-NO
	
	public final static String CONFIG_RELOADING_PERIOD = "common.config.reloading.period";
	
	//Paging
	
	public final static int DEFAULT_PAGE = 1;
	
	public final static int DEFAULT_ITEMS_PER_PAGE = 20;
	
	//Aspects - Audit
	
	public final static String FLAG_AUDIT_LOG = "audit.log.habilitado";
	
	public final static String BUSINESS_EXCEPTIONS = "audit.log.transaction.business_exceptions";
	
	public final static String USUARIO_LOG4J = "log4j.user.id";
	
	// Max items on queue auditrecords
	
	public final static String MAX_ITEMS_QUEUE_AUDITRECORDS = "max.items.quere.auditrecords";
	
	// Vistas a cachear de GeA
	
	public static final String COMMON_GEA_CACHE_VIEW = "common.gea.cache.view"; 

	// Tablas incluidas en el Cache de GeA
	
	public static final String COMMON_GEA_REFERENCES_CACHE = "common.gea.references.cache"; 
}
