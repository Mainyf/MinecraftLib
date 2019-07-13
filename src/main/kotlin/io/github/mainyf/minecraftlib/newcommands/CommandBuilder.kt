package io.github.mainyf.minecraftlib.newcommands

import io.github.mainyf.minecraftlib.commands.CommandReference
import io.github.mainyf.minecraftlib.extensions.getValueByKeyWith
import io.github.mainyf.minecraftlib.extensions.sendErrorMessage
import org.apache.commons.lang.ArrayUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Suppress("UNCHECKED_CAST")
class CommandBuilder {

    val subCommands: MutableMap<String, SubCommand> = mutableMapOf()
    var defaultAction: (sender: CommandSender) -> Unit = { }
    var unknownCommand: (sender: CommandSender) -> Unit = { }
    var name: String? = null
    var aliases: MutableList<String> = mutableListOf()

    var description: String? = ""
    var usageMessage: String? = ""

    var permission: String? = null
    var permissionMessage: String? = null

    fun defaultAction(defaultAction: (sender: CommandSender) -> Unit) {
        this.defaultAction = defaultAction
    }

    operator fun String.invoke(loadSubCommand: SubCommand.() -> Unit) {
        val subCommand = SubCommand()
        subCommand.loadSubCommand()
        subCommand.name = name
        subCommands[this] = subCommand
    }

    fun unknownSubCommand(unknownCommand: (sender: CommandSender) -> Unit) {
        this.unknownCommand = unknownCommand
    }

    fun build(): Command {
        return object : Command(name) {
            init {
                this.aliases = this@CommandBuilder.aliases
                this.description = this@CommandBuilder.description
                this.usageMessage = this@CommandBuilder.usageMessage
                this.permission = this@CommandBuilder.permission
                this.permissionMessage = this@CommandBuilder.permissionMessage
            }

            override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
                if (args.isEmpty()) {
                    this@CommandBuilder.defaultAction.invoke(sender)
                    return true
                }
                val commandName = args[0]
                val subCommand = subCommands.getValueByKeyWith { it == commandName }
                if (subCommand == null) {
                    this@CommandBuilder.unknownCommand.invoke(sender)
                    return false
                }

                // set original params
                subCommand.originalParams = ArrayUtils.remove(args, 0) as Array<String>

                // has sub command permission
                if (!subCommand.testPermission(sender as Player)) {
                    return false
                }

                // invoke sub command params validator
                val subCommandParams = subCommand.params
                if (subCommandParams.size != subCommand.originalParams.size) {
                    subCommand.paramsLengthException.invoke(sender)
                    return false
                }
                for (i in 0 until subCommandParams.size) {
                    val param = subCommandParams[i]
                    if (
                        param.validator != null
                        && !param.validator!!.invoke(sender, subCommand.originalParams[i])!!
                    ) {
                        return false
                    }
                }

                // final action invoke
                subCommand.action?.invoke(sender, subCommand.params.toTypedArray())
                return true
            }
        }
    }

}

class SubCommand {
    var name: String? = null
    var originalParams: Array<String> = arrayOf()
    var usage: (sender: CommandSender) -> String = {
        var paramsUsage = ""
        params.forEach {param ->
            paramsUsage += if(param.required) "<${param.name}>" else "[${param.name}]"
            paramsUsage += " "
        }
        "/<command> $name ${paramsUsage.trimEnd()}"
    }
    var paramsLengthException: (sender: CommandSender) -> Unit = {}
    var permission: String? = null
    var permissionMessage: String = CommandReference.Message.notPermission
    var action: ((sender: Player, params: Array<Parameter>) -> Unit)? = null
    var params: MutableList<Parameter> = mutableListOf()

    operator fun String.invoke(param: Parameter.() -> Unit) {
        val parameter = Parameter()
        parameter.param()
        this@SubCommand.params.add(parameter)
    }

    fun action(action: (sender: Player, params: Array<Parameter>) -> Unit) {
        this.action = action
    }

    fun paramsLengthException(paramsLengthException: (sender: CommandSender) -> Unit) {
        this.paramsLengthException = paramsLengthException
    }

    fun usage(usage: (sender: CommandSender) -> String) {
        this.usage = usage
    }

    var testPermission: (sender: Player) -> Boolean = {
        if (permission == null) true else {
            it.sendErrorMessage(permissionMessage)
            it.hasPermission(permission)
        }
    }
}

class Parameter {
    var name: String = ""
    var required = true
    var validator: ((sender: CommandSender, value: String) -> Boolean?)? = { _, value ->
        if(required) value.isNotEmpty() else {
            this.value = ""
            true
        }
    }
    var value: String? = null

    fun validator(validator: (sender: CommandSender, value: String) -> Boolean) {
        this.validator = validator
    }
}

fun command(setup: CommandBuilder.() -> Unit): Command {
    val commandBuilder = CommandBuilder()
    commandBuilder.setup()
    return commandBuilder.build()
}