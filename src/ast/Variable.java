package ast;
import environment.*;

/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines the properties and methods for a Variable class
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Creates an object of the Variable class
     * @param name the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Evaluates the variable.
     * @param env the environment in which the evaluation occurs
     * @return the value of the variable in the environment.
     */
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
