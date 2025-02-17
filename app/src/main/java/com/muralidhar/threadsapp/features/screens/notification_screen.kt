package com.muralidhar.threadsapp.features.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.muralidhar.threadsapp.R
import com.muralidhar.threadsapp.room.entities.Users
import com.muralidhar.threadsapp.ui.theme.Purple40
import com.muralidhar.threadsapp.ui.theme.Purple80
import com.muralidhar.threadsapp.ui.theme.PurpleGrey80
import com.muralidhar.threadsapp.utils.CommonDialog
import com.muralidhar.threadsapp.utils.ResultState
import com.muralidhar.threadsapp.viewmodel.UserViewModel

@Composable
fun NotificationScreen(userViewModel:  UserViewModel, modifier: Modifier) {

    val chips = listOf("All", "Replies", "Mentions", "Verified")
    var selected by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        userViewModel.fetchUserList()
    }

    val userListState = userViewModel.getUserList.observeAsState()
    var userList = mutableListOf<Users>()

    when (val result = userListState.value) {
        is ResultState.Loading -> {
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
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ConstraintLayout(decoupledConstraints(), modifier = modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.activity),
                modifier = modifier.layoutId(Notification.ACTIVITY_TEXT),
                style = TextStyle(
                    color = Color.Black, fontSize = 35.sp, fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = modifier
                    .padding(top = 15.dp)
                    .horizontalScroll(scrollState)
                    .fillMaxWidth()
                    .layoutId(Notification.CHIP_SELECTION)
            ) {
                chips.forEachIndexed { index, title ->
                    NotificationFilterChip(title = title,
                        selected = index == selected,
                        index = index,
                        modifier = modifier,
                        onValueChange = {
                            selected = it
                        })
                }
            }

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .layoutId(Notification.RECYCLER_VIEW)
            ) {
                if (selected == 0) item {
                    AllSection(modifier = modifier)
                    //RepliesSection()
                    AllSection(modifier = modifier)
                    //RepliesSection(text = "Mentioned you")
                }
                if (selected == 1) items(userList) {
                    RepliesSection(user = it, modifier = modifier)
                }
                if (selected == 2) items(5) {
                    /*RepliesSection(
                        text = "Mentioned you"
                    )*/
                }
                if (selected == 3) item(5) {
                    //SearchProfileLayout(listOf())
                }
            }
            //}
        }

    }
}

@Composable
fun RepliesSection(
    user: Users, modifier: Modifier, text: String = "@threadsapp is built by using jetpack comp...."
) {

    ConstraintLayout(
        decoupledRepliesSection(), modifier = modifier.padding(vertical = 10.dp)
    ) {
        NotificationProfileIcon(size = 30.dp, modifier = modifier.layoutId(Replies.ICON))
        ConstraintLayout(
            decoupledRepliesSection(), modifier = modifier.layoutId(Replies.PARENT_ROW)
        ) {
            Text(
                text = user.name,
                modifier = modifier.layoutId(Replies.USER_NAME),
                style = TextStyle(
                    color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.W600
                )
            )
            Text(text = "3 d", modifier = modifier.layoutId(Replies.TIME))
            Text(
                text = text, modifier = modifier.layoutId(Replies.TAG), style = TextStyle(
                    color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.W400
                )
            )
            Text(
                text = "I thought it's React Native",
                modifier = modifier.layoutId(Replies.REPLIES),
                style = TextStyle(
                    color = Color.Black, fontSize = 19.sp, fontWeight = FontWeight.W400
                )
            )
            Divider(
                modifier = modifier
                    .layoutId(Replies.LINE)
                    .fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.LightGray
            )
        }
    }

}

@Composable
fun AllSection(
    modifier: Modifier
) {

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.W600
            )
        ) {
            append("muralidhar ")
        }
        withStyle(
            style = SpanStyle(
                color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.W400
            )
        ) {
            append("and 406 others")
        }
    }

    ConstraintLayout(decoupledAllSection(), modifier = modifier.padding(vertical = 10.dp)) {
        NotificationProfileIcon(
            modifier = modifier.layoutId(All.ICON_ONE), size = 15.dp
        )
        NotificationProfileIcon(
            modifier = modifier.layoutId(All.ICON_TWO), size = 20.dp, color = Purple80
        )
        NotificationProfileIcon(
            modifier = modifier.layoutId(All.ICON_THREE), size = 15.dp, color = PurpleGrey80
        )
        NotificationProfileIcon(
            modifier = modifier.layoutId(All.ICON_FOUR), size = 20.dp
        )
        ConstraintLayout(
            decoupledAllSection(), modifier = modifier
                .padding(5.dp)
                .layoutId(All.PARENT_ROW)
        ) {
            Text(text = annotatedString, modifier = modifier.layoutId(All.TITLE))
            Text(
                text = "Followed You", style = TextStyle(
                    fontSize = 18.sp, color = Color.Gray
                ), modifier = Modifier.layoutId(All.FOLLOWED)
            )
            Text(
                text = "2 h", style = TextStyle(
                    fontSize = 18.sp, color = Color.Gray
                ), modifier = modifier.layoutId(All.TIME)
            )
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .layoutId(All.LINE),
                thickness = 0.5.dp,
                color = Color.LightGray
            )
        }
    }
}


