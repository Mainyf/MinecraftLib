package io.github.mainyf.minecraftlib.extensions

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

fun CommandSender.sendMessage(message: Any) {
    this.sendMessage(message.toString())
}

fun CommandSender.sendMessage(message: String, variable: Map<String, String> = mapOf(Pair("&", "§"))) {
    var msg = message
    variable.forEach { pair -> msg = msg.replace(pair.key, pair.value) }
    this.sendMessage(msg)
}

fun CommandSender.sendMessage(messages: Array<String>, variable: Map<String, String> = mapOf(Pair("&", "§"))) {
    messages.forEach { sendMessage(it, variable) }
}

fun CommandSender.sendErrorMessage(message: String, variable: Map<String, String> = mapOf(Pair("&", "§"))) {
    sendMessage("${ChatColor.RED}$message" , variable)
}

fun CommandSender.sendErrorMessage(messages: Array<String>, variable: Map<String, String> = mapOf(Pair("&", "§"))) {
    messages.forEach { sendErrorMessage(it, variable) }
}

fun CommandSender.sendWarnMessage(message: String, variable: Map<String, String> = mapOf(Pair("&", "§"))) {
    sendMessage("${ChatColor.YELLOW}$message" , variable)
}

fun CommandSender.sendWarnMessage(messages: Array<String>, variable: Map<String, String> = mapOf(Pair("&", "§"))) {
    messages.forEach { sendErrorMessage(it, variable) }
}
