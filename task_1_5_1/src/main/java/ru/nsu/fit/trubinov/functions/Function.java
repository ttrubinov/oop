package ru.nsu.fit.trubinov.functions;

import ru.nsu.fit.trubinov.number.Number;

import java.util.List;

public interface Function<T extends Number<T>> {
    int getArity();

    List<T> getArgs();

    default  boolean applicable() {
        return getArity() == getArgs().size();
    }

    T apply();
}
