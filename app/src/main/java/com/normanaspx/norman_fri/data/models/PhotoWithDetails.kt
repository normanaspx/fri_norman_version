package com.normanaspx.norman_fri.data.models

import androidx.room.Embedded
import androidx.room.Relation


/**
Creada por Norman el 3/15/2021
 **/
data class PhotoWithDetails (

    @Embedded
        var photo: Photo,
    @Relation(
            parentColumn = "id",
            entityColumn = "id_student"
        )
        var photos: List<Photo>,
)