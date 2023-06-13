package com.auth0.composeprogressiveprofiling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun QuestionAnswerScreen(
    navigation: NavController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(viewModel.progressiveProfilingQuestion)
        AnswerTextField(viewModel = viewModel)
        Button(onClick = {
            viewModel.saveProgressiveProfilingAnswer()
            viewModel.getMetadata()
            navigation.popBackStack()
        }) {
            Text(text = "Save Answer")
        }
    }
}

// At the time of writing (June 2023), Material3’s `TextField` API
// is still experimental. The `@OptIn` annotation allows us to use
// the Material3 version of `TextField` without raising an error.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerTextField(viewModel: MainViewModel) {
    Column(Modifier.padding(16.dp)) {
        TextField(
            value = viewModel.progressiveProfilingAnswer,
            onValueChange = { viewModel.progressiveProfilingAnswer = it }
        )
    }
}