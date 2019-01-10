package upkit.bp.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepClone {

	public static void main(String[] args) {

		Template template = new Template();
		template.setValue_1("1");
		template.setValue_2("2");
		
		ByteArrayOutputStream bao = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bas = null;
		ObjectInputStream ois = null;
		try {
			bao = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bao);
			
			// 序列化
			oos.writeObject(template);
			
			// 反序列化
			bas = new ByteArrayInputStream(bao.toByteArray());
			ois = new ObjectInputStream(bas);
			
			Template t = (Template) ois.readObject();
			
			System.out.println(t);
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class Template implements Serializable{

	private transient String value_1;
	private String value_2;

	public Template() {

	}
	
	

	@Override
	public String toString() {
		return "Template [value_1=" + value_1 + ", value_2=" + value_2 + "]";
	}



	public String getValue_1() {
		return value_1;
	}

	public void setValue_1(String value_1) {
		this.value_1 = value_1;
	}

	public String getValue_2() {
		return value_2;
	}

	public void setValue_2(String value_2) {
		this.value_2 = value_2;
	}

}
