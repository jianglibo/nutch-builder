package com.jianglibo.nutchbuilder.nutchalter;


import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.exception.NutchConfigXmlException;

public class TestRegexUrlFilterConfFile extends Tbase {

	@Test
	public void testRegexFile() throws IOException, SAXException, TransformerException, NutchConfigXmlException {
		Path cfile = StepBase.neighborProjectRoot.resolve("conf/regex-urlfilter.txt");
		RegexUrlFilterConfFile rufc = new RegexUrlFilterConfFile();
		rufc.withDefaultSkips();
		rufc.addAccept("aabbcc");
		rufc.persist(StepBase.neighborProjectRoot);
		assertTrue(Files.lines(cfile).filter(l -> l.equals("+aabbcc")).findAny().isPresent());
	}
}
