package models.service;

import api.models.ResponseDto;

public class EntityService<T> {
    ResponseDto responseDto;
    T entity;

    public EntityService(ResponseDto responseDto) {
        this.responseDto = responseDto;
    }

    public T getSinglInfo() {
        return (T) responseDto.getBody().as(entity.getClass());
    }

    public T[] getArrayInfo() {
        return (T[]) responseDto
                .getBody()
                .as(entity.getClass());
    }

}
