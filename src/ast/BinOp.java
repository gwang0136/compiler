package ast;
import environment.*;

/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines the methods for a binary operation class
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Creates an object of the BinOp class with operator op, and expressions exp1 and exp2
     * @param op the operator
     * @param exp1 the first expression
     * @param exp2 the second expression
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluates the BinOp in the environment
     * @param env the environment in which the evaluation occurs.
     * @return the integer value of the BinOp
     */
    public int eval(Environment env)
    {
        if(op.equals("-"))
            return exp1.eval(env) - exp2.eval(env);
        if(op.equals("*"))
            return exp1.eval(env) * exp2.eval(env);
        if(op.equals("/"))
            return exp1.eval(env) / exp2.eval(env);
        if(op.equals("%"))
            return exp1.eval(env) % exp2.eval(env);
        return exp1.eval(env) + exp2.eval(env);
    }
}
