package com.hobbydobby.domain.hobby

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

/**
 * HOBBY 테이블을 위한 Entity 클래스
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "hobby")
class Hobby(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Int? = null,
        @Column
        var pId : Int? = null,
        @Column
        var name : String = "",
        @Column
        var registerCount : Int = 0,
        @Column
        var boardOn : Boolean = false,
        @Column
        @CreatedDate
        var registerDate : LocalDateTime? = null,
        @Column
        @LastModifiedDate
        var updateDate : LocalDateTime? = null
)