package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    var userGuess by mutableStateOf("")
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private lateinit var currentWord:String
    private var usedWords:MutableSet<String> = mutableSetOf()
    init {
        resetGame()
    }

    private fun shuffleCurrentWord(currentWord:String):String
    {
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while(String(tempWord).equals(currentWord))
        {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambleWord = pickRandomWordAndShuffle())
    }
    private fun pickRandomWordAndShuffle():String{
        currentWord = allWords.random()
        if(usedWords.contains(currentWord))
            return pickRandomWordAndShuffle()
        else{
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }
    fun updateUserGuess(guessWord:String):Unit{
        userGuess = guessWord
    }

}