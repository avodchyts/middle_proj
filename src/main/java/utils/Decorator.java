package utils;

import java.util.function.UnaryOperator;

public interface Decorator<T> {
    T apply(T t);
}
