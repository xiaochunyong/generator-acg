package me.ely.domain.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "trade")
data class Trade(
        @Id val tradeNo: String,
        val tradeBatchNo: String,
        val orderNo: String,
        val amount: Int,
        val openid: String,
        val couponStockId: String,
        val couponId: String,
        val couponState: String,
        val status: String,
        val createDt: Date,
        val expireDt: Date,
        val updateDt: Date
)