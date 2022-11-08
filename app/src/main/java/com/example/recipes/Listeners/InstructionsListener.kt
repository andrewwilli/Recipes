package com.example.recipes.Listeners

import com.example.recipes.model.InstructionsResponse

interface InstructionsListener {
    fun didFetch(responses: List<InstructionsResponse?>?, message: String?)
    fun didError(message: String?)
}