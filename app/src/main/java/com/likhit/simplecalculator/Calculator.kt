package com.likhit.simplecalculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.likhit.simplecalculator.ui.theme.MediumGray
import com.likhit.simplecalculator.ui.theme.Orange

val buttonList = listOf(
    "AC","(",")","/",
    "7","8","9","*",
    "4","5","6","+",
    "1","2","3","-",
    "0",".","C","="
)

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel,
) {

    val equationText = viewModel.equationText.collectAsState()
    val resultText = viewModel.resultText.collectAsState()

    Box(
        modifier = modifier.padding(8.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = equationText.value,
                fontSize = 30.sp,
                textAlign = TextAlign.End,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = resultText.value,
                fontSize = 60.sp,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4)
            ) {
                items(buttonList){button ->
                    CalculatorButton(
                        button = button,
                        onClick = {
                            viewModel.onButtonClick(button)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    button: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(4.dp)
    ){
        FloatingActionButton(
            onClick = { onClick() },
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            contentColor = Color.White,
            containerColor = getColor(button = button)
        ) {
            Text(
                text = button,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun getColor(button: String): Color{
    if(button == "AC" || button == "C"){
        return Orange
    }
    if(button == "(" || button == ")" || button == "+" || button == "-"
        || button == "*" || button == "/"){
        return MediumGray
    }
    if(button == "="){
        return MaterialTheme.colorScheme.primaryContainer
    }
    return Color.Gray
}