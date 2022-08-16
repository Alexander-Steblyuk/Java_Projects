package util;

public enum Operation {
    ADD('+', 1),
    SUB('-', 1),
    MUL('*', 2),
    DIV('/', 2);

    private final char name;
    private final int priority;

    Operation(char name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }


    public char getName() {
        return name;
    }
}
