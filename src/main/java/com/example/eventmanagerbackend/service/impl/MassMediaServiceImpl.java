package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.MassMedia;
import com.example.eventmanagerbackend.mapper.MassMediaMapper;
import com.example.eventmanagerbackend.repository.MassMediaRepository;
import com.example.eventmanagerbackend.service.MassMediaService;
import com.example.eventmanagerbackend.web.dto.request.MassMediaFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertMassMediaRequest;
import com.example.eventmanagerbackend.web.dto.response.EventMemberResponse;
import com.example.eventmanagerbackend.web.dto.response.MassMediaResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class MassMediaServiceImpl implements MassMediaService {

    private final MassMediaRepository repository;

    private final MassMediaMapper massMediaMapper;
    @Override
    public ModelListResponse<MassMediaResponse> findAll(Pageable pageable) {
        log.info("Find all MassMedia");
        Page<MassMedia> massMedia = repository.findAll(pageable);
        return ModelListResponse.<MassMediaResponse>builder()
                .totalCount(massMedia.getTotalElements())
                .data(massMedia.stream().map(massMediaMapper::massMediaToResponse).toList())
                .build();
    }

    @Override
    public MassMediaResponse findById(UUID uuid) {
        return null;
    }

    @Override
    public MassMediaResponse create(UpsertMassMediaRequest entityRequest) {
        return null;
    }

    @Override
    public MassMediaResponse update(UUID uuid, UpsertMassMediaRequest entityRequest) {
        return null;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void setApprovment(UUID id, Approvement approvement) {

    }

    @Override
    public ModelListResponse<MassMediaResponse> filterBy(MassMediaFilterRequest filter) {
        return null;
    }
}
