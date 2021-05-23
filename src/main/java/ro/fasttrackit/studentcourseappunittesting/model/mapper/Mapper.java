package ro.fasttrackit.studentcourseappunittesting.model.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<A, E> {
    A toApi(E source);

    E toEntity(A source);

    default List<A> toApi(Collection<E> source) {
        return source.stream()
                .map(this::toApi)
                .collect(Collectors.toList());
    }

    default List<E> toEntity(Collection<A> source) {
        return source.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
