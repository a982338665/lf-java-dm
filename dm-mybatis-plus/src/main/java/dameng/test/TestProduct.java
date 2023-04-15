package dameng.test;
import java.io.InputStream;
import java.util.List;

import dameng.pojo.ProductCategory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import dameng.dao.ProductCategoryMapper;

public class TestProduct {
	SqlSession sqlSession = null;
	ProductCategoryMapper productCategoryMapper = null;
	public void init() {
		try {
			//1. 生成sqlsession factory biulder 对象
            MybatisSqlSessionFactoryBuilder builder = new MybatisSqlSessionFactoryBuilder();
            //2. 加载配置文件作为一个输入流
            InputStream resourceAsStream =
                    Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory factory = builder.build(resourceAsStream);
            //这里Resources 使用的包是 ibatis包
            //3. 通过会话工厂构造器 对象和  配置文件 流  构建一个会话构造工厂
            //4. 通过sql会话工厂 //true 设置mybatis事务自动提交
            sqlSession = factory.openSession(true);
            productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
		}catch (Exception e){
            e.printStackTrace();
        }
	}
	//测试插入信息
    @Test
    public void testInstert(){
    	productCategoryMapper.insert((new ProductCategory(null, "语文")));
    	productCategoryMapper.insert((new ProductCategory(28L, "语文")));
    }
    //测试修改信息
    @Test
    public void testUpdate(Integer id){
    	ProductCategory productCategory = productCategoryMapper.selectById(id);
    	productCategory.setName("英语");
    	productCategoryMapper.updateById(productCategory);
    }
    //测试根据id查询指定人信息
    @Test
    public void testSelectPersonById(){
    	ProductCategory productCategory = productCategoryMapper.selectById(1);
        System.out.println(productCategory);
    }
    //测试全查
    @Test
    public void testSelectAll(){
        List<ProductCategory> selectList = productCategoryMapper.selectList(null);
        for(ProductCategory p: selectList){
            System.out.println(p);
        }
    }
    //测试删除
    @Test
    public  void testDelete(){
    	productCategoryMapper.deleteById(5);
    }
    //测试增删改查
	public static void main(String[] args) {
		TestProduct test = new TestProduct();
		test.init();
		test.testInstert();
        test.testSelectAll();
        System.err.println("=================");
        test.testSelectPersonById();
        test.testUpdate(1);
        test.testDelete();
	}
}
