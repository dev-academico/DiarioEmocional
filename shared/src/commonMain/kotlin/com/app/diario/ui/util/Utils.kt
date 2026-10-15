package com.app.diario.ui.util

import androidx.compose.ui.graphics.Color

fun corIntensidade(intensidade: Int): Color =
    when {
        intensidade <= 3 -> Color(0xFF2E9E5B) // leve
        intensidade <= 6 -> Color(0xFFE59A00) // moderada
        else -> Color(0xFFD93B3B) // intensa
    }

fun rotuloIntensidade(intensidade: Int): String =
    when {
        intensidade <= 3 -> "Leve"
        intensidade <= 6 -> "Moderada"
        else -> "Intensa"
    }

/** "2026-10-05" -> "05/10/2026" (sem depender de java.time). */
fun formatarData(iso: String): String {
    val partes = iso.split("-")
    return if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else iso
}

fun calcularMinutosSono(
    dormiu: String,
    acordou: String,
): Int {
    return try {
        val (hDormiu, mDormiu) = dormiu.split(":").map { it.toInt() }
        val (hAcordou, mAcordou) = acordou.split(":").map { it.toInt() }

        val minDormiu = hDormiu * 60 + mDormiu
        val minAcordou = hAcordou * 60 + mAcordou

        if (minAcordou >= minDormiu) {
            minAcordou - minDormiu
        } else {
            (24 * 60 - minDormiu) + minAcordou
        }
    } catch (e: Exception) {
        0
    }
}

fun calcularDuracaoFormatada(
    dormiu: String,
    acordou: String,
): String {
    val totalMinutos = calcularMinutosSono(dormiu, acordou)
    val horas = totalMinutos / 60
    val minutos = totalMinutos % 60
    return "${horas}h ${minutos}min"
}
