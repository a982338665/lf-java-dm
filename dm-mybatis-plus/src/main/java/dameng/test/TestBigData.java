package dameng.test;
import java.io.*;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import dameng.dao.BigDataMapper;
import dameng.pojo.BigData;
public class TestBigData {
	SqlSession sqlSession = null;
	BigDataMapper bigDateMapper = null;
	public void init() {
		try {
		//1. 生成sqlsession factory biulder 对象
	    MybatisSqlSessionFactoryBuilder sfb = new MybatisSqlSessionFactoryBuilder();
	    //2. 加载配置文件作为一个输入流
	    //这里Resources 使用的包是 ibatis包
	    InputStream resourceAsStream;
			resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
	    //3. 通过会话工厂构造器 对象和  配置文件 流  构建一个会话构造工厂
	    SqlSessionFactory factory = sfb.build(resourceAsStream);
	    //4. 通过sql会话工厂 //true 设置mybatis事务自动提交
	    sqlSession = factory.openSession(true);
	    bigDateMapper = sqlSession.getMapper(BigDataMapper.class);
	    resourceAsStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		TestBigData test = new TestBigData();
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
		bigDateMapper.insert(bigDate);
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
		List<BigData> list = bigDateMapper.selectList(null);
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
