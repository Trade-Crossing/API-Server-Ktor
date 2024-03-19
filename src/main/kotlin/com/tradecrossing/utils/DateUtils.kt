package com.tradecrossing.utils

import java.time.LocalDateTime
import java.util.*

fun LocalDateTime.toDate(): Date =
  Date.from(this.atZone(TimeZone.getDefault().toZoneId()).toInstant())

fun Date.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(this.toInstant(), TimeZone.getDefault().toZoneId())


