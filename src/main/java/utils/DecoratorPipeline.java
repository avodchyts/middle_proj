package utils;
public class DecoratorPipeline<T> implements Decorator<T> {
    private final Decorator<T> decorator;

    public DecoratorPipeline(Decorator<T> decorator) {
        this.decorator = decorator;
    }

    @Override
    public T decorate(T driver){
        return decorator.decorate(driver);
    }
    public final DecoratorPipeline<T> addDecorator(Decorator<T> nextDecorator) {
        return new DecoratorPipeline<>((T input) -> nextDecorator.decorate(decorate(input)));
    }
}
