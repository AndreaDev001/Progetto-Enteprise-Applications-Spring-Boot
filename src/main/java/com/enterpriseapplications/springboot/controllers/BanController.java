package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.services.interfaces.BanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bans")
@RequiredArgsConstructor
public class BanController
{
    private final BanService banService;


    @GetMapping("/banner/{userID}")
    public ResponseEntity<PaginationResponse<BanDto>> getCreatedBans(@PathVariable("userID") Long userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<BanDto> bans = this.banService.getCreatedBans(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(bans.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),bans.getTotalPages(),bans.getTotalElements()));
    }

    @GetMapping("/banned/{userID}")
    public ResponseEntity<PaginationResponse<BanDto>> getReceivedBans(@PathVariable("userID") Long userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<BanDto> bans = this.banService.getReceivedBans(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(bans.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),bans.getTotalPages(),bans.getTotalElements()));
    }

    @GetMapping("/banned/{userID}/active")
    public ResponseEntity<BanDto> findBan(@PathVariable("userID") Long userID) {
        return ResponseEntity.ok(this.banService.getCurrentBan(userID));
    }

    @DeleteMapping("{banID}")
    public ResponseEntity<BanDto> deleteBan(@PathVariable("banID") Long banID) {
        this.banService.deleteBan(banID);
        return ResponseEntity.noContent().build();
    }
}