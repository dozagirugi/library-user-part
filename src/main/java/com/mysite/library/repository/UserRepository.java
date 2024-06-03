package com.mysite.library.repository;

import com.mysite.library.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

//Jparepoxitory<어떤 entity 클래스를 받을 것인지, 그 클래스의 pk의 타입>
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //Optional: null을 방지한다
    //findBy{조회하고자하는 필드 이름}을 적는다. 대소문자는 구분하지 않음.
    Optional<UserEntity> findById(Long idx);
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByEmail(String email);

}
