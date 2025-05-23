package com.example.eventmanagerbackend.web.controller;

import com.example.eventmanagerbackend.entity.MemberStatus;
import com.example.eventmanagerbackend.service.MemberStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/v1/status",produces = "application/json")
public class MemberStatusController {

    private final MemberStatusService memberStatusService;

    @GetMapping()
    public List<MemberStatus> getAllStatus()
    {
        return memberStatusService.getAllStatus();
    }

    @GetMapping("/{id}")
    public MemberStatus getStatusById( @PathVariable UUID id)
    {
        return memberStatusService.getStatusById(id);
    }

    @PostMapping()
    public void createNewStatus( @RequestParam String statusName)
    {
        memberStatusService.createNewStatus(statusName);
    }

    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable UUID id)
    {
        memberStatusService.deleteStatusById(id);
    }

}
