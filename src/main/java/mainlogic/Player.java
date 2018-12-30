package mainlogic;

import lombok.Getter;
import lombok.Setter;

public class Player {
    @Getter
    private final String indexNumber;
    @Getter
    private final String alias;
    @Getter
    private final String name;
    @Getter
    private final String surname;
    /**
     * Exact string that player put in his info.txt file
     */
    @Getter
    private final String lunchCommand;
    /**
     * Lunch command with full path so that it can be easily run
     */
    @Getter
    @Setter
    private String fullLunchCommand;

    public Player(String indexNumber, String alias, String name, String surname, String lunchCommand) {
        this(indexNumber, alias, name, surname, lunchCommand, null);
    }

    public Player(String indexNumber, String alias, String name, String surname, String lunchCommand, String fullLunchCommand) {
        this.indexNumber = indexNumber;
        this.alias = alias;
        this.name = name;
        this.surname = surname;
        this.lunchCommand = lunchCommand;
        this.fullLunchCommand = fullLunchCommand;
    }

    @Override
    public String toString() {
        return getAlias() + " " + getName() + " " + getSurname() + " " + getLunchCommand();
    }
}
