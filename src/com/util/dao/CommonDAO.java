package com.util.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommonDAO {

	//������ �߰�
	public void insertData(String id,Object value) throws SQLException;
	
	//������ ����
	public int updatetData(String id,Object value) throws SQLException;
	public int updatetData(String id,Map<String,Object> map) throws SQLException;
	
	//������ ����
	public int deleteData(String id) throws SQLException;
	public int delteData(String id,Object value) throws SQLException;
	public int deltetData(String id,Map<String,Object> map) throws SQLException;
	
	//�ش� ���ڵ� ��������(����Ǽ��� �� �����)
	public Object getReadData(String id);
	public Object getReadData(String id,Object value);
	public Object getReadData(String id,Map<String,Object> map);
	

	public int getIntValue(String id);
	public int getIntValue(String id,Object value);
	public int getIntValue(String id,Map<String,Object> map);

	public List<Object> getListData(String id);
	public List<Object> getListData(String id,Object value);
	public List<Object> getListData(String id,Map<String,Object> map);
	
}
