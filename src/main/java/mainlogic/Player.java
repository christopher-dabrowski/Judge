package mainlogic;

import lombok.Getter;

public class Player {
    @Getter
    private final String indexNumber;
    @Getter
    private final String alias;
    @Getter
    private final String name;
    @Getter
    private final String surname;
    @Getter
    private final String lunchCommand;

    public Player(String indexNumber, String alias, String name, String surname, String lunchCommand) {
        this.indexNumber = indexNumber;
        this.alias = alias;
        this.name = name;
        this.surname = surname;
        this.lunchCommand = lunchCommand;
    }

    @Override
    public String toString() {
        return getAlias() + " " + getName() + " " + getSurname() + " " + getLunchCommand();
    }
}
