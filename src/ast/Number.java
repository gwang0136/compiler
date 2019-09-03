package ast;
import environment.*;
/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines the properties and methods for the AST number class.
 */
public class Number extends Expression
{
    private int value;

    /**
     * Creates an object of the Number class of value val
     * @param val the value of the number
     */
    public Number(int val)
    {
        value = val;
    }

    /**
     * Evaluates the Number creation
     * @param env the environment in which the evaluation occurs
     * @return the value of the Number
     */
    public int eval(Environment env)
    {
        return value;
    }

}
