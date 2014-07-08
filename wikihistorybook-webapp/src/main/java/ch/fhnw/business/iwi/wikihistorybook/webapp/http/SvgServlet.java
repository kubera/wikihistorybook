package ch.fhnw.business.iwi.wikihistorybook.webapp.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class SvgServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get svg file name and svg graph
        String uniqueSvgName = request.getPathInfo().substring(1);
        ByteArrayOutputStream imageContent = (ByteArrayOutputStream) request.getSession().getAttribute(uniqueSvgName);
        // set the svg to the response
        response.reset();
        response.setContentType("image/svg+xml ");
        response.setContentLength(imageContent.size());
        IOUtils.write(imageContent.toByteArray(), response.getOutputStream());
    }

}
