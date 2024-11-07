package com.likhit.simplecalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel: ViewModel() {

    private val _equationText = MutableStateFlow("")
    val equationText = _equationText.asStateFlow()

    private val _resultText = MutableStateFlow("0")
    val resultText = _resultText.asStateFlow()

    fun onButtonClick(button: String){
        _equationText.value.let {
            if(button == "AC"){
                _equationText.value = ""
                _resultText.value = "0"
                return
            }

            if(button == "C"){
                if(it.isNotEmpty()){
                    _equationText.value = it.substring(0, it.length - 1)
                    return
                }
            }

            if(button == "="){
                try {
                    _resultText.value = calculateResult(_equationText.value)
                }catch (_: Exception){}
                return
            }

            _equationText.value = it + button
        }
    }

    private fun calculateResult(equation: String): String{
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var result = context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        if(result.endsWith(".0")){
           result = result.replace(".0", "")
        }
        return result
    }
}