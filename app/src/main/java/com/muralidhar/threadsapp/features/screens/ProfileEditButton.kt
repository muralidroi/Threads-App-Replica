package com.muralidhar.threadsapp.features.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ProfileEditButton(modifier: Modifier) {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val profileCenterPosition = createGuidelineFromTop(0.25f)
        val (editProfile, shareProfile) = createRefs()

        val buttonTextStyle = TextStyle(
            fontSize = 16.sp, fontWeight = FontWeight.SemiBold
        )

        TextButton(onClick = { },
            border = BorderStroke(1.dp, Color.LightGray),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, contentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(vertical = 0.dp),
            modifier = modifier
                .fillMaxWidth(0.45f)
                .constrainAs(editProfile) {
                    top.linkTo(profileCenterPosition)
                    start.linkTo(parent.start)
                    end.linkTo(shareProfile.start)
                }) {
            Text(
                text = "Edit profile", style = buttonTextStyle
            )
        }

        TextButton(onClick = {},
            border = BorderStroke(1.dp, Color.LightGray),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, contentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(vertical = 0.dp),
            modifier = modifier
                .fillMaxWidth(0.45f)
                .constrainAs(shareProfile) {
                    top.linkTo(profileCenterPosition)
                    start.linkTo(editProfile.end)
                    end.linkTo(parent.end)
                }) {
            Text(
                text = "Share profile", style = buttonTextStyle
            )
        }

        createHorizontalChain(
            editProfile, shareProfile, chainStyle = ChainStyle.Spread
        )
    }
}