package com.example.eventmanagerbackend.service.impl;

import com.example.eventmanagerbackend.entity.Approvement;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.entity.MassMedia;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.exception.RegistrationClosedException;
import com.example.eventmanagerbackend.mapper.MassMediaMapper;
import com.example.eventmanagerbackend.repository.EventMemberSpecification;
import com.example.eventmanagerbackend.repository.MassMediaRepository;
import com.example.eventmanagerbackend.repository.MassMediaSpecification;
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
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
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
    public MassMediaResponse findById(UUID id) {
        log.info("Find Media by ID: {}", id);
        return massMediaMapper.massMediaToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Media with id {0} not founded", id)
                )));
    }

    @Override
    public MassMediaResponse create(UpsertMassMediaRequest entityRequest) {
        log.info("Creating media {}", entityRequest);

        MassMedia massMedia = massMediaMapper.upsertRequestToMassMedia(entityRequest);
        if (!massMedia.getEvent().getRegOpen()) {
            throw new RegistrationClosedException(
                    MessageFormat.format("Registration on event {0} closed!",massMedia.getEvent().getName())
            );
        }

        return massMediaMapper.massMediaToResponse(
                repository.save(massMediaMapper.upsertRequestToMassMedia(entityRequest))
        );
    }

    @Override
    public MassMediaResponse update(UUID id, UpsertMassMediaRequest entityRequest) {

        log.info("Update media with ID: {}", id);

        var updatedMassMedia = updateFields(repository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("Event member with ID {0} not found!", id)
                        )),
                massMediaMapper.upsertRequestToMassMedia(entityRequest));

        log.info("Updated event member: {}", updatedMassMedia);

        return massMediaMapper.massMediaToResponse(
                repository.save(updatedMassMedia)
        );
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Delete mass media ID: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void setApprovment(UUID id, Approvement approvement) {
        log.info("Set {} approvment for media with id: {}", approvement, id);
        MassMedia massMedia = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Mass media with ID {0} not found!", id)
                ));
        massMedia.setApprovement(approvement);
        repository.save(massMedia);
    }

    @Override
    public ModelListResponse<MassMediaResponse> filterBy(MassMediaFilterRequest filter) {
        log.info("Find events members with filter: {}",filter);
        Page<MassMedia> massMedia = repository.findAll(MassMediaSpecification.withFilter(filter), filter.getPagination().pageRequest());
        return ModelListResponse.<MassMediaResponse>builder()
                .totalCount(massMedia.getTotalElements())
                .data(massMedia.stream().map(massMediaMapper::massMediaToResponse).toList())
                .build();
    }

    protected MassMedia updateFields(MassMedia oldEntity, MassMedia newEntity) {
        if (StringUtils.hasText(newEntity.getCompany())){
            oldEntity.setCompany(newEntity.getCompany());
        }

        if (StringUtils.hasText(newEntity.getFirstname())){
            oldEntity.setFirstname(newEntity.getFirstname());
        }
        if (StringUtils.hasText(newEntity.getMiddlename())){
            oldEntity.setMiddlename(newEntity.getMiddlename());
        }
        if (StringUtils.hasText(newEntity.getLastname())){
            oldEntity.setLastname(newEntity.getLastname());
        }
        if (StringUtils.hasText(newEntity.getPosition())){
            oldEntity.setPosition(newEntity.getPosition());
        }
        if (newEntity.getEvent()!=null){
            oldEntity.setEvent(newEntity.getEvent());
        }
        if (newEntity.getApprovement()!=null){
            oldEntity.setApprovement(newEntity.getApprovement());
        }
        if(newEntity.getPassportSeries() !=null){
            oldEntity.setPassportSeries(newEntity.getPassportSeries());
        }
        if(newEntity.getPassportNumber() != null)
        {
            oldEntity.setPassportNumber(newEntity.getPassportNumber());
        }
        if(newEntity.getEquipment() != null)
        {
            oldEntity.setEquipment(newEntity.getEquipment());
        }
        return oldEntity;
    }
}
