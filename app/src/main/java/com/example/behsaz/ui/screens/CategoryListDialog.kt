package com.example.behsaz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.behsaz.data.models.home.CategoryListData
import com.example.behsaz.ui.components.CardMediumCorner
import com.example.behsaz.ui.components.TextTitleMedium

@Composable
fun CategoryListDialog(
    categories: List<CategoryListData>,
    onDismissRequest: () -> Unit,
    onConfirmation: (CategoryListData) -> Unit
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        CardMediumCorner(modifier = Modifier.padding(vertical = 16.dp)) {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                items(categories.size) { index ->
                    CategoryListDialogItem(
                        item = categories[index],
                        isLastItem = index == categories.size - 1,
                        onItemClick = onConfirmation
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryListDialogItem(
    item: CategoryListData,
    isLastItem: Boolean,
    onItemClick: (CategoryListData) -> Unit
){
    Column(
        modifier = Modifier.clickable {
            onItemClick(item)
        }
    ) {
        TextTitleMedium(
            text = item.title,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        )
        if (!isLastItem){
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))
        }
    }

}
//data class CategoryListData(val id : Int, val title: String)

