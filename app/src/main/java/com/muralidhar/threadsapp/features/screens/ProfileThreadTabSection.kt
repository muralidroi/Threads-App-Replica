package com.muralidhar.threadsapp.features.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.Indicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ProfileThreadTabSection(modifier: Modifier) {

    ConstraintLayout(
        modifier.fillMaxHeight()
    ) {
        val tabGuideLine = createGuidelineFromTop(0.65f)
        val tabHeadings = listOf("Threads", "Replies", "Reposts")
        var tabIndex by remember {
            mutableIntStateOf(0)
        }

        var isSwipeToLeft by remember {
            mutableStateOf(false)
        }

        val dragState = rememberDraggableState(onDelta = { delta ->
            isSwipeToLeft = delta > 0
        })

        val (tab) = createRefs()

        val tabTextStyle = TextStyle(
            fontSize = 18.sp, fontWeight = FontWeight.Medium
        )

        Scaffold(topBar = {
            TabRow(selectedTabIndex = tabIndex, indicator = { tabPositions ->
                if (tabIndex < tabPositions.size) {
                    Indicator(
                        modifier = modifier
                            .tabIndicatorOffset(tabPositions[tabIndex])
                            .padding(start = 5.dp), color = Color.Black
                    )
                }
            }) {
                tabHeadings.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(
                            title, color = Color.Black, style = tabTextStyle
                        )
                    }, selected = tabIndex == index, onClick = { tabIndex = index })
                }
            }
        }, modifier = modifier.constrainAs(tab) {
            top.linkTo(tabGuideLine)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }, content = { paddingValues ->
            Column(
                modifier = modifier
                    .padding(
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .fillMaxSize()
                    .draggable(
                        state = dragState, orientation = Orientation.Horizontal
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (tabIndex) {
                    0 -> {
                        TextButton(
                            onClick = {},
                            border = BorderStroke(1.dp, Color.LightGray),
                            modifier = modifier.width(200.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(vertical = 0.dp)
                        ) {
                            Text(
                                text = "Start your first Thread", fontSize = 16.sp
                            )
                        }
                    }

                    1 -> {
                        Text(
                            text = "You haven't posted any replies yet"
                        )
                    }

                    2 -> {
                        Text(text = "You haven't reposted any threads yet")
                    }
                }
            }
        })
    }
}