package com.example.eventmanagerbackend.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelListResponse<T> {

    private Long totalCount;

    private List<T> data;

}
