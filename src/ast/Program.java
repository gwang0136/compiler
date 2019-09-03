package ast;
import java.util.List;
import environment.Environment;

/**
 * @author Gene Wang
 * @version 28 March 2018
 *
 * Defines the methods and properties of the Program class.
 */
public class Program extends Statement
{
    private List<Statement> statements;

    /**
     * Creates an object of the Program class
     * @param statements the List of Statements in the program
     */
    public Program(List<Statement> statements)
    {
        this.statements = statements;
    }

    /**
     * Executes the program.
     * @param env the environment in which the statement occurs.
     */
    public void exec(Environment env)
    {
        for (Statement state : statements)
            state.exec(env);
    }

}
