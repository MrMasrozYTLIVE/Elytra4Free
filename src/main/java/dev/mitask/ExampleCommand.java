package dev.mitask;

import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.core.command.annotations.CommandExecutor;

public class ExampleCommand extends Command {
    public ExampleCommand() {
        super("ExampleCommand", "description");
    }

    @CommandExecutor
    private String example() {
        //when return type is String you return the message you want to return to the user
        return "Hello World!";
    }

    @CommandExecutor(subCommand = {"remove", "del"})
    @CommandExecutor.Argument("string") //must set argument names
    private String removeFromExampleList(String string) {
        return string + " not found";
    }
}
