package interview.test.dom;

public class SupportedFile {

	private String fileName;
	private String extension;
	private long size;
	private String mimeType;

	public SupportedFile(String fileName,String extension,long size,String mimeType) {
		this.fileName = fileName;
		this.extension = extension;
		this.size = size;
		this.mimeType = mimeType;
	}

	public String getFileName() {
		return fileName;
	}

	public String getExtension() {
		return extension;
	}

	public long getSize() {
		return size;
	}

	public String getMimeType() {
		return mimeType;
	}

}