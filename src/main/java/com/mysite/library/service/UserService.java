package com.mysite.library.service;

import com.mysite.library.dto.UserDTO;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultAuthenticationEventPublisher authenticationEventPublisher;

    public void save(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //repository의 save 메소드 호출해야 한다 (조건: entity 객체를 넘겨줘야한다)
        //1. dto -> entity 변환
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        //2. repository의 save 메소드 호출
        //repository에서 쓰이는 save 메소드는 jp가 제공해주는 메소드이다. 그래서 여기서 'save' 메소드 이름은 꼭 지켜줘야한다
        userRepository.save(userEntity);
    }

    public UserDTO login(UserDTO userDTO) {
        //1. 회원이 입력한 이메일을 DB에서 조회함
        //2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        Optional<UserEntity> byUserEmail = userRepository.findByEmail(userDTO.getEmail());
        if (byUserEmail.isPresent()) {
            //조회 결과가 있다 (= 해당 이메일이 DB에 존재한다)

            //Optional로 감싸진 객체를 벗겨내는 작업 -> 안의 객체를 가져올수 있음
            UserEntity userEntity = byUserEmail.get();
            //벗겨낸 객체로 비밀번호 일치 여부를 확인하기
            if (userEntity.getPassword().equals(userDTO.getPassword())) {
                //비밀번호가 일치한다 -> 로그인이 가능하다
                //entity -> dto 변환 후 리턴하자
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            } else {
                //비밀번호가 불일치한다
                return null;
            }
        } else {
            //조회 결과가 없다
            return null;
        }
    }

    public UserDTO update(UserDTO userDTO) {
        Optional<UserEntity> byUserEmail = userRepository.findByEmail(userDTO.getEmail());
        if (byUserEmail.isPresent()) {
            UserEntity userEntity = byUserEmail.get();

            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            if (userDTO.getName() != null) {
                userEntity.setName(userDTO.getName());
            }
            if (userDTO.getPhone() != null) {
                userEntity.setPhone(userDTO.getPhone());
            }
            if (userDTO.getAddress() != null) {
                userEntity.setAddress(userDTO.getAddress());
            }

            userRepository.save(userEntity);

            return UserDTO.toUserDTO(userEntity);
        } else {
            return null;
        }
    }

    public UserDTO findById(Long id) {

        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(UserDTO::toUserDTO).orElse(null);
    }

    public UserDTO findByName(String name) {

        Optional<UserEntity> userEntity = userRepository.findByName(name);
        return userEntity.map(UserDTO::toUserDTO).orElse(null);
    }
}

