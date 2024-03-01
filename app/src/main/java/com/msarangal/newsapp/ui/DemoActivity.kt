package com.msarangal.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msarangal.newsapp.ui.theme.NewsAppTheme

class DemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                WellnessScreen()
            }
        }
    }
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        WaterCounter(modifier = Modifier)
        /**
         *  [toMutableStateList] makes the list observable by compose runtime.
         *  If this list changes, the Compose will trigger a recomposition.
         */
        val list = remember {
            wellnessViewModel.tasks
        }
        WellnessTaskList(
            list = list,
            onClose = { task -> wellnessViewModel.remove(task) }
        )
    }
}

@Composable
fun WaterCounter(modifier: Modifier) {
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }
    StatelessCounter(count = count, modifier = modifier, onIncrement = { count++ })
}

@Composable
fun StatelessCounter(
    count: Int,
    modifier: Modifier,
    onIncrement: () -> Unit
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(text = "You've had $count glasses")
        }

        Button(
            onClick = { onIncrement() },
            enabled = count < 10,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Add one")
        }

    }
}

@Composable
fun WellnessTaskList(
    list: List<WellnessTask>,
    onClose: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(items = list, key = { task -> task.id }) {
            WellnessTaskItem(taskName = it.label, onClose = { onClose(it) })
        }
    }
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by rememberSaveable {
        mutableStateOf(false)
    }
    WellnessTaskItem(
        taskName = taskName,
        checked = checked,
        onCheckedChange = { newValue -> checked = newValue },
        onClose = onClose,
        modifier = modifier
    )
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp), text = taskName
        )

        Checkbox(
            checked = checked,
            onCheckedChange = { value -> onCheckedChange(value) }
        )

        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close"
            )
        }
    }
}

data class WellnessTask(val id: Int, val label: String)

class WellnessViewModel : ViewModel() {

    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(task: WellnessTask) {
        _tasks.remove(task)
    }

    private fun getWellnessTasks(): List<WellnessTask> =
        List(30) { index -> WellnessTask(index, "Task # $index") }
}