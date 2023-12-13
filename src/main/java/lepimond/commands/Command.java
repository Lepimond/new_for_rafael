package lepimond.commands;

import lepimond.exceptions.PeopleCLIException;

public interface Command {
    void run() throws PeopleCLIException;

}
