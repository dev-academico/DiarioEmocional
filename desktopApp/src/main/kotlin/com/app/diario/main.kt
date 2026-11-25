package com.app.diario

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Diario",
    ) {
        val pastaDados = "${System.getProperty("user.home")}/.diario-emocional"
        java.io.File(pastaDados).mkdirs()
        App(pastaDados = pastaDados)
    }
}