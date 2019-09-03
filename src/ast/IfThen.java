package ast;
import environment.*;
import java.util.*;
/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines the properties and methods for a IfThen statement
 */
public class IfThen extends Statement
{
    private Expression cond;
    private Statement state1;
    private Statement state2;


    /**
     * Creates an object of the IfThen class with condition and the statements
     *
     * @param cond  the condition for the if
     * @param state the statement to be evaluated if true.
     * @param state2 the statement to be evaluated if false
     */
    public IfThen(Expression cond, Statement state, Statement state2)
    {
        this.cond = cond;
        this.state1 = state;
        this.state2 = state2;
    }


    /**
     * Executes the if statement
     * @param env the environment in which the if statement is executed.
     */
    public void exec(Environment env)
    {
        int val = 0;
        if(cond.eval(env) != 0)
            val = 1;
        if(val == 1)
            state1.exec(env);

        else if(state2 != null)
        {
            state2.exec(env);
        }
    }
}
