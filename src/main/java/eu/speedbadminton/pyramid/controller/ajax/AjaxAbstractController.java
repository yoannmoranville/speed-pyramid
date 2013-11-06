package eu.speedbadminton.pyramid.controller.ajax;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * User: Yoann Moranville
 * Date: 04/10/2013
 *
 * @author Yoann Moranville
 */
public class AjaxAbstractController {
    private static final Logger LOG = Logger.getLogger(AjaxAbstractController.class);

    private static final String APPLICATION_JSON = "application/json";
    protected static final String UTF8 = "UTF-8";
    protected static final String START_ARRAY = "[\n";
    protected static final String END_ARRAY = "]\n";
    protected static final String START_ITEM = "{";
    protected static final String COLON = ": ";
    protected static final String END_ITEM = "}";
    protected static final String COMMA = ",";

    protected static Writer getResponseWriter(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(UTF8);
        response.setContentType(APPLICATION_JSON);
        return new OutputStreamWriter(response.getOutputStream(), UTF8);
    }
    protected static void closeWriter(Writer writer) throws IOException {
        writer.flush();
        writer.close();
    }
    protected static String aroundQuotes(String data) {
        return "\"" + data + "\"";
    }
    protected static void writeUserData(Writer writer, String name, String email) throws IOException {
        writer.write(START_ITEM);
        writer.write(aroundQuotes("username"));
        writer.write(COLON);
        writer.write(aroundQuotes(name));
        writer.write(COMMA);
        writer.write(aroundQuotes("email"));
        writer.write(COLON);
        writer.write(aroundQuotes(email));
        writer.write(END_ITEM);
    }
    protected static void writeResultData(Writer writer, String askerName, String askedName) throws IOException {
        writer.write(START_ITEM);
        writer.write(aroundQuotes("asker"));
        writer.write(COLON);
        writer.write(aroundQuotes(askerName));
        writer.write(COMMA);
        writer.write(aroundQuotes("asked"));
        writer.write(COLON);
        writer.write(aroundQuotes(askedName));
        writer.write(END_ITEM);
    }
    protected static void writeSimpleData(Writer writer, String id, String value) throws IOException {
        writer.write(START_ITEM);
        writer.write(aroundQuotes(id));
        writer.write(COLON);
        writer.write(aroundQuotes(value));
        writer.write(END_ITEM);
    }
    protected static void startArray(Writer writer) throws IOException {
        writer.write(START_ARRAY);
    }
    protected static void endArray(Writer writer) throws IOException {
        writer.write(END_ARRAY);
    }
    protected static void pauseArray(Writer writer) throws IOException {
        writer.write(COMMA);
    }
}
