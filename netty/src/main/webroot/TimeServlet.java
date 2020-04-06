
import com.fenxiangz.learn.biowebserver.connector.ConnectorUtils;
import com.fenxiangz.learn.biowebserver.connector.HttpStatus;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class TimeServlet implements Servlet{

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        PrintWriter writer = servletResponse.getWriter();
        writer.print(ConnectorUtils.renderStatus(HttpStatus.SC_OK));
        writer.println("What time is it now?");
        writer.println(new Date().toString());
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}