package util;

import java.util.LinkedList;
import java.util.Deque;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ExpressionCalculator {
    private static final char OPEN_BRACE = '(';
    private static final char CLOSE_BRACE = ')';

    private final Deque<String> output;
    private final Deque<Operation> operations;

    public ExpressionCalculator() {
        output = new LinkedList<>();
        operations = new LinkedList<>();
    }

    public int calculate(String inputExpression, Map<String, String> operands) {

        String expression = inputExpression.replace(" ", "");

        for (char token : expression.toCharArray()) {
            Optional<Operation> operation = Stream.of(Operation.values())
                    .filter(e -> e.getName() == token)
                    .findFirst();

            if (operation.isEmpty()) {
                if (token != CLOSE_BRACE) {
                    addToken(token, operands);
                } else {
                    computeExpressionIntoBraces();
                }
            } else {
                if (operations.isEmpty()) {
                    operations.push(operation.get());
                } else {
                    executeHighPriorityComputation(operation.get());
                }
            }
        }

        int result = 0;

        while (!operations.isEmpty()) {
            result = executeOperation(output.pop(), output.pop(), operations.pop());
            output.push(String.valueOf(result));
        }

        return result;
    }

    private void computeExpressionIntoBraces() {
        while (true) {
            int result = executeOperation(output.pop(), output.pop(), operations.pop());

            if (!output.isEmpty() && output.peek().equals(String.valueOf(OPEN_BRACE))) {
                output.pop();
                output.push(String.valueOf(result));
                break;
            }

            output.push(String.valueOf(result));
        }
    }

    private void executeHighPriorityComputation(Operation operation) {
        while (!operations.isEmpty() &&
                operations.peek().getPriority() >= operation.getPriority() &&
                !checkLastOpenBrace()) {
            int result = executeOperation(output.pop(), output.pop(), operations.pop());
            output.push(String.valueOf(result));
        }

        operations.push(operation);
    }

    private boolean checkLastOpenBrace() {
        return List.copyOf(output).subList(1, output.size()).get(0).equals(String.valueOf(OPEN_BRACE));
    }

    private int executeOperation(String secondOp, String firstOp, Operation operation) {
        int result;

        switch (operation) {
            case ADD:
                result = Integer.parseInt(firstOp) + Integer.parseInt(secondOp);
                break;

            case SUB:
                result = Integer.parseInt(firstOp) - Integer.parseInt(secondOp);
                break;

            case MUL:
                result = Integer.parseInt(firstOp) * Integer.parseInt(secondOp);
                break;

            case DIV:
                result = Integer.parseInt(firstOp) / Integer.parseInt(secondOp);
                break;

            default:
                result = 0;
        }

        return result;
    }

    private void addToken(char token, Map<String, String> operands) {
        String value = operands.get(String.valueOf(token));

        output.push(Objects.requireNonNullElseGet(value, () -> String.valueOf(token)));
    }
}
