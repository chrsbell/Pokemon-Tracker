package com.example.pokemontracker.utils

import com.example.pokemontracker.utils.MessageProvider

abstract interface MessageView {
    abstract fun setSnackbarView(messageProvider: MessageProvider)
}