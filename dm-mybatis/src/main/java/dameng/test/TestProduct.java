package dameng.test;

import dameng.dao.ProductCategoryMapper;
import dameng.pojo.ProductCategory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestProduct {
    SqlSession sqlSession = null;
    ProductCategoryMapper productCategoryMapper = null;

    public void init() {
        try {
            //1. 生成 sqlsession factory biulder 对象
            SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
            //2. 加载配置文件作为一个输入流
            //这里Resources 使用的包是 ibatis 包
            InputStream resourceAsStream;
            resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
            //3. 通过会话工厂构造器 对象和  配置文件流 构建一个会话构造工厂
            SqlSessionFactory factory = sfb.build(resourceAsStream);
            //4. 通过 sql 会话工厂 //true 设置 mybatis 事务自动提交
            sqlSession = factory.openSession(true);

            productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //测试插入信息
    @Test
    public void testInstert() {
        this.init();
//		productCategoryMapper.autoInsert("PRODUCTION.PRODUCT_CATEGORY");
        productCategoryMapper.insertProductCategory(new ProductCategory(5, "少儿 "));
    }

    //测试修改信息
    @Test
    public void testUpdate() {
        this.init();
        ProductCategory productCategory = productCategoryMapper.selectProductCategoryById(4);
        productCategory.setNAME("英语");
        productCategoryMapper.updateProductCategory(productCategory);
    }

    //测试根据 id 查询指定人信息
    @Test
    public void testSelectPersonById() {
        this.init();
        ProductCategory productCategory = productCategoryMapper.selectProductCategoryById(1);
        System.out.println(productCategory);
    }

    //测试全查
    @Test
    public void testSelectAll() {
        this.init();
        List<ProductCategory> list = productCategoryMapper.selectAll();
        for (ProductCategory p : list) {
            System.out.println(p);
        }
    }

    //测试删除
    @Test
    public void testDelete() {
        this.init();
        productCategoryMapper.deleteById(6);
        productCategoryMapper.deleteById(7);
        productCategoryMapper.deleteById(8);
        productCategoryMapper.deleteById(9);
    }

    public static void main(String[] args) {
        TestProduct test = new TestProduct();
        test.init();
        test.testInstert();
        test.testDelete();
        test.testUpdate();
        test.testSelectPersonById();
        test.testSelectAll();
    }
}
