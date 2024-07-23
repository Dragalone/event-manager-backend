package com.example.eventmanagerbackend.service;

import com.example.eventmanagerbackend.entity.MemberStatus;
import com.example.eventmanagerbackend.repository.MemberStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberStatusService {

    private final MemberStatusRepository memberStatusRepository;

    public List<MemberStatus> getAllStatus()
    {
        return memberStatusRepository.findAll();
    }

    public MemberStatus getStatusById(UUID id){

        return memberStatusRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = false)
    public void deleteStatusById(UUID id)
    {
        memberStatusRepository.deleteById(id);
    }

    @Transactional
    public void createNewStatus(String statusName)
    {
        MemberStatus memberStatus = new MemberStatus();
        memberStatus.setStatus(statusName);
        memberStatusRepository.save(memberStatus);
    }





}
