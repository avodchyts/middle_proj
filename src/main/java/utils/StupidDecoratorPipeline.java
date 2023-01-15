package utils;

public class StupidDecoratorPipeline<T> implements Decorator<T> {
    private Decorator<T> decorator;

    public StupidDecoratorPipeline(Decorator<T> decorator) {
        this.decorator = decorator;
    }

    @Override
    public T apply(T driver) {
        return decorator.apply(driver);
    }

    public final DecoratorPipeline<T> addDecorator(Decorator<T> nextDecorator) {
        return new DecoratorPipeline<T>((T input) -> nextDecorator.apply(apply(input)));
    }

    public T execute(T input) {
        return decorator.apply(input);
    }
}
