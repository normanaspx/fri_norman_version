package com.normanaspx.norman_fri.data.models

import androidx.room.Embedded
import androidx.room.Relation
import com.normanaspx.norman_fri.data.Photo


/**
Creada por Norman el 3/15/2021
 **/
data class PhotoWithDetails (

    @Embedded
    var photo: PhotoEntity,
    @Relation(
            parentColumn = "id",
            entityColumn = "idUrls"
        )
    var urls: UrlsEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idUser"
    )
    var user: UserEntity

)