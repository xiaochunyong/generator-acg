package me.ely.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SchedulingService {

    val logger = LoggerFactory.getLogger(this.javaClass)

//    @Scheduled(cron = "0 0/5 * * * ?")
//    fun task1() {
//         logger.info("每5分钟执行一次")
//    }
//
//    @Scheduled(cron = "0 0 2 * * ?")
//    fun task2() {
//         logger.info("凌晨2点执行一次")
//    }
}