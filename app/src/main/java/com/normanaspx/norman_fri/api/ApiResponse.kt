package com.normanaspx.norman_fri.api

import com.normanaspx.norman_fri.data.Photo
import com.normanaspx.norman_fri.data.models.PhotoEntity


/**
Creada por Norman el 3/13/2021
 **/
data class ApiResponse (
        val results: List<Photo>,
        val db_results: List<PhotoEntity>
    )