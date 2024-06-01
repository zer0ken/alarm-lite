package com.example.alarmapp.addalarm.bookmark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.alarmapp.R

@Composable
fun Bookmark(bookmark : MutableState<Boolean>) {
    val star = if(bookmark.value) ImageVector.vectorResource(id = R.drawable.baseline_star_rate_24)
                else ImageVector.vectorResource(id = R.drawable.baseline_star_outline_24)
    val starColor = if(bookmark.value) Color(0xFFF5EE31)  else Color.Gray
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text =  stringResource(id = R.string.bookmark))
        Icon(
            imageVector = star,
            contentDescription = stringResource(id = R.string.bookmark),
            tint = starColor,
            modifier = Modifier
                .clickable { bookmark.value = !bookmark.value }
        )
    }
}