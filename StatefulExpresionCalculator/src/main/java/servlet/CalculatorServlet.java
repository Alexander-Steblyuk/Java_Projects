package servlet;

import lombok.extern.java.Log;
import util.ExpressionCalculator;
import util.ExpressionParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Log
@WebServlet("/calc/*")
public class CalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1;
    private static final int SUCCESS_CODE = 200;
    private static final int FIRST_ASSIGN_CODE = 201;
    private static final int DELETE_CODE = 204;
    private static final int OPERANDS_NOT_ASSIGNED_CODE = 409;

    private static final String EXPRESSION_ATTRIBUTE_NAME = "expression";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String BODY_ATTRIBUTE = "body";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        String expression = (String) session.getAttribute(EXPRESSION_ATTRIBUTE_NAME);

        boolean allOperandsAssigned = new ExpressionParser().isAllOperands(expression, attributeNames);

        if (!allOperandsAssigned) {
            resp.setStatus(OPERANDS_NOT_ASSIGNED_CODE);
        } else {
            resp.setStatus(SUCCESS_CODE);

            try {
                int result = new ExpressionCalculator().calculate(expression, getOperands(session));

                resp.getWriter().print(result);
            } catch (IOException e) {
                log.warning(e.getLocalizedMessage());
                throw new IOException(e);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String attrName = (String) req.getAttribute(NAME_ATTRIBUTE);
        String attrValue = (String) req.getAttribute(BODY_ATTRIBUTE);

        if (session.getAttribute(attrName) != null) {
            resp.setStatus(SUCCESS_CODE);
        } else {
            resp.setStatus(FIRST_ASSIGN_CODE);
        }

        String linkedValue = (String) session.getAttribute(attrValue);

        if (linkedValue != null) {
            session.setAttribute(attrName, linkedValue);
        } else {
            session.setAttribute(attrName, attrValue);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute(getAttributeName(req));

        resp.setStatus(DELETE_CODE);
    }

    private String getAttributeName(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    private Map<String, String> getOperands(HttpSession session) {
        Map<String, String> operands = new HashMap<>();
        Enumeration<String> operandsNames = session.getAttributeNames();

        while (operandsNames.hasMoreElements()) {
            String currName = operandsNames.nextElement();
            if (!currName.equals(EXPRESSION_ATTRIBUTE_NAME)) {
                operands.put(currName, (String) session.getAttribute(currName));
            }
        }

        return operands;
    }
}
