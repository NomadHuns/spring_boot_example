package com.example.spring_boot_jpa_example.module.users.roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
    데이터 베이스의 테이블을 자바 클래스로 구현합니다.
    클래스의 필드명은 테이블의 칼럼명입니다.
 */

@EntityListeners(AuditingEntityListener.class) // 서버 시간을 사용하여 자동으로 입력하도록 하기 위해 사용되는 어노테이션.
@NoArgsConstructor // 필드 값을 입력하지 않은 초기화메서드 생성 어노테이션.
@AllArgsConstructor // 모든 필드값을 입력하는 초기화메서드 생성 어노테이션.
@Builder // 빌더패턴을 사용할 수 있도록 하는 어노테이션.
@Getter // 필드의 Getter 메서드를 자동으로 생성해주는 어노테이션.
@Entity // 데이터베이스 테이블을 자바 클래스로 구현한다는 의미의 어노테이션.
public class Roles {

    @Id // 고유 값을 의미. 주로 PK로 사용됨. @Entity가 붇은 클래스는 반드시 하나의 @Id를 가진 필드가 필요합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 새로운 로우 생성시에 자동으로 입력되는 전략을 선택하는 어노테이션.
    private Long id; // 기본적으로 필드값은 테이블의 칼럼명을 의미함.

    @Column(nullable = false, unique = true, name = "role") // 칼럼의 제약 조건을 설정하는 어노테이션. name은 만약 클래스의 필드값과 테이블의 칼럼명을 일치하여 사용하지 않을 경우 칼럼명을 직접 정의할 수 있음.
    private String name;

    @CreatedDate // 새로운 로우 생성시에 자동으로 입력되도록 하는 어노테이션. 수정시에는 자동으로 입력되지 않기때문에 주의 필요함.
    private LocalDateTime createDate;

    @LastModifiedDate // 해당 로우가 수정될 때 서버 시간을 자동으로 입력해주는 어노테이션.
    private LocalDateTime updateDate;

    private LocalDateTime deleteDate;
}
