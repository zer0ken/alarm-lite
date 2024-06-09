package com.example.alarmapp.addalarm.ringagain

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.R

@Composable
fun BackwardAndTitle(navController:NavController) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.round_arrow_back_ios_new_24),
            contentDescription = stringResource(id = R.string.backward),
            modifier = Modifier
                .size(24.dp)
                .scale(scaleX = 0.9f, scaleY = 1f)
                .clickable {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = R.string.ring_again), fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}