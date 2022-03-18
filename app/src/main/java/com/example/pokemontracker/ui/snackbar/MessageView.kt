package com.example.pokemontracker.ui.snackbar

abstract interface MessageView {
    abstract fun setSnackbarView(messageProvider: MessageProvider)
}