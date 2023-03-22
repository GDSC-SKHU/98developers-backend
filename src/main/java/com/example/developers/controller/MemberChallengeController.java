package com.example.developers.controller;

import com.example.developers.DTO.ChallengeDTO;
import com.example.developers.DTO.ChangeMemberChallengeDTO;
import com.example.developers.domain.Member;
import com.example.developers.service.ChallengeService;
import com.example.developers.service.MemberChallengeService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberChallengeController {
    private final MemberChallengeService memberChallengeService;

    @PostMapping("/user/{challengeId}/takePhoto")
    public ResponseEntity<String> saveImgUser(
            Authentication authentication,
            @PathVariable Long challengeId,
            @ModelAttribute MultipartFile image
    ) throws IOException {
        Member member = ((Member) authentication.getPrincipal());
        memberChallengeService.updateMemberChallengeInfo(member ,image, challengeId);

        return ResponseEntity.ok("save user img");
    }

    @PostMapping("/user/{challengeId}/enroll")
    public ResponseEntity<String> attendMemberChallenge(
            Authentication authentication,
            @PathVariable Long challengeId
    ) {
        Member member = ((Member) authentication.getPrincipal());
        memberChallengeService.attendMemberChallenge(member, challengeId);
        return ResponseEntity.ok("attend user challenge");
    }


    @PostMapping( "/api/challenge/{challengeId}/change")
    public ResponseEntity<String> changeMemberChallenge(
            Authentication authentication,
            @PathVariable Long challengeId,
            @RequestBody List<ChangeMemberChallengeDTO> uids
    ) {
        Member member = ((Member) authentication.getPrincipal());
        if (!member.getAuthorities().equals("ADMIN"))
            throw new RuntimeException(String.format("접근할 수 없는 권한입니다."));
        memberChallengeService.changeMemberChallenge(uids,challengeId);
        return ResponseEntity.ok("attend user challenge");
    }
}
