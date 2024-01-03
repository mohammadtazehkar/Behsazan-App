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
import com.example.behsaz.data.models.myService.SubCategoryListData
import com.example.behsaz.ui.components.CardMediumCorner
import com.example.behsaz.ui.components.TextTitleMedium
import com.example.behsaz.utils.ClickHelper

@Composable
fun SubCategoryListDialog(
    subCategories: List<SubCategoryListData>,
    onDismissRequest: () -> Unit,
    onConfirmation: (SubCategoryListData) -> Unit
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        CardMediumCorner(modifier = Modifier.padding(vertical = 16.dp)) {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                items(subCategories.size) { index ->
                    CategoryListDialogItem(
                        item = subCategories[index],
                        isLastItem = index == subCategories.size - 1,
                        onItemClick = onConfirmation
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryListDialogItem(
    item: SubCategoryListData,
    isLastItem: Boolean,
    onItemClick: (SubCategoryListData) -> Unit
){
    Column(
        modifier = Modifier.clickable { ClickHelper.getInstance().clickOnce {
            onItemClick(item)
        }}
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

