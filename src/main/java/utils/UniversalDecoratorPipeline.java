package utils;

public class UniversalDecoratorPipeline<T> implements Decorator<T> {
    private Decorator<T> decorator;

    public UniversalDecoratorPipeline(Decorator<T> decorator) {
        this.decorator = decorator;
    }

    @Override
    public T apply(T driver) {
        return decorator.apply(driver);
    }

    public final DecoratorPipeline<T> addDecorator(Decorator<T> nextDecorator) {
        return new DecoratorPipeline<T>((T input) -> nextDecorator.apply(apply(input)));
    }
}
