package com.tg.api.intercept;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * json 处理
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper {

	private final String body;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				//解决中文乱码问题
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		body = stringBuilder.toString();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes("UTF-8"));
		ServletInputStream servletInputStream = new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
			@Override
			public void setReadListener(ReadListener arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}
		}; 
		return servletInputStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	public String getBody() {
		return this.body;
	}
}
