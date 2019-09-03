package ast;
import environment.*;
/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines the abstract class for an Expression
 */
public abstract class Expression
{
    /**
     * Evaluates the expression in the environment
     * @param env the environment in which the evaluation occurs
     * @return the value of the expression
     */
    public abstract int eval(Environment env);

}
