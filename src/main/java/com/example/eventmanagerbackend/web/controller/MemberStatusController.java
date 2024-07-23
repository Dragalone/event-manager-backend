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

    @GetMapping("/getAll")
    public List<MemberStatus> getAllStatus()
    {
        return memberStatusService.getAllStatus();
    }

    @GetMapping("/findById")
    public MemberStatus getStatusById( @RequestParam UUID id)
    {
        return memberStatusService.getStatusById(id);

    }

    @PostMapping("/addStatus")
    public void createNewStatus( @RequestParam String statusName)
    {

        memberStatusService.createNewStatus(statusName);
    }

    @PostMapping("/deleteStatus")
    public void deleteStatus(@RequestParam UUID id)
    {
        memberStatusService.deleteStatusById(id);
    }

}
