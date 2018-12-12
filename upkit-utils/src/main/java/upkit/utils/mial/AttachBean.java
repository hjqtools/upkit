package upkit.utils.mial;

import java.io.File;

/**
 *
 * @description: 附件类，只有文件，即附件才文件名
 * @author: melody
 * @email: cuzart@163.com
 */
public class AttachBean {

	private String cid;
	private File file;
	private String fileName;

	public AttachBean() {
	}

	public AttachBean(File file, String fileName) {
		super();
		this.file = file;
		this.fileName = fileName;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
