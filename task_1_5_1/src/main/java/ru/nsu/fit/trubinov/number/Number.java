package ru.nsu.fit.trubinov.number;

public interface Number<T extends Number<T>> {
    String toString();

    boolean equals(T b);

    T add(T b);

    T sub(T b);

    T mul(T b);

    T div(T b);

    T log();

    T cos();

    T sin();
}
