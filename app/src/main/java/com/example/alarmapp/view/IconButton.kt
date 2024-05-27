package com.example.alarmapp.view

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

/**
 * IconToggleButton을 생성하는 컴포지션 함수입니다.
 *
 * @param painter 내부에 삽입될 아이콘
 * @param checked 초기 버튼 상태
 * @param onCheckedChange 버튼 상태가 변경될 때 호출되는 콜백 함수
 * @author 이현령
 */
@Composable
fun IconToggleButton_(
    painter: Painter,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = Modifier.size(20.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.size(10.dp)
        )
    }
}