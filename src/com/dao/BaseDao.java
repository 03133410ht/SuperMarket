package com.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.util.DButil;

/**
 * ���ݿ�����ӹ�����
 * @author Goddard
 *
 */
public class BaseDao {
	
	private Logger log = Logger.getLogger(BaseDao.class);

	
	/**
	 * �������ݿ⹤�� 
	 * @param sql 		����sql���
	 * @param params	����
	 * @return			�Ƿ���³ɹ�
	 */
	protected boolean update(String sql,Object ... params) {
		boolean flag = false;
		Connection conn = DButil.getConnection();
		PreparedStatement pstmt = DButil.getPrePareStatement(conn, sql);
		try {
			DButil.bindParam(pstmt, params);
			if(pstmt.executeUpdate() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, null, pstmt);
		}
		return flag;
		
	}
	
	
	
	/**
	 * ��ѯ���ݿ⹤��
	 * @param sql  sql���
	 * @param cls	class�ļ�
	 * @param params	����
	 * @return
	 */
	protected <T> List<T> query(String sql,Class<T> cls, Object ... params){
		List<T> list = new ArrayList<T>();
		Connection conn = DButil.getConnection();
		PreparedStatement pstmt = DButil.getPrePareStatement(conn, sql);
		ResultSet rs = null;
		try {
			DButil.bindParam(pstmt, params);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				T bean = cls.newInstance();
				for(int i = 1; i<=rsmd.getColumnCount();i++) {
					BeanUtils.setProperty(bean, rsmd.getColumnLabel(i).toLowerCase(), rs.getObject(i));
				}
				list.add(bean);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, pstmt);
		}
		return list;
	}
	
}
