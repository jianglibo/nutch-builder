package com.jianglibo.nutchbuilder.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class KatharsisContentResponseWrapper  extends HttpServletResponseWrapper {
	private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

	public KatharsisContentResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {

			@Override
			public void write(int b) throws IOException {
				bos.write(b);
			}

			@Override
			public void setWriteListener(WriteListener listener) {
			}

			@Override
			public boolean isReady() {
				return true;
			}
		};
	}

	// It's already a katharsis document.
	public ByteArrayOutputStream getBos() {
		return bos;
	}

	public void writeToWrapped(ByteArrayOutputStream os) throws IOException {
		ServletOutputStream sos = getResponse().getOutputStream(); 
		sos.write(os.toByteArray());
		sos.flush();
	}
}
