package Client.commandManagers.commands;

import Client.commandManagers.Command;

/**
 * Команда exit: завершает работу программы без сохранения в файл.
 */
public class Exit extends Command {

    public Exit() {
        super(false);
    }
    
    @Override
    public String getName() {
        return "exit";
    }
    
    @Override
    public String getDescr() {
        return "завершить программу (без сохранения в файл)";
    }
    
    @Override
    public void execute() {
        System.out.println("Завершение программы без сохранения...");
        System.exit(0);
    }
    
    @Override
    public boolean checkArgument(Object argument) {
        // Команда не требует аргументов
        return true;
    }
} 