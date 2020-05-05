package com.hobbydobby.repository.member

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import javax.persistence.*

/**
 * MEMBER 테이블을 위한 Entity class
 * DB에 넣기전에 해당 객체로 넣음
 * DB의 결과도 해당 객체로 받음
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "member")
class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Int? = null,
        @Column
        var userId : String = "",
        @Column
        var password : String = "",
        @Column
        @CreatedDate
        var registerDate : LocalDateTime? = null
)

/**
 * MEMBER 테이블에 접근하기 위한 인터페이스
 */
interface MemberRepository : JpaRepository<Member, Int> {
}