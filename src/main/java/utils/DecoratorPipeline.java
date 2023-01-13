package utils;

import java.util.function.UnaryOperator;

public class DecoratorPipeline<T> implements UnaryOperator<T> {
    private final UnaryOperator<T> decorator;

    public DecoratorPipeline(UnaryOperator<T> decorator) {
        this.decorator = decorator;
    }

    @Override
    public T apply(T driver){
        return decorator.apply(driver);
    }
    public final DecoratorPipeline<T> addDecorator(UnaryOperator<T> nextDecorator) {
        return new DecoratorPipeline<T>((T input) -> nextDecorator.apply(apply(input)));
    }
}
