package com.shoppingService.java11;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileMethods {
	public static void main(String[] args) throws IOException {
		Path file = Paths.get("D:/Bkp/D/Workspace/TestWorkspace/shopping-service/src/main/resources/SampleFile.txt");
		String content = Files.readString(file);
		log.info("{}", content);

		Path newFile = Paths
				.get("D:/Bkp/D/Workspace/TestWorkspace/shopping-service/src/main/resources/SampleFile2.txt");
		String newContent = content.concat("\n7\n");
		Files.writeString(newFile, newContent);
		String readNewContent = Files.readString(newFile);
		log.info("{}", readNewContent);
	}
}
