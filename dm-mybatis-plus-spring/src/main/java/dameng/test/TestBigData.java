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

import dameng.dao.BigDataMapper;
import dameng.pojo.BigData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-dao.xml"})
public class TestBigData {
	@Autowired
	BigDataMapper bigDataMapper= null;

	//测试插入大字段表
	@Test
	public void testInsert() {
		try {
		String filePath = "./img/1.png";
		File file = new File(filePath);
		String filePath2 = "./img/1.txt";
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
		BigData bigDate = new BigData(null,bytes1,bytes2,stringBuffer.toString());

		bigDataMapper.insert(bigDate);
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
	@Test
	public void testSelect() {
		List<BigData> list = bigDataMapper.selectList(null);
		try {
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
		}
	}
}
