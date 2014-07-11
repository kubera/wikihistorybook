package ch.fhnw.business.iwi.wikihistorybook.webapp.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphCreator;

public class SvgServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private SvgGraphCreator svgGraphCreator;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getPathInfo().substring(1);
        ByteArrayOutputStream imageStream = svgGraphCreator.getSvgStream(imageName);
        setImageStream2Response(imageStream, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int year = Integer.valueOf(request.getParameter("year"));
        String imageName = svgGraphCreator.createSvgStreamAndStoreToSession(year);
        setImageName2Response(imageName, response);
    }

    @Override
    public void init() throws ServletException {
        // we need to do the injection manually 
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        AutowireCapableBeanFactory ctx = context.getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }

    private void setImageStream2Response(ByteArrayOutputStream imageContent, HttpServletResponse response)
            throws IOException {
        response.reset();
        response.setContentType("image/svg+xml ");
        response.setContentLength(imageContent.size());
        IOUtils.write(imageContent.toByteArray(), response.getOutputStream());
    }

    private void setImageName2Response(String svgName, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("image/svg+xml ");
        byte[] nameInBytes = svgName.getBytes("UTF-8");
        response.setContentLength(nameInBytes.length);
        IOUtils.write(nameInBytes, response.getOutputStream());
    }

}
