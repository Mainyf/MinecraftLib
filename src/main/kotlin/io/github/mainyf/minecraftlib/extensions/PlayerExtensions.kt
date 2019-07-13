package io.github.mainyf.minecraftlib.extensions

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

//fun CommandSender.sendMessage(message: Any) {
//    this.sendMessage(message.toString())
//}

fun CommandSender.sendColorMessage(message: String, variable: Map<String, Any> = mapOf()) {
    var msg = message
    variable.mergeMap(mapOf("&" to "§")).forEach { pair -> msg = msg.replace(pair.key, pair.value.toString()) }
    this.sendMessage(msg)
}

fun CommandSender.sendColorMessage(messages: Array<String>, variable: Map<String, Any> = mapOf()) {
    messages.forEach { sendColorMessage(it, variable) }
}

fun CommandSender.sendErrorMessage(message: String, variable: Map<String, Any> = mapOf()) {
    sendColorMessage("${ChatColor.RED}$message", variable)
}

fun CommandSender.sendErrorMessage(messages: Array<String>, variable: Map<String, Any> = mapOf()) {
    messages.forEach { sendErrorMessage(it, variable) }
}

fun CommandSender.sendWarnMessage(message: String, variable: Map<String, Any> = mapOf()) {
    sendColorMessage("${ChatColor.YELLOW}$message", variable)
}

fun CommandSender.sendWarnMessage(messages: Array<String>, variable: Map<String, Any> = mapOf()) {
    messages.forEach { sendErrorMessage(it, variable) }
}
