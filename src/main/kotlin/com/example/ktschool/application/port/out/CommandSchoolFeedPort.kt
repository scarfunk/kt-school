package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity
import com.example.ktschool.adapter.out.persistence.entity.SchoolFeedEntity

interface CommandSchoolFeedPort {
    fun postFeed(feed: SchoolFeedEntity, school: SchoolEntity): SchoolFeedEntity

    fun updateFeed(feed: SchoolFeedEntity, school: SchoolEntity): SchoolFeedEntity

    fun deleteFeed(feed: SchoolFeedEntity, school: SchoolEntity): SchoolFeedEntity
}
