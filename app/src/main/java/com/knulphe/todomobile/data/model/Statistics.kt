package com.knulphe.todomobile.data.model

import com.google.errorprone.annotations.Keep

@Keep
class Statistics {
    var totalDoneTodos: Int = 0;
    var totalTodos: Int = 0;
    var totalWaitingTodos: Int = 0;
    var dailyCompletedTodos: Int = 0;
    var weeklyCompletedTodos: Int = 0;
    var monthlyCompletedTodos: Int = 0;
}