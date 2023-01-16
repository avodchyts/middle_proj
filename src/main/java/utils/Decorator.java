package utils;

import java.util.function.UnaryOperator;

public interface Decorator<T> extends UnaryOperator<T> {
    T apply(T t);
}
