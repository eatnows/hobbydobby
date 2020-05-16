package com.hobbydobby.domain.member

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
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
        var email : String = "",
        @Column
        var password : String = "",
        @Column
        var salt : String = "",
        @Column
        var name : String = "",
        @Column
        var nickname : String = "",
        @Column
        var birthday : String = "",
        @Column
        var phone : String = "",
        @Column
        var address : String = "",
        @Column
        var introduce : String = "",
        @Column
        @CreatedDate
        var registerDate : LocalDateTime? = null,
        @Column
        @LastModifiedDate
        var updateDate : LocalDateTime? = null
)