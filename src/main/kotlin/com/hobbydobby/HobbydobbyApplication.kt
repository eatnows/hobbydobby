package com.hobbydobby

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing //Jpa Auditing을 활성화 시키기 위한 어노테이션
class HobbydobbyApplication

fun main(args: Array<String>) {
    runApplication<HobbydobbyApplication>(*args)
}
