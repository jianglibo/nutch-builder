package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RegexUrlFilterConfFile {
	
	private List<String> acceptLines = new ArrayList<>();
	
	private List<String> skipLines = new ArrayList<>();
	
	public void persist(Path projectRoot) throws IOException {
		Path regexUrlfilter = projectRoot.resolve("conf/regex-urlfilter.txt");
		List<String> originLines = Files.readAllLines(regexUrlfilter);
		List<String> newLines = new ArrayList<>();
		for(String ol : originLines) {
			if (ol.trim().startsWith("#")) {
				String uncomment = ol.trim().substring(1);
				if (acceptLines.contains(uncomment)) {
					newLines.add(uncomment);
					acceptLines.remove(uncomment);
				} else {
					newLines.add(ol);
				}
			} else {
				if (skipLines.contains(ol.trim())) { // if already exists, take away from acceptsLines.
					skipLines.remove(ol.trim());
					newLines.add(ol);
				}else if(acceptLines.contains(ol.trim())) { // alreay exists.
					acceptLines.remove(ol.trim());
					newLines.add(ol);
				} else {
					newLines.add("#" + ol);
				}
			}
		}
		newLines.addAll(skipLines);
		newLines.addAll(acceptLines);
		Files.write(regexUrlfilter, newLines);
	}

	public RegexUrlFilterConfFile withDefaultSkips() {
		skipLines.add("-^(file|ftp|mailto):");
		skipLines.add("-\\.(gif|GIF|jpg|JPG|png|PNG|ico|ICO|css|CSS|sit|SIT|eps|EPS|wmf|WMF|zip|ZIP|ppt|PPT|mpg|MPG|xls|XLS|gz|GZ|rpm|RPM|tgz|TGZ|mov|MOV|exe|EXE|jpeg|JPEG|bmp|BMP|js|JS)$");
		skipLines.add("-[?*!@=]");
		skipLines.add("-.*(/[^/]+)/[^/]+\\1/[^/]+\\1/");
		return this;
	}
	
	public RegexUrlFilterConfFile addAccept(String acceptLine) {
		if (!acceptLine.startsWith("+")) {
			acceptLine = "+" + acceptLine;
		}
		acceptLines.add(acceptLine);
		return this;
	}
	
	public RegexUrlFilterConfFile addSkip(String skipLine) {
		if (!skipLine.startsWith("-")) {
			skipLine = "-" + skipLine;
		}
		skipLines.add(skipLine);
		return this;
	}
}
