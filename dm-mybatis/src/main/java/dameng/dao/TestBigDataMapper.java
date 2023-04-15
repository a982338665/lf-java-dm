package dameng.dao;

import java.util.List;

import dameng.pojo.TestBigData;

public interface TestBigDataMapper {
	//插入大字段
	public void InsertIntoTestBigDate(TestBigData bigdate);
	//查询大字段
	public List<TestBigData> SelectFromTestBigDate();
}