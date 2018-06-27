package com.hieupham.data.source.remote.api.response

import com.google.gson.annotations.Expose

/**
 * Created by hieupham on 6/26/18.
 */
data class InfoResponse(@Expose val height: Long = 0) : Response