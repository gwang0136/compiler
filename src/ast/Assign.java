package ast;
import environment.*;

/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * The Assignment class defines the methods for a variable assignment of the form
 * variable := expression;
 */
public class Assign extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Creates an assignment object of name var and value exp
     * @param var the name of the variable
     * @param exp the value of the expression
     */
    public Assign(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Executes the value of the assignment by setting the value of the variable to the expression
     * @param env the environment in which the variable assignment occurs
     */
    public void exec(Environment env)
    {
        Integer val = exp.eval(env);
        env.setVariable(var, val);
    }


}
