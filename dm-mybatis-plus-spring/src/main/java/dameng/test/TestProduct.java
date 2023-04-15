package dameng.test;
import java.util.List;

import dameng.dao.ProductCategoryMapper;
import dameng.pojo.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-dao.xml"})
public class TestProduct {
	@Autowired
    ProductCategoryMapper productCategoryMapper = null;
	//测试插入信息
    @Test
    public void testInstert(){
    	productCategoryMapper.insert((new ProductCategory(null, "语文8")));
    }
    //测试修改信息
    @Test
    public void testUpdate(){
    	ProductCategory productCategory = productCategoryMapper.selectById(4);
    	productCategory.setName("英语10");
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
}
