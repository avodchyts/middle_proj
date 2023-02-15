package api;

import java.util.function.Function;

public interface IApiClient<T, R> extends Function<T,  R> {

    @Override
    public R apply(T t);
}
