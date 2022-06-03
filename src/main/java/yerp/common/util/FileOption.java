package yerp.common.util;

import java.io.File;

public class FileOption {
	private String filePath;
	private String[] uploadableExtension;
	private String[] directoryColumn;
	private int uploadedCount = 0;
	
	public FileOption() {}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath.replace(File.separator, "/");
	}

	public String[] getUploadableExtension() {
		return uploadableExtension == null? new String[] {} : uploadableExtension;
	}

	public void setUploadableExtension(String[] uploadableExtension) {
		this.uploadableExtension = uploadableExtension;
	}

	public String[] getDirectoryColumn() {
		return directoryColumn;
	}

	public void setDirectoryColumn(String[] directoryColumn) {
		this.directoryColumn = directoryColumn;
	}

	public int getUploadedCount() {
		return uploadedCount;
	}

	public void setUploadedCount(int uploadedCount) {
		this.uploadedCount = uploadedCount;
	}
}
