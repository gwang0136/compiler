package ast;

import environment.*;

/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines methods for the display class
 */
public class Display extends Statement
{
    private Expression expression;
    private Read read;

    /**
     * Creates objects of the expression class
     * @param expression the expression to be displayed
     * @param read the read value
     */
    public Display(Expression expression, Read read)
    {
        this.expression = expression;
        this.read = read;
    }

    /**
     * executes the display, displaying the expression and executing the read value
     * @param env the environment in which the statement occurs.
     */
    public void exec(Environment env)
    {
        System.out.println(expression.eval(env));
        if(read!=null)
            read.exec(env);
    }
}
