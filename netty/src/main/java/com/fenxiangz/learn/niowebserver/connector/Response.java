package com.fenxiangz.learn.niowebserver.connector;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * HTTP/1.1 200 OK
 */
public class Response implements ServletResponse {
    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void sendStaticResources() throws IOException {
        File file = new File(ConnectorUtils.WEB_ROOT, request.getUri());
        try {
            writeResponse(file, HttpStatus.SC_OK);
        } catch (IOException e) {
            File file404 = new File(ConnectorUtils.WEB_ROOT, "404.html");
            writeResponse(file404, HttpStatus.SC_NOT_FOUND);
        }
    }

    private void writeResponse(File resource, HttpStatus status) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(resource));
        output.write(ConnectorUtils.renderStatus(status).getBytes());
        byte[] buffer = new byte[BUFFER_SIZE];
        int length = 0;
        while ((length = bufferedInputStream.read(buffer)) != -1) {
            output.write(buffer, 0, length);
            output.flush();
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        PrintWriter writer = new PrintWriter(output, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
