package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntityService<E, R, ID> {

    ModelListResponse<E> findAll(Pageable pageable);

    E findById(ID id);

    E create(R entityRequest);

    E update(ID id, R entityRequest);

    void deleteById(ID id);

}