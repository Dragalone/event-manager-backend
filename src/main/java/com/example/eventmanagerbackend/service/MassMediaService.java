package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.web.dto.request.MassMediaFilterRequest;
import com.example.eventmanagerbackend.web.dto.request.UpsertMassMediaRequest;
import com.example.eventmanagerbackend.web.dto.response.MassMediaResponse;
import com.example.eventmanagerbackend.web.dto.response.ModelListResponse;

import java.util.UUID;

public interface MassMediaService extends EntityService<MassMediaResponse, UpsertMassMediaRequest, UUID>{

    void setApprovment(UUID id, Approvement approvement);

    ModelListResponse<MassMediaResponse> filterBy(MassMediaFilterRequest filter);
}
