package com.bignerdranch.travelcommunity.adapters

import org.junit.Test

import org.junit.Assert.*

/**
 * @author zhongxinyu
 * @date 2020/5/16
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 */
class CovertersTest {

    @Test
    fun getFristUrlfromString() {

        val str = "[\"group1/M00/00/00/L2ZgWV6_GhiEBbjzAAAAAMUjNvA722.jpg\",\"group1/M00/00/00/L2ZgWV6_GhiEBbjzAAAAAMUjNvA722.jpg\"]"

        print(Coverters.getUrlList(str))
    }
}