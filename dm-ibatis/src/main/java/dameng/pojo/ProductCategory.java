package dameng.pojo;
public class ProductCategory {
	
	private Long productCategoryId;
	private String name;
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ProductCategory [productCategoryId=" + productCategoryId + ", name=" + name + "]";
	}
	public ProductCategory(Long productCategoryId, String name) {
		super();
		this.productCategoryId = productCategoryId;
		this.name = name;
	}
	public ProductCategory() {
		super();
	}
}
