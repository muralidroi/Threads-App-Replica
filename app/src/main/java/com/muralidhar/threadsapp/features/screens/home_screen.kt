package com.muralidhar.threadsapp.features.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.muralidhar.threadsapp.R
import com.muralidhar.threadsapp.room.entities.Users
import com.muralidhar.threadsapp.ui.theme.Purple80
import com.muralidhar.threadsapp.utils.CommonDialog
import com.muralidhar.threadsapp.utils.ResultState
import com.muralidhar.threadsapp.utils.noRippleEffect
import com.muralidhar.threadsapp.viewmodel.UserViewModel

@Composable
fun HomeScreen(modifier: Modifier, userViewModel: UserViewModel) {

    LaunchedEffect(key1 = true) {
        userViewModel.fetchUserList()
    }

    val userListState = userViewModel.getUserList.observeAsState()
    var userList = mutableListOf<Users>()

    when (val result = userListState.value) {
        is ResultState.Loading -> {
            // Show loading indicator
            CommonDialog()
        }
        is ResultState.Success -> {
            userList = result.data
        }
        is ResultState.Failure -> {
            val exception = result.msg
            // Show error message
            Text(text = "Error: $exception")
        }
        else -> {
            // Initial state, do nothing
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ConstraintLayout(
            decoupledConstraints(), modifier = Modifier.fillMaxSize()
        ) {
            val scrollState = rememberLazyListState()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo), contentDescription = "",
                    modifier = modifier
                        .layoutId(Home.LOGO)
                        .size(50.dp),
                    tint = Color.Unspecified,
                )

                LazyColumn(
                    state = scrollState
                ) {
                    items(userList) {
                        UserPostEach(
                            modifier = Modifier.padding(top = 10.dp), user = it
                        )
                        Divider(
                            color = Color.LightGray,
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserPostEach(
    modifier: Modifier, user: Users
) {
    ConstraintLayout(
        decoupledConstraints(), modifier = modifier
            .layoutId(Home.POST_LAYOUT)
            .fillMaxWidth()
    ) {
        ProfileIcon(
            modifier = modifier.layoutId(Home.ICON)
        )
        Text(
            text = user.name, modifier = modifier.layoutId(Home.USERNAME), style = TextStyle(
                color = Color.Black, fontWeight = FontWeight.Bold
            )
        )
        ConstraintLayout(
            decoupledConstraints(), modifier = modifier.layoutId(Home.TIME_LAYOUT)
        ) {
            Text(
                text = user.createdAt, style = TextStyle(
                    color = Color.LightGray, fontWeight = FontWeight.Bold
                )
            )
        }
        Icon(
            Icons.Default.MoreVert,
            modifier = modifier
                .layoutId(Home.THREE_DOT)
                .padding(end = 5.dp),
            contentDescription = "",
            tint = Color.Black
        )
        Text(
            text = user.userDescription,
            modifier = Modifier
                .layoutId(Home.TEXT)
                .padding(top = 5.dp),
            style = TextStyle(
                color = Color.Black, fontWeight = FontWeight.Normal
            )
        )

        if (user.imageName.isNotEmpty()) {
            Image(
                modifier = Modifier
                    .layoutId(Home.ICON_ONE)
                    .requiredSize(300.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .padding(top = 10.dp),
                painter = rememberImagePainter(data = user.imageName, builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                }),
                contentDescription = "post"
            )
        }

        ConstraintLayout(
            decoupledConstraints(), modifier = Modifier.layoutId(Home.LIKE_COMMENT_SHARE_LAYOUT)
        ) {
            Icon(
                painterResource(id = R.drawable.love),
                contentDescription = "",
                tint = Color.Black,
                modifier = modifier
                    .layoutId(Home.LIKE)
                    .requiredSize(25.dp)
            )
            Icon(
                painterResource(id = R.drawable.comment_icon_thrd),
                contentDescription = "",
                tint = Color.Black,
                modifier = modifier
                    .layoutId(Home.COMMENT)
                    .requiredSize(20.dp)
            )
            Icon(
                painterResource(id = R.drawable.repost_icon_thread),
                contentDescription = "",
                tint = Color.Black,
                modifier = modifier
                    .layoutId(Home.REPOST)
                    .requiredSize(20.dp)
            )
            Icon(
                painterResource(id = R.drawable.share_icon_thrd),
                contentDescription = "",
                tint = Color.Black,
                modifier = modifier
                    .layoutId(Home.SHARE)
                    .requiredSize(20.dp)
            )
        }
    }
}

@Composable
fun ProfileIcon(
    modifier: Modifier, onClick: () -> Unit = { }
) {
    Box(
        modifier = modifier
            .background(Purple80, CircleShape)
            .size(45.dp)
            .noRippleEffect { onClick() }, contentAlignment = Alignment.BottomEnd
    ) {
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "",
            modifier = modifier.offset(x = 5.dp, y = 5.dp)
        )
    }
}

private fun decoupledConstraints(): ConstraintSet {
    return ConstraintSet {
        val icon = createRefFor(Home.ICON)
        val logo = createRefFor(Home.LOGO)
        val postLayout = createRefFor(Home.POST_LAYOUT)
        val username = createRefFor(Home.USERNAME)
        val timeLayout = createRefFor(Home.TIME_LAYOUT)
        val threeDot = createRefFor(Home.THREE_DOT)
        val userText = createRefFor(Home.TEXT)
        val userImage = createRefFor(Home.ICON_ONE)
        val likeIcon = createRefFor(Home.LIKE)
        val commentIcon = createRefFor(Home.COMMENT)
        val repostIcon = createRefFor(Home.REPOST)
        val shareIcon = createRefFor(Home.SHARE)
        val likeCommentShareIcon = createRefFor(Home.LIKE_COMMENT_SHARE_LAYOUT)

        constrain(logo) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        constrain(postLayout) {
            start.linkTo(parent.start)
            top.linkTo(logo.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(icon) {
            start.linkTo(postLayout.start, 5.dp)
        }
        constrain(username) {
            start.linkTo(icon.end, 5.dp)
            top.linkTo(parent.top)
        }
        constrain(timeLayout) {
            start.linkTo(username.end, 10.dp)
            top.linkTo(parent.top)
        }
        constrain(threeDot) {
            start.linkTo(timeLayout.end, 120.dp)
            top.linkTo(parent.top)
            end.linkTo(parent.end, 5.dp)
        }
        constrain(userText) {
            start.linkTo(icon.end, 10.dp)
            top.linkTo(username.bottom)
        }
        constrain(userImage) {
            top.linkTo(userText.bottom)
            start.linkTo(icon.end, 10.dp)
        }
        constrain(likeCommentShareIcon) {
            top.linkTo(userImage.bottom)
            start.linkTo(icon.end, 10.dp)
        }
        constrain(likeIcon) {
            start.linkTo(parent.start)
            end.linkTo(commentIcon.start)
        }
        constrain(commentIcon) {
            start.linkTo(likeIcon.end, 25.dp)
            end.linkTo(repostIcon.start)
        }
        constrain(repostIcon) {
            start.linkTo(commentIcon.end, 25.dp)
            end.linkTo(shareIcon.start)
        }
        constrain(shareIcon) {
            start.linkTo(repostIcon.end, 25.dp)
        }
    }
}

private enum class Home {
    ICON, USERNAME, TEXT, LIKE, COMMENT, SHARE, REPOST, ICON_ONE, LOGO, POST_LAYOUT, TIME_LAYOUT, LIKE_COMMENT_SHARE_LAYOUT, THREE_DOT
}