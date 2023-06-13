package com.auth0.composeprogressiveprofiling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.auth0.composeprogressiveprofiling.ui.theme.ComposeProgressiveProfilingTheme


class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Auth0.Android requires an activity’s context in order to work.
        mainViewModel.setContext(this)

        setContent {
            ComposeProgressiveProfilingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(
                        viewModel = mainViewModel
                    )
                }
            }
        }
    }

}

@Composable
fun App(
    viewModel: MainViewModel
) {
    MainScreen(
        viewModel = viewModel
    )
}