@Composable
fun NotificationProfileIcon(
    modifier: Modifier, size: Dp = 20.dp, color: Color = Purple40
) {
    Box(
        modifier = modifier
            .background(color, CircleShape)
            .size(size)
    )
}


@Composable
fun NotificationFilterChip(
    title: String, selected: Boolean, index: Int, modifier: Modifier, onValueChange: (Int) -> Unit
) {

    Button(
        onClick = { onValueChange(index) },
        shape = RoundedCornerShape(10.dp),
        border = if (!selected) BorderStroke(1.dp, Color.LightGray) else null,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color.Black else Color.White,
            contentColor = if (selected) Color.White else Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        modifier = modifier
            .padding(end = 10.dp)
            .width(120.dp),
        contentPadding = PaddingValues(vertical = 0.dp)
    ) {
        Text(
            text = title, style = TextStyle(
                fontSize = 18.sp, fontWeight = FontWeight.W600
            )
        )
    }
}

private fun decoupledConstraints(): ConstraintSet {
    return ConstraintSet {
        val activityText = createRefFor(Notification.ACTIVITY_TEXT)
        val chipSelection = createRefFor(Notification.CHIP_SELECTION)
        val recyclerview = createRefFor(Notification.RECYCLER_VIEW)


        constrain(activityText) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(chipSelection) {
            top.linkTo(activityText.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(recyclerview) {
            start.linkTo(parent.start)
            end.linkTo(parent.end, 10.dp)
            top.linkTo(chipSelection.bottom, 20.dp)
        }


    }
}

private fun decoupledAllSection(): ConstraintSet {
    return ConstraintSet {

        val iconOne = createRefFor(All.ICON_ONE)
        val iconTwo = createRefFor(All.ICON_TWO)
        val iconThree = createRefFor(All.ICON_THREE)
        val iconFour = createRefFor(All.ICON_FOUR)
        val parentRow = createRefFor(All.PARENT_ROW)
        val title = createRefFor(All.TITLE)
        val time = createRefFor(All.TIME)
        val followedBy = createRefFor(All.FOLLOWED)
        val line = createRefFor(All.LINE)

        constrain(iconOne) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(iconTwo) {
            start.linkTo(iconOne.end, 5.dp)
            top.linkTo(parent.top)
        }
        constrain(iconThree) {
            start.linkTo(parent.start)
            top.linkTo(iconOne.bottom, 10.dp)
        }
        constrain(iconFour) {
            start.linkTo(iconThree.end, 5.dp)
            top.linkTo(iconTwo.bottom, 5.dp)
        }
        constrain(parentRow) {
            start.linkTo(iconTwo.end, 10.dp)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
        constrain(title) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(time) {
            start.linkTo(title.end, 10.dp)
            top.linkTo(parent.top)

        }
        constrain(followedBy) {
            top.linkTo(title.bottom)
            start.linkTo(parent.start)
        }
        constrain(line) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(followedBy.bottom, 15.dp)
        }

    }
}

private fun decoupledRepliesSection(): ConstraintSet {
    return ConstraintSet {
        val icon = createRefFor(Replies.ICON)
        val username = createRefFor(Replies.USER_NAME)
        val tag = createRefFor(Replies.TAG)
        val replies = createRefFor(Replies.REPLIES)
        val time = createRefFor(Replies.TIME)
        val parentRow = createRefFor(Replies.PARENT_ROW)
        val line = createRefFor(Replies.LINE)

        constrain(icon) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            //end.linkTo(parentRow.start)
        }
        constrain(parentRow) {
            start.linkTo(icon.end, 10.dp)
            top.linkTo(parent.top)
            // end.linkTo(parent.end)
        }

        constrain(username) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(time) {
            start.linkTo(username.end, 5.dp)
            top.linkTo(parent.top)
        }
        constrain(tag) {
            start.linkTo(parent.start)
            top.linkTo(username.bottom)
        }
        constrain(replies) {
            start.linkTo(parent.start)
            top.linkTo(tag.bottom)
        }
        constrain(line) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(replies.bottom, 10.dp)
        }
    }

}

private enum class Notification {
    ACTIVITY_TEXT, CHIP_SELECTION, RECYCLER_VIEW
}

private enum class All {
    ICON_ONE, ICON_TWO, ICON_THREE, ICON_FOUR, PARENT_ROW, TITLE, TIME, FOLLOWED, LINE
}

private enum class Replies {
    ICON, USER_NAME, TAG, REPLIES, TIME, PARENT_ROW, LINE
}
