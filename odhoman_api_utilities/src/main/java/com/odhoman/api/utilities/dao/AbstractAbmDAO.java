package com.odhoman.api.utilities.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.config.AppConfig;
import com.odhoman.api.utilities.db.DatabaseConnection;
import com.odhoman.api.utilities.dependencyinjector.Initializable;
import com.odhoman.api.utilities.paging.EnumOrderInfo;
import com.odhoman.api.utilities.paging.OrderInfo;
import com.odhoman.api.utilities.paging.PageInfo;
import com.odhoman.api.utilities.transac.ApplicationErrorException;

public abstract class AbstractAbmDAO<T,F> implements Initializable {
	
	protected AbstractConfig config = null;
	protected Logger logger = null;
	
	public AbstractAbmDAO() {
		this(AppConfig.getInstance());
	}
	
	public AbstractAbmDAO(AbstractConfig config) {
		setConfiguration(config);
	}
	
	public void setConfiguration(AbstractConfig config) {
		this.config = config;
		logger = this.config.getLogger();
	}
	
	@Override
	public void init(Object... params) {
		setConfiguration((AbstractConfig) params[0]);
	}

	protected abstract DatabaseConnection getDatabaseConnection();
	
	/** Cierra los recursos abiertos */
	
	private void closeResources(DatabaseConnection db, PreparedStatement ps, ResultSet rs, String function) {
	
		try {
			if(rs != null) rs.close();
		} catch (Exception e) {
			logger.error(getClassName() + " - closeResources: Exception in " + function, e);
		}
		try {
			if(ps != null) ps.close();
		} catch (Exception e) {
			logger.error(getClassName() + " - closeResources: Exception in " + function, e);
		}
		try {
			if(db != null) db.close();
		} catch (Exception e) {
			logger.error(getClassName() + " - closeResources: Exception in " + function, e);
		}		
	}
	
	private String getClassName() {
		return getClass().getSimpleName();
	}
	
	protected boolean isNotNull(Object o) {
		return o != null;
	}
	
	protected boolean isNotEmpty(String s) {
		return isNotNull(s) && !"".equals(s);
	}
	
	/** Retorna la tabla sobre la cual operara este DAO */
	
	protected abstract String getMainTableName();
	
	/** Retorna la lista de los campos a utilizar en el insert */
	
	protected abstract List<String> getInsertFields(T item);
	
	/** 
	 * 	Carga el PreparedStatement con los parametros necesarios para realizar el query. 
	 *  
	 *  Se le pasa el objeto DatabaseConnection por si se necesita ir a buscar info a otro lado en la base. 
	 */
	
	protected abstract void fillInsertParameters(T item, PreparedStatement ps, DatabaseConnection db) throws Exception;
	
	/** Retorna la lista de los campos a utilizar en el update */
	
	protected abstract List<String> getUpdateFields(T item);
	
	/** Devuelve las condiciones del update a realizar */
	
	protected abstract String getUpdateConditions(F filter); //TODO validar la exp regular
	
	/** 
	 * 	Carga el PreparedStatement con los parametros necesarios para realizar el query. 
	 *  
	 *  Se le pasa el objeto DatabaseConnection por si se necesita ir a buscar info a otro lado en la base. 
	 */
	
	protected abstract void fillUpdateParameters(F filter, T item, PreparedStatement ps, DatabaseConnection db) throws Exception;
	
	/** Retorna la lista de los campos a utilizar en el select */
	
	protected abstract List<String> getSelectFields();
	
	/** Retorna la lista de las tablas a utilizar en el select */
	
	protected abstract List<String> getSelectTables();
	
	/** Devuelve las condiciones del select a realizar */
	
	protected abstract String getSelectConditions(F filter); //TODO validar la exp regular
	
	/** 
	 * 	Carga el PreparedStatement con los parametros necesarios para realizar el query. 
	 *  
	 *  Se le pasa el objeto DatabaseConnection por si se necesita ir a buscar info a otro lado en la base. 
	 */
	
	protected abstract void fillSelectParameters(F filter, PreparedStatement ps, DatabaseConnection db) throws Exception;
		
