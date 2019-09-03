package ast;
import environment.*;
/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines properties and methods for the While class
 */
public class While extends Statement
{
    private Expression cond;
    private Program prog;

    /**
     * Creates an object of the While class
     * @param cond the condition for the While
     * @param prog the program to be evaluated
     */
    public While(Expression cond, Program prog)
    {
        this.cond = cond;
        this.prog = prog;
    }

    /**
     * Executes the while loop correctly.
     * @param env the environment in which the statement occurs.
     */
    public void exec(Environment env)
    {
        while(cond.eval(env) == 1)
            prog.exec(env);

    }

}
