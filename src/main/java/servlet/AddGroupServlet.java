package servlet;

import model.Group;
import org.springframework.context.ApplicationContext;
import service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/addGroup"})
public class AddGroupServlet extends HttpServlet {
    private Service service;

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = applicationContext.getBean(Service.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //redirect to addGroup.jsp
        req.getRequestDispatcher("/WEB-INF/pages/addGroup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //waiting for data from the form
        String groupName = req.getParameter("name");
        boolean groupIsActive =  ("true".equals(req.getParameter("active")) ? true : false);

        if (groupName.length() < 1){
            resp.sendRedirect("html/error.jsp");
        } else {
            Group group = service.addGroup("ACP14", groupIsActive);
            req.setAttribute("group", group);
                req.getRequestDispatcher("/WEB-INF/pages/group-info.jsp").forward(req,resp);
        }
    }
}
