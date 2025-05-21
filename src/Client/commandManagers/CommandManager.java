package Client.commandManagers;

import Client.commandManagers.commands.*;
import Client.network.TCPClient;
import Client.exceptions.BuildObjectException;
import Client.exceptions.CommandInterruptedException;
import Client.exceptions.UnknownCommandException;
import Common.models.User;
import java.util.*;


/**
 The CommandManager class is responsible for managing all available commands in the application.
 It provides a static method getCommandMap() that returns a HashMap of all commands where the key is
 the command name and the value is an instance of the corresponding Command subclass.
 */
public class CommandManager {

    /**
     A LinkedHashMap object that stores all available commands in the application. The key is the command name
     and the value is an instance of the corresponding Command subclass.
     */
    private LinkedHashMap<String, Command> commandMap;
    private Scanner scanner;
    private final TCPClient tcpClient;
    private final User user;
    private CommandMode currentMode = CommandMode.CLI_UserMode;

    /**
     * Новый конструктор, получающий заранее созданный экземпляр StudyGroupCollectionManager.
     *
     * @param scanner сканер для чтения ввода
     */
    public CommandManager(Scanner scanner, TCPClient tcpClient, User user) {
        this.scanner = scanner;
        this.tcpClient = tcpClient;
        this.user = user;
        initializeCommands();
    }

    private void initializeCommands() {
        commandMap = new LinkedHashMap<>();
        commandMap.put("help", new Help(tcpClient, user));
        commandMap.put("info", new Info(tcpClient, user));
        commandMap.put("show", new Show(tcpClient, user));
        commandMap.put("add", new Add(scanner, this, tcpClient, user));
        commandMap.put("update_id", new UpdateId(scanner, this, tcpClient, user));
        commandMap.put("remove_by_id", new RemoveById(tcpClient, user));
        commandMap.put("clear", new Clear(tcpClient, user));
        commandMap.put("execute_script", new ExecuteScript(this));
        commandMap.put("exit", new Exit());
        commandMap.put("head", new Head(tcpClient, user));
        commandMap.put("remove_head", new RemoveHead(tcpClient, user));
        commandMap.put("remove_lower", new RemoveLower(tcpClient, user));
        commandMap.put("average_of_transferred_students", new AverageOfTransferredStudents(tcpClient, user));
        commandMap.put("group_counting_by_form_of_education", new GroupCountingByFormOfEducation(tcpClient, user));
        commandMap.put("print_field_ascending_group_admin", new PrintFieldAscendingGroupAdmin(tcpClient, user));
    }

    /*
    public void initializeData(String dataFile) {
        collectionManager.initializeData(dataFile);
    }
     */

    /**
     Returns the commandMap HashMap that stores all available commands in the application.
     @return the HashMap of all commands where the key is the command name and the value is an instance
     of the corresponding Command subclass.
     */
    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }

    public void setCurrentMode(CommandMode mode) {
        this.currentMode = mode;
    }

    public CommandMode getCurrentMode() {
        return currentMode;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Universe method for command executing.
     */
    public void executeCommand(String[] args) {
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException("Команда не указана.");
            }

            Command command = commandMap.get(args[0]);
            if (command == null) {
                throw new UnknownCommandException("\nКоманда " + args[0] + " не обнаружена :( ");
            }

            if (args.length > 1) {
                command.setArgument(args[1]);
            }

            command.execute();

        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException e) {
            System.err.println("Выполнение команды пропущено из-за неправильных предоставленных аргументов! (" + e.getMessage() + ")");
            if (currentMode == CommandMode.CLI_UserMode) {
                throw new CommandInterruptedException(e);
            }
        } catch (BuildObjectException | UnknownCommandException e) {
            System.err.println(e.getMessage());
            if (currentMode == CommandMode.CLI_UserMode) {
                return;
            }
        } catch (Exception e) {
            // Обработка всех остальных исключений
            System.err.println("В командном менеджере произошла непредвиденная ошибка! ");
            if (currentMode == CommandMode.CLI_UserMode) {
                throw new CommandInterruptedException(e);
            }
        }
    }
}
