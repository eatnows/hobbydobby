package com.hobbydobby.repository.member

import com.hobbydobby.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

/**
 * MEMBER 테이블에 접근하기 위한 인터페이스
 */
interface MemberRepository : JpaRepository<Member, Int> {
    /**
     * Email로 대상 찾기
     */
    fun findByEmail(email : String) : Member?

    /**
     * Nickname으로 대상 찾기
     */
    fun findByNickname(nickname : String) : Member?
}