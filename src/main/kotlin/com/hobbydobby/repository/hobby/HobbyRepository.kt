package com.hobbydobby.repository.hobby

import com.hobbydobby.domain.hobby.Hobby
import org.springframework.data.jpa.repository.JpaRepository

/**
 * HOBBY 테이블에 접근하기 위한 인터페이스
 */
interface HobbyRepository : JpaRepository<Hobby, Int> {
    fun findByNameEquals(name : String) : Hobby?
}