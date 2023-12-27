package com.example.behsaz.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardMediumCorner(
    modifier : Modifier = Modifier,
    content: @Composable () -> Unit
){
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun CardColumnMediumCorner(
    modifier : Modifier = Modifier,
    cardModifier : Modifier = Modifier,
    columnModifier : Modifier = Modifier,
    columnHorizontalAlignment : Alignment.Horizontal = Alignment.CenterHorizontally ,
    content: @Composable () -> Unit
) {
    CardMediumCorner (
        modifier = cardModifier
    ){
        Column(
            modifier = columnModifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp),
            horizontalAlignment = columnHorizontalAlignment
        ) {
            content()
        }
    }
}

@Composable
fun CardBoxMediumCorner(
    modifier : Modifier = Modifier,
    cardModifier : Modifier = Modifier,
    boxModifier : Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
){
    CardMediumCorner (
        modifier = cardModifier
    ){
        Box(
            modifier = boxModifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp))
        {
            content()
        }
    }
}



@Composable
fun CardRowMediumCorner(
    modifier : Modifier = Modifier,
    cardModifier : Modifier = Modifier,
    rowModifier : Modifier = Modifier,
    rowVerticalAlignment : Alignment.Vertical = Alignment.CenterVertically ,
    content: @Composable RowScope.() -> Unit
) {
    CardMediumCorner (
        modifier = cardModifier
    ){
        Row(
            modifier = rowModifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp),
            verticalAlignment = rowVerticalAlignment
        ) {
            content()
        }
    }
}