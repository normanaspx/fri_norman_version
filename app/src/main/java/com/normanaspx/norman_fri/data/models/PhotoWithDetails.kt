package com.normanaspx.norman_fri.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.normanaspx.norman_fri.data.Photo


/**
Creada por Norman el 3/15/2021
 **/
data class PhotoWithDetails (

    @Embedded
    val photo: PhotoEntity,
    @Relation(
            parentColumn = "id",
            entityColumn = "id_fkimage",
        )
    val urls: UrlsEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_fkUser",
    )
    val user: UserEntity?

)