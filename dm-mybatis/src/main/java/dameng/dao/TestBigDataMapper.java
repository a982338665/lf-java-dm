package dameng.dao;

import java.util.List;

import dameng.pojo.TestBigData;

public interface TestBigDataMapper {
	//������ֶ�
	public void InsertIntoTestBigDate(TestBigData bigdate);
	//��ѯ���ֶ�
	public List<TestBigData> SelectFromTestBigDate();
}