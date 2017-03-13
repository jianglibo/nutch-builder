package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RegexUrlFilterConfFile {
	
	private List<String> linesToAppend = new ArrayList<>();
	
	public void alter(Path projectRoot) throws IOException {
		Path regexUrlfilter = projectRoot.resolve("conf/regex-urlfilter.txt");
		List<String> originLines = Files.readAllLines(regexUrlfilter);
		List<String> newLines = new ArrayList<>();
		for(String ol : originLines) {
			if (ol.trim().startsWith("#")) {
				newLines.add(ol);
			} else {
				if (linesToAppend.contains(ol.trim())) {
					linesToAppend.remove(ol.trim());
					newLines.add(ol);
				} else {
					newLines.add("#" + ol);
				}
			}
		}
		newLines.addAll(linesToAppend);
		Files.write(regexUrlfilter, newLines);
	}

	public RegexUrlFilterConfFile withDefaultSkips() {
		linesToAppend.add("-^(file|ftp|mailto):");
		linesToAppend.add("-\\.(gif|GIF|jpg|JPG|png|PNG|ico|ICO|css|CSS|sit|SIT|eps|EPS|wmf|WMF|zip|ZIP|ppt|PPT|mpg|MPG|xls|XLS|gz|GZ|rpm|RPM|tgz|TGZ|mov|MOV|exe|EXE|jpeg|JPEG|bmp|BMP|js|JS)$");
		linesToAppend.add("-[?*!@=]");
		linesToAppend.add("-.*(/[^/]+)/[^/]+\\1/[^/]+\\1/");
		return this;
	}
	
	public RegexUrlFilterConfFile addAccept(String acceptLine) {
		linesToAppend.add(acceptLine);
		return this;
	}
	
}
