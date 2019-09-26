package com.zxyun.order.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @des: 自定义实体转换
 * @Author: given
 * @Date 2019/7/19 14:53
 */
public abstract class Converter<D, E> {

    private Function<D,E> fromDto;
    private Function<E,D> fromEntity;

    protected abstract Function<D,E> fromDtoFunction();

    protected abstract Function<E,D> fromEntityFunction ();

    /**
     * 单个实体转换
     * @param dto
     * @return
     */
    public final E convertToEntity (final D dto) {
        if (fromDto == null)
            fromDto = fromDtoFunction();

        return fromDto.apply(dto);
    }

    /**
     * 单个实体转换
     * @param entity
     * @return
     */
    public final D convertToDto (final E entity) {
        if (fromEntity == null)
            fromEntity = fromEntityFunction();

        return fromEntity.apply(entity);
    }

    /**
     * 多个实体转换
     * @param dtos
     * @return
     */
    public final List<E> convertToEntities (final Collection<D> dtos) {
        return dtos.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    /**
     * 多个实体转换
     * @param entities
     * @return
     */
    public final List<D> convertToDtos (final Collection<E> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
