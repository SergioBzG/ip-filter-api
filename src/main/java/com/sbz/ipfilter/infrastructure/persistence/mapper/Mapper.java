package com.sbz.ipfilter.infrastructure.persistence.mapper;

public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}
