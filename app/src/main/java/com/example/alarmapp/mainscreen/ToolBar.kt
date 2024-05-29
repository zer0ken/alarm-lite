package com.example.alarmapp.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes

@Composable
fun ToolBar(navController: NavController) {
    val sortList = listOf<String>("정렬 방식 1", "정렬 2", "정렬 방식 3") //시스템에서 설정된 정렬 방식으로 정렬 진행
    var selectedSort by remember { mutableStateOf(sortList[0]) }
    var menuExpanded by remember { mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(305.dp) // 세로의 3분의 1
    ){
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Go to setting",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable {
                    navController.navigate(Routes.Setting.route)
                    //백스택 관련 작업 필요하면 이곳으로 다시 올 것
                }
        )
        // 다음에 울릴 알람 정보 표시, n시m분 후에 알람이 울립니다 \n 5월 27일 (월) 오전 9:08
        // 알람이 전부 꺼져있다면 "모든 알람이 꺼진 상태입니다"

        // 알람 뷰모델 생기면 객체로 정보 받아와서 작업 시작
    }
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ){
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.rounded_undo_24),
                contentDescription = "Undo",
                modifier = Modifier
                    .clickable {
                        //이전으로 되돌리는 건 메인화면의 알람 수정에 대하여만 고려?
                        //undo 한 것들을 stack에다가 저장
                    }
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.rounded_redo_24),
                contentDescription = "Redo",
                modifier = Modifier
                    .clickable {
                        // stack에 저장된 걸 pop 시켜서 복원
                    }
            )
        }

        Row {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Unit Alarm",
                modifier = Modifier
                    .clickable {
                        navController.navigate(Routes.AddUnitAlarm.route)
                    }
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "How to Sort",
                modifier = Modifier
                    .clickable {
                        menuExpanded = true;
                    }
            )
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                modifier = Modifier
                    .width(128.dp)
            ) {
                sortList.forEach { sortItem ->
                    DropdownMenuItem(
                        text = {
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = sortItem,
                                    color = if(sortItem == selectedSort) Color(0xFF734D4D) else Color.Black
                                )
                                if (sortItem == selectedSort){
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected sort",
                                        tint = Color(0xFF734D4D),
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }
                            }
                        },
                        onClick = {
                            selectedSort = sortItem
                            menuExpanded = false
                            // 정렬 방식 변경으로 인한 알람들 나열 작업
                        }
                    )
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ToolBarPreview() {
    val navController = rememberNavController()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffE9E9E9))
            .padding(horizontal = 24.dp)
    ) {
        ToolBar(navController)
    }
}