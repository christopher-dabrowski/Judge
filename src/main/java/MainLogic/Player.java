package MainLogic;

public class Player {
    private String alias;
    private String name;
    private String surname;
    private String lunchCommand;

    public Player(String alias, String name, String surname, String lunchCommand) {
        this.alias = alias;
        this.name = name;
        this.surname = surname;
        this.lunchCommand = lunchCommand;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLunchCommand() {
        return lunchCommand;
    }
}
