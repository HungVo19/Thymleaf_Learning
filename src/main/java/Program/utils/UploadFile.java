package Program.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import Program.config.Constants;

public class UploadFile {
	public static String upload(MultipartFile file) throws IOException {
		// create path from file name and destination folder
		Path fileNameAndPath = Paths.get(Constants.UPLOAD_DIR, file.getOriginalFilename());
		// copy file to destination folder
		Files.write(fileNameAndPath, file.getBytes());
		return fileNameAndPath.toString();
	}

	public static String uploadImage(MultipartFile file) throws IOException {
		String name = Objects.requireNonNull(file.getOriginalFilename()).substring(0,file.getOriginalFilename().lastIndexOf("."))
				+ System.currentTimeMillis() + ".jpg";
		Path fileNameAndPath = Paths.get(Constants.UPLOAD_IMG_DIR, name);
		Files.write(fileNameAndPath, file.getBytes(), StandardOpenOption.CREATE);
		return fileNameAndPath.toString().substring(fileNameAndPath.toString().lastIndexOf("\\upload"));
	}

	public static String createPath(String fileName) {
		Path path = Paths.get(Constants.UPLOAD_DIR, fileName);
		return path.toString();
	}

	public static List<String> uploadMultiFiles(MultipartFile[] files) throws IOException {
		List<String> list = new ArrayList<>();
		for (MultipartFile file : files) {
			Path fileNameAndPath = Paths.get(Constants.UPLOAD_DIR, file.getOriginalFilename());
			Files.write(fileNameAndPath, file.getBytes());
			list.add(fileNameAndPath.toString());
		}
		return list;
	}
}
