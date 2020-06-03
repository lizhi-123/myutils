package config;

/**
 * 缓存配置文件的key
 * 
 * @author dawei
 *
 */
public class ConfigFile {

	private String fileName;// 文件名
	private String fileExtensionName;// 文件的扩展名

	public ConfigFile(String fileName, String fileExtensionName) {
		super();
		this.fileName = fileName;
		this.fileExtensionName = fileExtensionName;
	}

	public ConfigFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtensionName() {
		return fileExtensionName;
	}

	public void setFileExtensionName(String fileExtensionName) {
		this.fileExtensionName = fileExtensionName;
	}

	public String generateKey() {
		return "ConfigFile [fileName=" + fileName + ", fileExtensionName=" + fileExtensionName + "]";
	}

}
