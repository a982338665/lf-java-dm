package dameng.pojo;

public class BigData {
	private Long id;
	private byte[] photo; //ibatis�� Image ��Blob ӳ���byte[]
	private byte[] describe;
	private String txt; //mybatis �� Clob ӳ��� String
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public byte[] getDescribe() {
		return describe;
	}
	public void setDescribe(byte[] describe) {
		this.describe = describe;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	@Override
	public String toString() {
		return "TestBigDate [id=" + id + ", txt=" + txt + "]";
	}
	public BigData(Long id, byte[] photo, byte[] describe, String txt) {
		super();
		this.id = id;
		this.photo = photo;
		this.describe = describe;
		this.txt = txt;
	}
	public BigData() {
		super();
	}
}
