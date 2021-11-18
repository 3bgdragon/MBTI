package com.example.security.domain.user;

import com.example.security.core.BaseJpaQueryDSLRepository;
import com.example.security.domain.BaseService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService extends BaseService<UserInfo, Long> implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

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

    public Page<UserInfo> getAllUser(int page, String filter) {
        Pageable pageable = PageRequest.of(page - 1,10);
        BooleanBuilder builder = new BooleanBuilder();

        if(isNotEmpty(filter)) {
            builder.and(qUserInfo.email.contains(filter));
            builder.or(qUserInfo.mbti.contains(filter));
        }

        QueryResults results = select().from(qUserInfo)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qUserInfo.code.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

}