	/** Obtiene las columnas de resultado, crea, carga y devuelve el objeto T */
	
	protected abstract T loadItem(ResultSet rs) throws Exception;
	
	/** Devuelve las condiciones del delete a realizar */
	
	protected abstract String getDeleteConditions(F filter); //TODO validar la exp regular
	
	/** 
	 * 	Carga el PreparedStatement con los parametros necesarios para realizar el query. 
	 *  
	 *  Se le pasa el objeto DatabaseConnection por si se necesita ir a buscar info a otro lado en la base. 
	 */
	
	protected abstract void fillDeleteParameters(F filter, PreparedStatement ps, DatabaseConnection db) throws Exception;
	
	/** Agrega un item */
	
	public int addItem(T item) throws DAOException {
		
		logger.debug(getClassName() + " - addItem: Ejecutando...");
		
		DatabaseConnection db = null;			
		PreparedStatement ps = null;
				
		try {
			db = getDatabaseConnection();
			db.connect();
			
			ps = buildInsertStatement(db, item);
			
			fillInsertParameters(item, ps, db);	//Se completan los parametros del insert
			
			int resultado = ps.executeUpdate();
			
			logger.debug(getClassName() + " - addItem: Query ejecutada");
		
			if(resultado <= 0) {
				throw new DAOException("No se pudo insertar el item " + item);
			}
			
			logger.debug(getClassName() + " - addItem: Se insertaron " + resultado + " items");
			
			return resultado;

		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - addItem: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, ps, null, "addItem");
		}
	}
	
	/** Prepara el statemente para el insert del item recibido */
	
	private PreparedStatement buildInsertStatement(DatabaseConnection db, T item) throws Exception {
		
		logger.debug(getClassName() + " - buildInsertStatement: item=" + item);
		
		StringBuffer query = new StringBuffer("INSERT INTO ");
		query.append(getMainTableName());	//Tabla especifica donde se realizara el query
		
		query.append("(");
					
		int i = 0;
		StringBuffer values = new StringBuffer("(");
		for(String field : getInsertFields(item)) {	//Obtencion de los campos del insert
			i++;
			if(i > 1) {
				query.append(",");
				values.append(",");
			}
			query.append(field);
			values.append("?");
		}
		
		query.append(")");
		values.append(")");
		
		query.append(" VALUES");
		query.append(values.toString());
		
		String sql = query.toString();
		
		logger.info(getClassName() + " - buildInsertStatement: QUERY: " + sql);
			
		PreparedStatement ps = db.prepare(sql);		
		
		return ps;
	}
	
	/** Agrega una lista de items */
	
	public int addItems(List<T> items) throws DAOException {
		
		logger.debug(getClassName() + " - addItems: Ejecutando...");
		
		if(items == null || items.isEmpty()) {
			logger.debug(getClassName() + " - addItems: Finalizando. No hay items que agregar");
			return 0;
		}
		
		DatabaseConnection db = null;			
		PreparedStatement ps = null;
				
		try {
			db = getDatabaseConnection();
			db.connect();
			
			logger.debug(getClassName() + " - addItems: items=" + items);
			
			for(T item : items) {
			
				//Solo con el primer item arma el prepared statement necesario. Luego simplemente hace el fill correspondiente al item.
				
				if(ps == null) {
					ps = buildInsertStatement(db, item);
				}
				
				fillInsertParameters(item, ps, db);	//Se completan los parametros del insert
				
				ps.addBatch();
			}
				
			int[] resultado = ps.executeBatch();
			
			logger.debug(getClassName() + " - addItems: Batch ejecutado");
			
			int resultadoTotal = getProcessedResultCount(resultado);
			
			logger.info(getClassName() + " - addItems: Se insertaron " + resultadoTotal + " items");
			
			ps.clearBatch();
			
			return resultadoTotal;

		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - addItems: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, ps, null, "addItems");
		}
	}
	
	
	/** Modifica un item */
	
	public int changeItem(F filter, T item) throws DAOException {
		
		logger.debug(getClassName() + " - changeItem: Ejecutando...");
		
		DatabaseConnection db = null;
		PreparedStatement ps = null;
		
		try {
			
			db = getDatabaseConnection();
			db.connect();
			
			ps = buildUpdateStatement(db, item, filter, true);
			
			fillUpdateParameters(filter, item, ps, db);	//Se completan los parametros del update

			int resultado = ps.executeUpdate();
			
			logger.debug(getClassName() + " - changeItem: Query ejecutada");
		
			if(resultado <= 0)
				throw new DAOException("No se pudo actualizar el item " + item);
			
			logger.debug(getClassName() + " - changeItem: Se modificaron " + resultado + " items");
			
			return resultado;

		} catch (ItemNotFoundException e) {
			throw e;
		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {			
			logger.error(getClassName() + " - changeItem: Exception ", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, ps, null, "changeItem");
		}
	}
	
	/** Prepara el statement para el update del item recibido */
	
	private PreparedStatement buildUpdateStatement(DatabaseConnection db, T item, F filter, boolean uniqueControl) throws Exception {
		
		logger.debug(getClassName() + " - changeItem: filter=" + filter + " - item=" + item);
		
		int count = countItems(filter, db);
		
		//Se consulta si se debe hacer control del unique
		if(uniqueControl && count != 1){
			
			// Si no se encuentra ninguno se lo lanza como exception
			if(count==0)
				throw new ItemNotFoundException("No se encontro ningun item para actualizar");
			
			// Si se encuentra mas de un item para actualizar se lanza esta exception
			throw new ApplicationErrorException("Hay mas de un item para actualizar: " + filter);
		}
		
		StringBuffer query = new StringBuffer("UPDATE ");
		query.append(getMainTableName());	//Tabla especifica donde se realizara el query
		
		query.append(" SET ");
					
		int i = 0;
		for(String field : getUpdateFields(item)) {	//Obtencion de los campos del update
			i++;
			if(i > 1) {
				query.append(", ");
			}
			query.append(field);
			query.append(" = ?");
		}
		
		query.append(" WHERE ");
		query.append(getUpdateConditions(filter));
					
		String sql = query.toString();
		
		logger.info(getClassName() + " - changeItem: QUERY: " + sql);
					
		PreparedStatement ps = db.prepare(sql);		
		
		return ps;
	}
	
	/** Modifica una lista de items a partir de una lista de filters. Supone que todos los filters se realizan por el mismo campo o conjunto de campos. */
	
	public int changeItems(List<F> filters, T item) throws DAOException {
		
		logger.debug(getClassName() + " - changeItems: Ejecutando...");
		
		if(filters == null || filters.isEmpty()) {
			logger.debug(getClassName() + " - changeItems: Finalizando. No hay items que actualizar");
			return 0;
		}
		
		DatabaseConnection db = null;			
		PreparedStatement ps = null;
				
		try {
			db = getDatabaseConnection();
			db.connect();
			
			logger.debug(getClassName() + " - changeItems: filters=" + filters);
			
			for(F filter : filters) {
			
				//Solo con el primer item arma el prepared statement necesario. Luego simplemente hace el fill correspondiente al item.
				//Tener en cuenta que se supone que todos los filters vendran por el mismo o mismos campos...
				
				if(ps == null) {
					ps = buildUpdateStatement(db, item, filter, false);	//No se hace la validacion de cantidad de items a actualizar.
				}
				
				fillUpdateParameters(filter, item, ps, db);	//Se completan los parametros del update
				
				ps.addBatch();
			}
				
			int[] resultado = ps.executeBatch();
			
			logger.debug(getClassName() + " - changeItems: Batch ejecutado");
			
			int resultadoTotal = getProcessedResultCount(resultado);
			
			logger.info(getClassName() + " - changeItems: Se actualizaron " + resultadoTotal + " items");
			
			ps.clearBatch();
			
			return resultadoTotal;

		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - changeItems: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, ps, null, "changeItems");
		}
	}
	
	/** Modifica una lista de items a partir de una lista de filters. Supone que todos los filters se realizan por el mismo campo o conjunto de campos. */
	
	public int changeDifferentItems(List<F> filters, List<T> items) throws DAOException {
		
		logger.debug(getClassName() + " - changeItems: Ejecutando...");
		
		if(filters == null || filters.isEmpty()) {
			logger.debug(getClassName() + " - changeItems: Finalizando. No hay items que actualizar");
			return 0;
		}
		
		DatabaseConnection db = null;			
		PreparedStatement ps = null;
				
		try {
			db = getDatabaseConnection();
			db.connect();
			
			logger.debug(getClassName() + " - changeItems: filters=" + filters);
			for (int i = 0; i < filters.size(); i++) {
			
				//Solo con el primer item arma el prepared statement necesario. Luego simplemente hace el fill correspondiente al item.
				//Tener en cuenta que se supone que todos los filters vendran por el mismo o mismos campos...
				
				if(ps == null) {
					ps = buildUpdateStatement(db, items.get(i), filters.get(i), false);	//No se hace la validacion de cantidad de items a actualizar.
				}
				
				fillUpdateParameters(filters.get(i), items.get(i), ps, db);	//Se completan los parametros del update
				
				ps.addBatch();
			}
				
			int[] resultado = ps.executeBatch();
			
			logger.debug(getClassName() + " - changeItems: Batch ejecutado");
			
			int resultadoTotal = getProcessedResultCount(resultado);
			
			logger.info(getClassName() + " - changeItems: Se actualizaron " + resultadoTotal + " items");
			
			ps.clearBatch();
			
			return resultadoTotal;

		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - changeItems: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, ps, null, "changeItems");
		}
	}	

	protected String getOrdenamiento(OrderInfo orderInfo) {
		
		return orderInfo.getOrderField() + " " + 
				(EnumOrderInfo.ORDER_TYPE_ASC.getDescripcion().equals(orderInfo.getOrderType()) ? "asc" : "desc");
	}
	
	/** Devuelve el item con las condiciones especificadas */
	
	public T getDetail(T itemAsFilter) throws DAOException {
		
		return getItem(adaptItemToFilter(itemAsFilter));
		
	}
	
	/** Convierte un item particular en un filter para poder realizar una busqueda */
	
	protected F adaptItemToFilter(T itemAsFilter) {
		throw new ApplicationErrorException("No hay definida una conversion para el item " + itemAsFilter);
	}
	
	/** Devuelve el item con las condiciones especificadas */
	
	public T getItem(F filter) throws DAOException {
		
		DatabaseConnection db = null;
		
		try {
			db = getDatabaseConnection();
			db.connect();		

			return getItem(filter, db);
			
		} catch (ItemNotFoundException e) {
			throw e;
		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - getItem: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, null, null, "getItem");
		}		
	}
	
	/** Devuelve el item con las condiciones especificadas */
	
	private T getItem(F filter, DatabaseConnection db) throws DAOException {
		
		logger.debug(getClassName() + " - getItem: Ejecutando...");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			logger.debug(getClassName() + " - getItem: filter=" + filter);

			StringBuffer query = new StringBuffer("SELECT ");
			
			int i = 0;
			for(String field : getSelectFields()) {	//Obtencion de los campos del select
				i++;
				if(i > 1) {
					query.append(",");
				}
				query.append(field);
			}
			
			query.append(" FROM ");
			
			int j = 0;
			for(String field : getSelectTables()) {	//Obtencion de las tablas del select
				j++;
				if(j > 1) {
					query.append(",");
				}
				query.append(field);
			}
			
			query.append(" WHERE ");
			
			query.append(getSelectConditions(filter));	//Obtencion de los campos del select
			
			String sql = query.toString();
			
			logger.info(getClassName() + " - getItem: QUERY: " + sql);
			
			ps = db.prepare(sql);
			
			fillSelectParameters(filter, ps, db);	//Se completan los parametros del select

			rs = ps.executeQuery();
			
			logger.debug(getClassName() + " - getItem: Query ejecutada");
			
			T item = null;			
			while(rs.next()) {			//Si hubiere varios resultados, se queda con el ultimo.
				
				item = loadItem(rs);	//Obtiene las columnas y crea el objeto final			
			}
			
			if(item == null)
				throw new ItemNotFoundException("No existe el item especificado: " + filter);
			
			logger.info(getClassName() + " - getItem: Retornando item " + item);
			
			return item;
			
		} catch (ItemNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - getItem: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(null, ps, rs, "getItem");
		}	
	}
	
	/** Devuelve todos los items que cumplan con el filter especificado */
	
	public List<T> getItems(F filter) throws DAOException {
		
		PageInfo pageInfo = new PageInfo(1, Integer.MAX_VALUE);
		
		OrderInfo orderInfo = new OrderInfo("1", EnumOrderInfo.ORDER_TYPE_ASC.getDescripcion());
		
		return getItems(filter, pageInfo, orderInfo);
	}
	
	/** Devuelve todos los items que cumplan con el filter y orderInfo especificados */
	
	public List<T> getItems(F filter, OrderInfo orderInfo) throws DAOException {
		
		PageInfo pageInfo = new PageInfo(1, Integer.MAX_VALUE);
		
		return getItems(filter, pageInfo, orderInfo);
	}
	
	/** Devuelve los items paginados que cumplan con el filter, pageInfo y orderInfo especificados */
	
	public List<T> getItems(F filter, PageInfo pageInfo, OrderInfo orderInfo) throws DAOException {
		
		logger.debug(getClassName() + " - getItems: Ejecutando...");
		
		DatabaseConnection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			db = getDatabaseConnection();
			db.connect();
			
			logger.debug(getClassName() + " - getItems: filter=" + filter + " - pageInfo=" + pageInfo + " - orderInfo=" + orderInfo);
			
			//Se define si se hara paginado o no...
			
			boolean pagingActive = pageInfo != null && pageInfo.getItemsPerPage() != Integer.MAX_VALUE;
			
			StringBuffer query = new StringBuffer(pagingActive ? "SELECT * FROM (SELECT a.*, rownum rnum FROM (" : "");
			
			query.append("SELECT ");
			
			int i = 0;
			for(String field : getSelectFields()) {	//Obtencion de los campos del select
				i++;
				if(i > 1) {
					query.append(",");
				}
				query.append(field);
			}
			
			query.append(" FROM ");
			
			int j = 0;
			for(String field : getSelectTables()) {	//Obtencion de las tablas del select
				j++;
				if(j > 1) {
					query.append(",");
				}
				query.append(field);
			}
			
			query.append(" WHERE ");
			
			query.append(getSelectConditions(filter));	//Obtencion de los campos del select
			
			query.append(" ORDER BY " + getOrdenamiento(orderInfo));
			
			if(pagingActive) {			
				query.append(") a ) WHERE rnum BETWEEN ");
				query.append(((pageInfo.getPage() - 1) * pageInfo.getItemsPerPage()) + 1);
				query.append(" AND ");
				query.append( pageInfo.getPage() * pageInfo.getItemsPerPage() );
			}
			
			String sql = query.toString();
			
			logger.info(getClassName() + " - getItems: QUERY: " + sql);
			
			ps = db.prepare(sql);
			
			fillSelectParameters(filter, ps, db);	//Se completan los parametros del select

			rs = ps.executeQuery();
			
			logger.debug(getClassName() + " - getItems: Query ejecutada");
			
			List<T> items = new ArrayList<T>();
			T item = null;			
			while(rs.next()) {
				
				item = loadItem(rs);	//Obtiene las columnas y crea el objeto final			
				items.add(item);				
			}
			
			logger.info(getClassName() + " - getItems: Retornando " + items.size() + " items");
			
			return items;
			
		} catch (Exception e) {
			logger.error(getClassName() + " - getItems: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, ps, rs, "getItems");
		}		
	}
	
	/** Devuelve la cantidad de items que cumplan con el filter especificado */
	
	public int countItems(F filter) throws DAOException {
		
		DatabaseConnection db = null;
		
		try {
			db = getDatabaseConnection();
			db.connect();

			return countItems(filter, db);	
			
		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - countItems: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, null, null, "countItems");
		}		
	}
	
	/** Devuelve la cantidad de items que cumplan con el filter especificado */
	
	private int countItems(F filter, DatabaseConnection db) throws DAOException {
		
		logger.debug(getClassName() + " - countItems: Ejecutando...");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {

			logger.debug(getClassName() + " - countItems: filter=" + filter);
			
			StringBuffer query = new StringBuffer("SELECT COUNT(*) FROM ");
			
			int i = 0;
			for(String field : getSelectTables()) {	//Obtencion de las tablas del select
				i++;
				if(i > 1) {
					query.append(",");
				}
				query.append(field);
			}
			
			query.append(" WHERE ");
			
			query.append(getSelectConditions(filter));	//Obtencion de los campos del select
			
			String sql = query.toString();
			
			logger.info(getClassName() + " - countItems: QUERY: " + sql);
			
			ps = db.prepare(sql);
			
			fillSelectParameters(filter, ps, db);	//Se completan los parametros del select

			rs = ps.executeQuery();
			
			logger.debug(getClassName() + " - countItems: Query ejecutada");
			
			rs.next();
			
			int count = rs.getInt(1);
			
			logger.info(getClassName() + " - countItems: Hay " + count + " items");
			
			return count;
			
		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - countItems: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(null, ps, rs, "countItems");
		}		
	}
	
	/** Elimina un item */
	
	public int deleteItem(F filter) throws DAOException {
		
		logger.debug(getClassName() + " - deleteItem: Ejecutando...");
		
		DatabaseConnection db = null;
		PreparedStatement ps = null;
		
		try {
			
			db = getDatabaseConnection();
			db.connect();
			
			ps = buildDeleteStatement(db, filter, true);	//Se valida que solo haya uno a borrar... 
			
			fillDeleteParameters(filter, ps, db);	//Se completan los parametros del delete

			int resultado = ps.executeUpdate();
			
			logger.debug(getClassName() + " - deleteItem: Query ejecutada");
		
			if(resultado <= 0)
				throw new DAOException("No se pudo eliminar el item especificado: " + filter);

			logger.debug(getClassName() + " - deleteItem: Se eliminaron " + resultado + " items");
			
			return resultado;
			
		} catch (ItemNotFoundException e) {
			throw e;
		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - deleteItem: Exception ", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, ps, null, "deleteItem");
		}
	}
	
	/** Elimina una lista de items a partir de una lista de filters. Supone que todos los filters se realizan por el mismo campo o conjunto de campos. */
	
	public int deleteItems(List<F> filters) throws DAOException {
		
		logger.debug(getClassName() + " - deleteItems: Ejecutando...");
		
		if(filters == null || filters.isEmpty()) {
			logger.debug(getClassName() + " - deleteItems: Finalizando. No hay items que eliminar");
			return 0;
		}
		
		DatabaseConnection db = null;			
		PreparedStatement ps = null;
				
		try {
			db = getDatabaseConnection();
			db.connect();
			
			logger.debug(getClassName() + " - deleteItems: filters=" + filters);
			
			for(F filter : filters) {
			
				//Solo con el primer item arma el prepared statement necesario. Luego simplemente hace el fill correspondiente al item.
				//Tener en cuenta que se supone que todos los filters vendran por el mismo o mismos campos...
				
				if(ps == null) {
					ps = buildDeleteStatement(db, filter, false);	//No se hace la validacion de cantidad de items a borrar.
				}
				
				fillDeleteParameters(filter, ps, db);	//Se completan los parametros del delete
				
				ps.addBatch();
			}
				
			int[] resultado = ps.executeBatch();
			
			logger.debug(getClassName() + " - deleteItems: Batch ejecutado");
			
			int resultadoTotal = getProcessedResultCount(resultado);
			
			logger.info(getClassName() + " - deleteItems: Se eliminaron " + resultadoTotal + " items");
			
			ps.clearBatch();
			
			return resultadoTotal;

		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			logger.error(getClassName() + " - deleteItems: Exception", e);
			throw new DAOException(e);
		} finally {
			closeResources(db, ps, null, "deleteItems");
		}
	}
	
	/** Retorna la cantidad de items procesados */
	
	private Integer getProcessedResultCount(int [] result) {
		
		if(result == null || result.length == 0)
			return 0;
		
		Integer total = 0;
		for(int i : result) {
			total += (i != Statement.SUCCESS_NO_INFO) ? i : 1; 
		}
		return total;
		
	}
	
	/** Prepara el statement para el delete a realizar */
	
	private PreparedStatement buildDeleteStatement(DatabaseConnection db, F filter, boolean uniqueControl) throws Exception {
		
		logger.debug(getClassName() + " - buildDeleteStatement: filter=" + filter);
		
		int count = countItems(filter, db);
		
		//Se consulta si se debe hacer control del unique
		if(uniqueControl && count != 1){
			
			// Si no se encuentra ninguno se lo lanza como exception
			if(count==0)
				throw new ItemNotFoundException("No se encontro ningun item para eliminar");
			
			// Si se encuentra mas de un item para actualizar se lanza esta exception
			throw new ApplicationErrorException("Hay mas de un item para eliminar: " + filter);
		}
		
		StringBuffer query = new StringBuffer("DELETE ");
		query.append(getMainTableName());	//Tabla especifica donde se realizara el query
		
		query.append(" WHERE ");
		query.append(getDeleteConditions(filter));
					
		String sql = query.toString();
		
		logger.info(getClassName() + " - buildDeleteStatement: QUERY: " + sql);
					
		PreparedStatement ps = db.prepare(sql);
		
		return ps;
	}
	
	
	protected String appendFields(List<String> fields){
		int sequenceNumber = 0;
		StringBuffer conditions = new StringBuffer();
		
		for(String field: fields){
		
			if(sequenceNumber==0){
				conditions.append(" "+ field+" = ? ");
				sequenceNumber++;
				continue;
			}
			
			if(sequenceNumber > 0) {
				conditions.append(" AND ");
			}else{
				sequenceNumber++;	
			}
			
			conditions.append(" "+ field+" = ? ");
			
		}
		
		return conditions.toString();
	}
	
	protected int fillListParameter(int initialSeq, List<Object> lista, PreparedStatement ps)
			throws SQLException {
		for (Object o : lista)
			initialSeq = fillInsertOrUpdateParameter(initialSeq, o, ps);
		
		return initialSeq;
	}

	protected int fillInsertOrUpdateParameter(int seq, Object o, PreparedStatement ps) throws SQLException {

		if (null == o) {
			ps.setNull(seq, java.sql.Types.NULL);
		} else {
			fillParameterByType(seq, o, ps);
		}

		seq++;

		return seq;
	}
	
	protected int fillSelectListParameter(int initialSeq, List<Object> lista, PreparedStatement ps)
			throws SQLException {
		for (Object o : lista)
			initialSeq = fillSelectParameter(initialSeq, o, ps);
		
		return initialSeq;
	}
	
	protected int fillSelectParameter(int seq, Object o, PreparedStatement ps) throws SQLException {

		if (null == o) {
			return seq;
		} else {
			fillParameterByType(seq, o, ps);
		}

		seq++;

		return seq;
	}

	protected void fillParameterByType(int seq, Object o, PreparedStatement ps) throws SQLException {
		if (o instanceof Long) {
			ps.setLong(seq, (Long) o);
		} else if (o instanceof String) {
			String value = (String) o;
			if(isNotEmpty(value)){
				ps.setString(seq, (String) o);
			}else{
				ps.setNull(seq, java.sql.Types.NULL);
			}
		} else if (o instanceof Double) {
			ps.setDouble(seq, (Double) o);
		} else if (o instanceof Date) {
			Date date = (Date) o;
			ps.setDate(seq, new java.sql.Date(date.getTime()));
		}
	}
	
	protected <M extends Object> M getValueOrNull(M value, ResultSet rs) throws SQLException {

		if (rs.wasNull()) {
			return null;
		}

		return value;
	}

}
