package ast;
import environment.*;
/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines the methods for an abstract Statement class
 */
public abstract class Statement
{
    /**
     * Executes the statement.
     * @param env the environment in which the statement occurs.
     */
    public abstract void exec(Environment env);
}
