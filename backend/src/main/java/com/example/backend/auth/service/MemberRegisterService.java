package com.example.backend.auth.service;

import com.example.backend.auth.dto.RegisterSuccessDto;
import com.example.backend.auth.exception.DuplicateOAuth2ProviderException;
import com.example.backend.entity.member.Member;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.service.MemberCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRegisterService {
    private final MemberRepository memberRepository;
    private final MemberCreateService memberCreateService;
    
    @Transactional
    public RegisterSuccessDto getOrCreateMember(UserProfileDto userProfileDto, OAuth2Provider oAuth2Provider) {
        Optional<Member> memberOptional = memberRepository.findByEmail(userProfileDto.getEmail());
        Member member;

        // 이미 가입된 이메일이 존재하는 경우
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
            // 이미 가입된 이메일의 소셜 사이트와 현재 가입요청한 소셜 사이트가 다를 경우 - 로그인 실패
            if (isDuplicateOAuth2Provider(member, oAuth2Provider)) {
                throw new DuplicateOAuth2ProviderException(member.getOAuth2Provider());
            }
            // 이미 가입된 이메일의 소셜 사이트와 현재 가입요청한 소셜 사이트가 같을 경우 - 로그인 성공
            return new RegisterSuccessDto(member, false);
        }
        // 처음 가입한 경우 - 로그인 성공
        else {
            member = memberCreateService.createUser(userProfileDto, oAuth2Provider);
            return new RegisterSuccessDto(member, true);
        }
    }
    
    private boolean isDuplicateOAuth2Provider(Member member, OAuth2Provider oAuth2Provider) {
        return !member.getOAuth2Provider().equals(oAuth2Provider);
    }
}
