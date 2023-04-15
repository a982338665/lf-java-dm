package dameng.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value="PRODUCTION.BIG_DATA")
public class BigData {
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;
	private byte[] photo; //mybatis�� Image ��Blob ӳ���byte[]
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
		return "BigDate [id=" + id + ", txt=" + txt + "]";
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
