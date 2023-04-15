package dameng.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import dameng.dao.TestBigDataMapper;
import dameng.pojo.TestBigData;
public class Test_TestBigData {
	SqlSession sqlSession = null;
	TestBigDataMapper testBigDateMapper = null;
	public void init() {
		try {
		//1. 生成 sqlsession factory biulder 对象
	    SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
	    //2. 加载配置文件作为一个输入流
	    //这里 Resources 使用的包是 ibatis 包
	    InputStream resourceAsStream;
			resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
	    //3. 通过会话工厂构造器 对象和  配置文件 流  构建一个会话构造工厂
	    SqlSessionFactory factory = sfb.build(resourceAsStream);
	    //4. 通过 sql 会话工厂 //true 设置 mybatis 事务自动提交
	    sqlSession = factory.openSession(true);
	    testBigDateMapper = sqlSession.getMapper(TestBigDataMapper.class);
	    resourceAsStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Test_TestBigData test = new Test_TestBigData();
		test.init();
		test.testInsert();
		test.testSelect();
	}
	//测试插入大字段表
	private void testInsert() {
		try {
		String filePath = "./img/1.png";
		File file = new File(filePath);
		String filePath2 = "./img/1.txt";
		File file2 = new File(filePath2);
		InputStream in;
		in = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes1 = new byte[732160];//缓冲区要够大
		byte[] bytes2 = new byte[732160];//缓冲区要够大
		in.read(bytes1);
		InputStream in2 = new BufferedInputStream(new FileInputStream(file));
		in2.read(bytes2);
		BufferedReader reader = new BufferedReader(new InputStreamReader
				(new FileInputStream(file2),"UTF-8"));
		//char[] c = new char[10240];
		char[] chars = new char[4096];
		reader.read(chars);
		TestBigData bigDate = new TestBigData(null,bytes1,bytes2,new String(chars));
		testBigDateMapper.InsertIntoTestBigDate(bigDate);
		in.close();
		in2.close();
		reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//测试查询大字段表
	private void testSelect() {
		List<TestBigData> list = testBigDateMapper.SelectFromTestBigDate();
		try {
			for(TestBigData big:list) {
				//打印出 id
				System.out.println("id = "+big.getId());
				//将 photo 列信息 输出到指定路径
				FileOutputStream fos = new FileOutputStream("D:/"+big.getId()+"_mybatis_ photo_test.jpg");
				fos.write(big.getPhoto());
				//将 describe 列信息 输出到指定路径
				FileOutputStream fos2 = new FileOutputStream("D:/"+big.getId()+"_mybatis_describe_test.jpg");
				fos2.write(big.getDescribe());
				//将 photo 列信息 输出到控制台
				System.out.println("txt="+big.getTxt());
				fos.close();
				fos2.close();
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
