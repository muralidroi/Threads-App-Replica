package com.muralidhar.threadsapp.features.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.muralidhar.threadsapp.R

@Composable
fun ProfileScreenTopBar(modifier: Modifier) {
    ConstraintLayout(
        decoupledConstraintsForTopBar(), modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp, end = 5.dp, top = 20.dp, bottom = 20.dp
            )
    ) {

        Icon(
            painter = painterResource(id = R.drawable.lock),
            contentDescription = "",
            modifier = modifier
                .layoutId(HomeItem.LOCK_ICON)
                .size(30.dp),
            tint = Color.Unspecified
        )

        Icon(
            painter = painterResource(id = R.drawable.instagram_logo),
            contentDescription = "",
            modifier = modifier
                .layoutId(HomeItem.INSTA_ICON)
                .size(30.dp),
            tint = Color.Unspecified
        )
        Icon(
            painter = painterResource(id = R.drawable.setting_gear),
            contentDescription = "",
            modifier = modifier
                .layoutId(HomeItem.SETTING_ICON)
                .size(30.dp),
            tint = Color.Unspecified
        )
    }
}

private fun decoupledConstraintsForTopBar(): ConstraintSet {
    return ConstraintSet {
        val lock = createRefFor(HomeItem.LOCK_ICON)
        val rightIcon = createGuidelineFromStart(0.7f)
        val insta = createRefFor(HomeItem.INSTA_ICON)
        val setting = createRefFor(HomeItem.SETTING_ICON)

        constrain(lock) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(insta) {
            start.linkTo(rightIcon)
            end.linkTo(setting.start)
            top.linkTo(parent.top)
        }
        constrain(setting) {
            start.linkTo(insta.end, 20.dp)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    }
}

private enum class HomeItem {
    LOCK_ICON, INSTA_ICON, SETTING_ICON
}