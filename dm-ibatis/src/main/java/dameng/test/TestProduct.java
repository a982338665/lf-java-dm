package dameng.test;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import dameng.pojo.ProductCategory;
public class TestProduct {
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
	//测试插入信息
    @Test
    public void testInstert(){
    	try {
    		//注意此处sqlmap 调用传递的字符串参数与  对应xml文件里sql 语句的ID 相对应
			sqlMap.insert("insertProductCategory",new ProductCategory(null, "物理"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    //测试修改信息
    @Test
    public void testUpdate(){
    	ProductCategory productCategory;
		try {
			productCategory = (ProductCategory) sqlMap.queryForObject("selectProductCategoryById",4);
			productCategory.setName("英语5");
			//注意此处sqlmap 调用传递的字符串参数与  对应xml文件里sql 语句的ID 相对应
			sqlMap.update("updateProductCategory", productCategory);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    //测试根据id查询指定人信息
    @Test
    public void testSelectPersonById(){
    	ProductCategory productCategory;
    	try {
    		//注意此处sqlmap 调用传递的字符串参数与  对应xml文件里sql 语句的ID 相对应
			productCategory = (ProductCategory) sqlMap.queryForObject("selectProductCategoryById",4);
			System.out.println(productCategory);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    //测试全查
    @Test
    public void testSelectAll(){
    	List<ProductCategory> list;
		try {
			//注意此处sqlmap 调用传递的字符串参数与  对应xml文件里sql 语句的ID 相对应
			list = sqlMap.queryForList("selectAll");
			for(ProductCategory p: list){
	            System.out.println(p);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    //测试删除
    @Test
    public void testDelete(){
    	try {
    		//注意此处sqlmap 调用传递的字符串参数与  对应xml文件里sql 语句的ID 相对应
			sqlMap.delete("deleteById",5);
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
		TestProduct test = new TestProduct();
		test.before();
		test.testInstert();
		test.testUpdate();
        test.testSelectPersonById();
        test.testSelectAll();
        test.testDelete();
	}
}
