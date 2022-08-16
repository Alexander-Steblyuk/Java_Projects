package util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExpressionParser {
    private static final Pattern OPERAND_PATTERN = Pattern.compile("\\p{Lower}", Pattern.UNICODE_CHARACTER_CLASS);
    private static final String EXPRESSION_PARAMETER_NAME = "expression";

    public boolean isAllOperands(String expression, Enumeration<String> transOperandsNames) {
        boolean result = true;
        Matcher matcher = OPERAND_PATTERN.matcher(expression);
        List<String> transOperands = new ArrayList<>();
        List<String> exprOperands = matcher.results()
                .map(MatchResult::group)
                .collect(Collectors.toList());

        while (transOperandsNames.hasMoreElements()) {
            String curOperand = transOperandsNames.nextElement();

            if (!curOperand.equals(EXPRESSION_PARAMETER_NAME)) {
                transOperands.add(curOperand);
            }
        }

        for (String operand : exprOperands) {
            if (!transOperands.contains(operand)) {
                result = false;
                break;
            }
        }

        return result;
    }

}
