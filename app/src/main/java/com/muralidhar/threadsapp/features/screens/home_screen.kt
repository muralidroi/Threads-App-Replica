package com.muralidhar.threadsapp.features.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muralidhar.threadsapp.R
import com.muralidhar.threadsapp.room.entities.Users
import com.muralidhar.threadsapp.ui.theme.Purple80
import com.muralidhar.threadsapp.utils.noRippleEffect
import java.util.Date

@Composable
fun HomeScreen(modifier: Modifier = Modifier, userList: MutableList<Users>) {

    val context = LocalContext.current

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ConstraintLayout(
            decoupledConstraints(), modifier = modifier.fillMaxSize()
        ) {
            val scrollState = rememberLazyListState()

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
                        modifier = modifier
                            .layoutId(Home.PARENT_ROW)
                            .padding(top = 50.dp),
                        user = it,
                        context = context
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserPostEach(
    modifier: Modifier = Modifier, user: Users, context: Context
) {
    ConstraintLayout(
        decoupledConstraints(),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        ProfileIcon(modifier = Modifier.layoutId(Home.ICON))
        ConstraintLayout(
            decoupledConstraints(), modifier = Modifier
                .layoutId(Home.POST_LAYOUT)
                .fillMaxWidth()

        ) {
            Text(
                text = user.userName,
                modifier = Modifier.layoutId(Home.USERNAME),
                style = TextStyle(
                    color = Color.Black, fontWeight = FontWeight.Bold
                )
            )
            ConstraintLayout(
                decoupledConstraints(),
                modifier = Modifier
                    .layoutId(Home.TIME_LAYOUT)
                    .padding(start = 200.dp, end = 5.dp)
            ) {
                val timeSincePosted = (Date().time - user.createdAt)

                val hrs = (timeSincePosted / (1000 * 60 * 60))
                Text(
                    text = "$hrs h", modifier = Modifier.layoutId(Home.TIME), style = TextStyle(
                        color = Color.Black, fontWeight = FontWeight.Bold
                    )
                )
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.layoutId(Home.THREE_DOTS)
                )
            }
            Text(
                text = user.name,
                modifier = Modifier
                    .layoutId(Home.TEXT)
                    .padding(top = 5.dp),
                style = TextStyle(
                    color = Color.Black, fontWeight = FontWeight.Normal
                )
            )

            val message = remember {
                mutableStateOf("")
            }
            // on below line creating variable for firebase
            // database and database reference.
            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.getReference(user.imageName)

            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // this method is call to get the realtime
                    // updates in the data.
                    // this method is called when the data is
                    // changed in our Firebase console.
                    // below line is for getting the data from
                    // snapshot of our database.
                    val map = snapshot.value

                    if (user.imageName == snapshot.key) {
                        message.value = map.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // calling on cancelled method when we receive
                    Toast.makeText(context, "Fail to get data.", Toast.LENGTH_SHORT).show()
                }
            })

            if (message.value.isNotEmpty()) {
                Image(
                    modifier = Modifier
                        .layoutId(Home.ICON_ONE)
                        .requiredSize(300.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .padding(top = 10.dp),
                    painter = rememberImagePainter(data = message.value, builder = {
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
                    modifier = Modifier
                        .layoutId(Home.LIKE)
                        .requiredSize(25.dp)
                )
                Icon(
                    painterResource(id = R.drawable.comment_icon_thrd),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier
                        .layoutId(Home.COMMENT)
                        .requiredSize(20.dp)
                )
                Icon(
                    painterResource(id = R.drawable.repost_icon_thread),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier
                        .layoutId(Home.REPOST)
                        .requiredSize(20.dp)
                )
                Icon(
                    painterResource(id = R.drawable.share_icon_thrd),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier
                        .layoutId(Home.SHARE)
                        .requiredSize(20.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier, onClick: () -> Unit = { }
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
            modifier = Modifier.offset(x = 5.dp, y = 5.dp)
        )
    }
}

private fun decoupledConstraints(): ConstraintSet {
    return ConstraintSet {
        val icon = createRefFor(Home.ICON)
        val logo = createRefFor(Home.LOGO)
        val parentRow = createRefFor(Home.PARENT_ROW)
        val postLayout = createRefFor(Home.POST_LAYOUT)
        val username = createRefFor(Home.USERNAME)
        val timeLayout = createRefFor(Home.TIME_LAYOUT)
        val time = createRefFor(Home.TIME)
        val threeDot = createRefFor(Home.THREE_DOTS)
        val userText = createRefFor(Home.TEXT)
        val userImage = createRefFor(Home.ICON_ONE)
        val likeIcon = createRefFor(Home.LIKE)
        val commentIcon = createRefFor(Home.COMMENT)
        val repostIcon = createRefFor(Home.REPOST)
        val shareIcon = createRefFor(Home.SHARE)
        val likeCommentShareIcon = createRefFor(Home.LIKE_COMMENT_SHARE_LAYOUT)

        constrain(icon) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(logo) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 20.dp)
        }
        constrain(parentRow) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(postLayout) {
            start.linkTo(icon.end, 10.dp)
            top.linkTo(parent.top)
        }
        constrain(username) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(userText) {
            start.linkTo(icon.end)
            top.linkTo(username.bottom)
        }
        constrain(userImage) {
            top.linkTo(userText.bottom, 10.dp)
            start.linkTo(parent.start, 10.dp)
        }
        constrain(likeCommentShareIcon) {
            top.linkTo(userImage.bottom, 10.dp)
            start.linkTo(parent.start, 20.dp)
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
        constrain(timeLayout) {
            start.linkTo(username.end)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
        constrain(time) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(threeDot) {
            start.linkTo(time.end)
        }


    }
}

private enum class Home {
    ICON, PARENT_ROW, USERNAME, TIME, THREE_DOTS, TEXT, LIKE, COMMENT, SHARE, REPOST, ICON_ONE, LOGO, POST_LAYOUT, TIME_LAYOUT, LIKE_COMMENT_SHARE_LAYOUT,
}