package com.util.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;


//@Repository�� �ָ� CommonDAOImpl�� ��ü ������ why? ������̼��� ����Ѵٰ� ����س��� ������
@Repository("dao") 
public class CommonDAOImpl implements CommonDAO{
	
	
/*
	�ؿ��� ���� ������ ���ִ°��� @Autowired => ������̼�
	public CommonDAOImpl(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
*/	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	
	
	@Override
	public void insertData(String id, Object value) throws SQLException {
		
		try {
		
			sqlMapClientTemplate.getSqlMapClient().startTransaction();
			sqlMapClientTemplate.getSqlMapClient().getCurrentConnection().setAutoCommit(false);
			
			sqlMapClientTemplate.insert(id,value);

			sqlMapClientTemplate.getSqlMapClient().getCurrentConnection().commit();
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally{

			//������ ������� ����̵� Ʈ������� ������!
			sqlMapClientTemplate.getSqlMapClient().getCurrentConnection().setAutoCommit(true);
			sqlMapClientTemplate.getSqlMapClient().endTransaction();
		}
		
	}
	

	@Override
	public int updatetData(String id, Object value) throws SQLException {
		
		int result = 0;
		
		try {
			
			result = sqlMapClientTemplate.update(id,value);

			
		} catch (Exception e) {
			
			System.out.println(e.toString());
		} 
		
		return result;
	}

	
	@Override
	public int updatetData(String id, Map<String, Object> map) throws SQLException {
		
		int result = 0;
		
		try {
			
			
			result = sqlMapClientTemplate.update(id,map);

			
		} catch (Exception e) {
			
			System.out.println(e.toString());
		}
		
		return result;
	}

	
	@Override
	public int deleteData(String id) throws SQLException {

		int result = 0;
		
		try {
			
			result = sqlMapClientTemplate.delete(id);
			
		} catch (Exception e) {
			
			System.out.println(e.toString());
		}
		
		return result;
		
	}

	@Override
	public int delteData(String id, Object value) throws SQLException {

		int result = 0;
		
		try {
			
			result = sqlMapClientTemplate.delete(id,value);

			
		} catch (Exception e) {
			
			System.out.println(e.toString());
		}
		
		return result;
		
	}

	@Override
	public int deltetData(String id, Map<String, Object> map) throws SQLException {
		
		int result = 0;
		
		try {
			
			result = sqlMapClientTemplate.delete(id,map);
			
		} catch (Exception e) {
			
			System.out.println(e.toString());
		}
		
		return result;
		
	}

	@Override
	public Object getReadData(String id) {
		
		return sqlMapClientTemplate.queryForObject(id);
		
	}

	@Override
	public Object getReadData(String id, Object value) {

		
		return sqlMapClientTemplate.queryForObject(id,value);
	}

	@Override
	public Object getReadData(String id, Map<String, Object> map) {

		
		return sqlMapClientTemplate.queryForObject(id,map);
	}

	@Override
	public int getIntValue(String id) {
		
		int num=0;
		
		num=((Integer)sqlMapClientTemplate.queryForObject(id)).intValue();
		
		return num;
	}

	@Override
	public int getIntValue(String id, Object value) {
		
		int num=0;
		
		num=((Integer)sqlMapClientTemplate.queryForObject(id,value)).intValue();
		
		return num;
		
	}

	@Override
	public int getIntValue(String id, Map<String, Object> map) {
		
		int num=0;
		
		num=((Integer)sqlMapClientTemplate.queryForObject(id,map)).intValue();
		
		return num;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListData(String id) {
		
		List<Object> lists = (List<Object>)sqlMapClientTemplate.queryForList(id);
		
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListData(String id, Object value) {
		
		List<Object> lists = (List<Object>)sqlMapClientTemplate.queryForList(id,value);
		
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListData(String id, Map<String, Object> map) {
		
		List<Object> lists = (List<Object>)sqlMapClientTemplate.queryForList(id,map);
		
		return lists;
	}

}
