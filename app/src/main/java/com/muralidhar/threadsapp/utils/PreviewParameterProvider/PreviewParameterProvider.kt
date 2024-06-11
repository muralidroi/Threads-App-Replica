package com.muralidhar.threadsapp.utils.PreviewParameterProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.muralidhar.threadsapp.room.entities.Users
import java.util.Date

class UserPreviewParameterProvider : PreviewParameterProvider<Users> {
    override val values = sequenceOf(
        Users(
            name = "Muralidhar",
            userName = "iamMurali",
            followersCount = 170,
            email = "email",
            bioInfo = "yad bhavam tad bhavati",
            createdAt = Date().time,
            imageName = ""
        )
    )
}