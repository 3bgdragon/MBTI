package com.example.security.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Spring Security 필수 메소드 구현
     *
     * @param email 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */

    @Override
    public UserInfo loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }


    /**
     * 회원정보 저장
     *
     * @param infoDto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
     public Long save(UserInfoDto infoDto) {
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         infoDto.setPassword(encoder.encode(infoDto.getPassword()));

         return userRepository.save(UserInfo.builder()
                 .email(infoDto.getEmail())
                 .auth(infoDto.getAuth())
                 .mbti(infoDto.getMbti())
                 .password(infoDto.getPassword()).build()).getCode();
     }

    public String getUsermbti() {
         //현재 세션에 로그인한 사용자 정보를 가져온다
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String test = authentication.getName();
        Optional<UserInfo> userInfo = userRepository.findByEmail(authentication.getName());
        return String.valueOf(userInfo.get().getMbti());
    }

    public Optional<UserInfo> findUser(String email) {
         return userRepository.findByEmail(email);
    }
}
