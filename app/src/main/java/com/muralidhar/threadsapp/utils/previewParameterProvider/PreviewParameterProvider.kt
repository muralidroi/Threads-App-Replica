package com.muralidhar.threadsapp.utils.previewParameterProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.muralidhar.threadsapp.room.entities.Users
import java.util.Date

class UserPreviewParameterProvider : PreviewParameterProvider<Users> {
    override val values = sequenceOf(
        Users(
            name = "Muralidhar",
            userDescription = "iamMurali",
            followersCount = 170,
            email = "email",
            bioInfo = "yad bhavam tad bhavati",
            createdAt = Date().time.toString(),
            imageName = ""
        )
    )
}