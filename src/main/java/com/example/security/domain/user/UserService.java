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

import java.time.LocalDateTime;
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
    public Long registUser(UserInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));
        return userRepository.save(UserInfo.builder()
                .email(infoDto.getEmail())
                .auth(infoDto.getAuth())
                .mbti(infoDto.getMbti())
                .status("normal")
                .password(infoDto.getPassword()).build()).getCode();
    }

    public Long modifyUser(UserInfoDto infoDto) {
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         infoDto.setPassword(encoder.encode(infoDto.getPassword()));

         //타임리프에서 lastLoginDt를 전달할떄 오류가나서 userInfo 객체를 만들어서 현재수정하려는 회원의 정보를 가져온다
        Optional<UserInfo> userInfo = userRepository.findByEmail(infoDto.getEmail());

             return userRepository.save(UserInfo.builder()
                     .code(Long.valueOf(infoDto.getCode()))
                     .email(infoDto.getEmail())
                     .auth(infoDto.getAuth())
                     .mbti(infoDto.getMbti())
                     .status(infoDto.getStatus())
                     .lastLoginDt(userInfo.get().getLastLoginDt())
                     .password(infoDto.getPassword()).build()).getCode();
     }

    public String getUsermbti() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserInfo> userInfo = userRepository.findByEmail(authentication.getName());

        return String.valueOf(userInfo.get().getMbti());
    }

    public Optional<UserInfo> findUserByEmail(String email) {
         return userRepository.findByEmail(email);
    }

    public Optional<UserInfo> findUser(String code) {
        return userRepository.findById(Long.valueOf(code));
    }

    public Page<UserInfo> getAllUser(int page, String filter) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        BooleanBuilder builder = new BooleanBuilder();

        if(isNotEmpty(filter)) {
            if(filter.equals(" ")){
                filter = null;
            }
        }

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

    public void makeLastLogin() {
        //현재 세션에 로그인한 사용자 정보를 가져온다
        //현재 활동한 시간을 기록한다
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserInfo> userInfo = userRepository.findByEmail(authentication.getName());
        userRepository.save(UserInfo.builder()
                .code(Long.valueOf(userInfo.get().getCode()))
                .email(userInfo.get().getEmail())
                .auth(userInfo.get().getAuth())
                .mbti(userInfo.get().getMbti())
                .password(userInfo.get().getPassword())
                .status(userInfo.get().getStatus())
                .lastLoginDt(LocalDateTime.now())
                .build());
    }
}
