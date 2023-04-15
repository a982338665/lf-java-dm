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
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import dameng.pojo.BigData;

public class TestBigData {
	SqlMapClient sqlMap = null;
	Reader reader = null;
	@Before
	public void before() {
		try {
			//1. 读取SqlMapConfig.xml配置文件
			reader = Resources.getResourceAsReader("SqlMapConfig.xml");
			//2. 通过读取的配置文件构建 sqlMap对象
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//测试插入大字段表
    @Test
    public void testInstert(){
    	try {
    		String filePath = "../img/1.png";
    		File file = new File(filePath);
    		String filePath2 = "../img/1.txt";
    		File file2 = new File(filePath2);
    		InputStream in;
    		in = new BufferedInputStream(new FileInputStream(file));
    		byte[] bytes1 = new byte[102400];
    		byte[] bytes2 = new byte[102400];
    		in.read(bytes1);
    		InputStream in2 = new BufferedInputStream(new FileInputStream(file));
    		in2.read(bytes2);
    		BufferedReader reader = new BufferedReader(new InputStreamReader
    				(new FileInputStream(file2),"UTF-8"));
    		StringBuffer stringBuffer = new StringBuffer("");
    		String str = null;
    		while((str = reader.readLine())!=null) {
    			stringBuffer.append(str);
    			stringBuffer.append("\n");
    		}
    		BigData bigData = new BigData(null,bytes1,bytes2,stringBuffer.toString());
    		//注意此处sqlmap 调用传递的字符串参数与  对应xml文件里sql 语句的ID 相对应
			sqlMap.insert("insertBigData",bigData);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    //测试查询大字段表
    @Test
    public void testSelectAll(){
		try {
			List<BigData> list = sqlMap.queryForList("selectAllBigData");
			for(BigData big:list) {
				//打印出id
				System.out.println("id = "+big.getId());
				//将photo列信息 输出到指定路径
				FileOutputStream fos = new FileOutputStream("D:/"+big.getId()+"_DM8特点.jpg");
				fos.write(big.getPhoto());
				//将describe列信息 输出到指定路径
				FileOutputStream fos2 = new FileOutputStream("D:/"+big.getId()+"_Blob_DM8特点.jpg");
				fos2.write(big.getDescribe());
				//将photo列信息 输出到控制台
				System.out.println("txt="+big.getTxt());
				fos.close();
				fos2.close();
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    @After
    public void after() {
    	try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public static void main(String[] args) {
		TestBigData test = new TestBigData();
		test.before();
		test.testInstert();
        test.testSelectAll();
        test.after();
	}
}
