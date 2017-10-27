package interview.test.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;

import interview.test.dom.SupportedFile;


public class SupportedFilesService {
	public List<SupportedFile> getFiles() throws Exception {
		Properties properties = new Properties();
   		properties.load(SupportedFilesService.class.getResourceAsStream("/config.properties"));
		
		Collection<File> files = FileUtils.listFiles(new File((String)properties.get("input_directory")), ((String)properties.get("supported_file_types")).split(","), true);

		List<SupportedFile> supportedFiles = new ArrayList<>();
		Tika tika = new Tika();
		for(File file : files) {
			SupportedFile supportedFile = new SupportedFile(file.getAbsolutePath(), FilenameUtils.getExtension(file.getAbsolutePath()), file.getTotalSpace(), tika.detect(file));
			supportedFiles.add(supportedFile);
		}

		return supportedFiles;
	}
}