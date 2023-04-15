package dameng.dao;

import java.util.List;

import dameng.pojo.ProductCategory;

public interface ProductCategoryMapper {

    public List<ProductCategory> selectAll();

    public int selectCount();

    public ProductCategory selectProductCategoryById(Integer id);

    public void insertProductCategory(ProductCategory ProductCategory);

    public void deleteById(Integer id);

    public void updateProductCategory(ProductCategory ProductCategory);

    void autoInsert(String s);
}
