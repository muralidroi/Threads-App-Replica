package com.muralidhar.threadsapp.features.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.muralidhar.threadsapp.R
import com.muralidhar.threadsapp.room.entities.Users

@Composable
fun ProfileDescriptionLayout(
    user: Users, modifier: Modifier
) {
    ConstraintLayout(
        decoupledProfileDescription(),
        modifier
            .padding(start = 12.dp)
            .fillMaxWidth()
    ) {

        val nameStyle = TextStyle(
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif,
            letterSpacing = 0.25.sp
        )

        val userNameStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace
        )

        val bioStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace
        )

        Text(
            text = user.name,
            modifier = modifier.layoutId(ProfileDescription.NAME),
            style = nameStyle
        )

        Text(
            text = user.userDescription,
            modifier = modifier.layoutId(ProfileDescription.USERNAME),
            style = userNameStyle
        )

        Text(
            text = user.bioInfo,
            modifier = modifier.layoutId(ProfileDescription.BIO),
            style = bioStyle
        )

        Image(
            painter = painterResource(R.drawable.murali),
            contentDescription = "Round Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .requiredSize(80.dp)
                .clip(CircleShape)
                .border(0.1.dp, Color.Red, CircleShape)
                .layoutId(ProfileDescription.IMAGE)
        )
    }
}

fun decoupledProfileDescription(): ConstraintSet {
    return ConstraintSet {
        val name = createRefFor(ProfileDescription.NAME)
        val profileImage = createRefFor(ProfileDescription.IMAGE)
        val userName = createRefFor(ProfileDescription.USERNAME)
        val bio = createRefFor(ProfileDescription.BIO)

        val imageGuideline = createGuidelineFromAbsoluteLeft(0.7f)
        val topGuideLine = createGuidelineFromTop(0.4f)

        constrain(profileImage) {
            top.linkTo(topGuideLine)
            start.linkTo(imageGuideline)
            end.linkTo(parent.end, 5.dp)
            bottom.linkTo(parent.bottom)
        }

        constrain(name) {
            top.linkTo(topGuideLine, 20.dp)
            start.linkTo(parent.start)
            bottom.linkTo(userName.top)
        }

        constrain(userName) {
            start.linkTo(parent.start)
            top.linkTo(name.bottom, 8.dp)
            bottom.linkTo(bio.top)
        }

        constrain(bio) {
            start.linkTo(parent.start)
            top.linkTo(userName.bottom, 18.dp)
            bottom.linkTo(parent.bottom)
        }

    }
}

enum class ProfileDescription {
    NAME, USERNAME, BIO, IMAGE
}