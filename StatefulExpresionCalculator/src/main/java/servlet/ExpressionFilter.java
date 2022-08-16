package servlet;


import lombok.extern.java.Log;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log
@WebFilter("/calc/*")
public class ExpressionFilter implements Filter {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("[-+*/]");
    private static final Pattern OPERAND_PATTERN = Pattern.compile("\\p{Lower}", Pattern.UNICODE_CHARACTER_CLASS);

    private static final int INVALID_EXPRESSION_CODE = 400;
    private static final int INVALID_PARAMETER_CODE = 403;

    private static final int PARAMETER_MIN_VALUE = -10000;
    private static final int PARAMETER_MAX_VALUE = 10000;

    private static final String INVALID_EXPRESSION_MESSAGE = "invalid expression";
    private static final String EXPRESSION_PARAMETER_NAME = "expression";
    private static final String FILTERED_METHOD_NAME = "PUT";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String BODY_ATTRIBUTE = "body";
    private static final String SLASH_SYMBOL = "/";


    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("filter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        boolean errorSends = false;

        if (req.getMethod().equals(FILTERED_METHOD_NAME)) {
            String attrName = getAttributeName(req);
            String attrValue;

            try (BufferedReader bf = req.getReader()) {
                attrValue = bf.lines().collect(Collectors.joining());
            }

            req.setAttribute(NAME_ATTRIBUTE, attrName);
            req.setAttribute(BODY_ATTRIBUTE, attrValue);

            if (attrName.equals(EXPRESSION_PARAMETER_NAME) && !EXPRESSION_PATTERN.matcher(attrValue).find()) {

                errorSends = true;
                resp.sendError(INVALID_EXPRESSION_CODE, INVALID_EXPRESSION_MESSAGE);

            } else {
                errorSends = checkOperands(resp, attrName, attrValue);
            }
        }

        if (errorSends) {
            return;
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        System.out.println("filter destroyed");
    }

    private String getAttributeName(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return uri.substring(uri.lastIndexOf(SLASH_SYMBOL) + 1);
    }

    private boolean checkOperands(HttpServletResponse resp, String attrName, String attrValue) throws IOException {
        int attrIntValue = 0;
        boolean errorSends = false;

        if (!attrName.equals(EXPRESSION_PARAMETER_NAME) && !OPERAND_PATTERN.matcher(attrValue).matches()) {
            attrIntValue = Integer.parseInt(attrValue);
        }

        if (attrIntValue < PARAMETER_MIN_VALUE || attrIntValue > PARAMETER_MAX_VALUE) {
            errorSends = true;
            resp.sendError(INVALID_PARAMETER_CODE);
        }

        return errorSends;
    }
}
