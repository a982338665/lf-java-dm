package dameng.pojo;

public class ProductCategory {
    private Integer PRODUCT_CATEGORYID;
    private String NAME;

    public int getPRODUCT_CATEGORYID() {
        return PRODUCT_CATEGORYID;
    }

    public void setPRODUCT_CATEGORYID(Integer PRODUCT_CATEGORYID) {
        this.PRODUCT_CATEGORYID = PRODUCT_CATEGORYID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public ProductCategory(Integer PRODUCT_CATEGORYID, String NAME) {
        super();
        this.PRODUCT_CATEGORYID = PRODUCT_CATEGORYID;
        this.NAME = NAME;
    }

    public ProductCategory() {
        super();
    }

    @Override
    public String toString() {
        return "ProductCategory [PRODUCT_CATEGORYID=" + PRODUCT_CATEGORYID + ", NAME=" + NAME + "]";
    }

}
