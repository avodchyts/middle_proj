package ui.support;
import support.DeviceFactory;public class DecoratorPipeline<T> implements DeviceFactory.Decorator<T> {
    private final DeviceFactory.Decorator<T> decorator;
    public DecoratorPipeline(DeviceFactory.Decorator<T> decorator) {
        this.decorator = decorator;
    }

    @Override
    public T decorate(T driver){
        return decorator.decorate(driver);
    }
    public final DeviceFactory.DecoratorPipeline<T> addDecorator(DeviceFactory.Decorator<T> nextDecorator) {
        return new DeviceFactory.DecoratorPipeline<>((T input) -> nextDecorator.decorate(decorate(input)));
    }
}
