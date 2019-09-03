package ast;
import environment.*;
import java.util.*;

/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines the methods for a Boolean Expression class
 */
public class Bool extends Expression
{
    private List<Expression> exps;
    private List<String> relops;

    /**
     * Creates a Boolean Expression object with the following instance variables
     * @param exps the expressions
     * @param relops the operators
     */
    public Bool(List<Expression> exps, List<String> relops)
    {
        this.exps = exps;
        this.relops = relops;
    }

    /**
     * evaluates the boolean expression
     * @param env the environment in which the evaluation occurs
     * @return the evaluated value, or 1 if true, 0 if false
     */
    public int eval(Environment env)
    {
        Expression exp1 = exps.get(0);
        if (relops.size()==0)
        {
            return exp1.eval(env);
        }
        int result = 0;
        boolean test = false;
        for (int i = 0; i < relops.size(); i++)
        {
            if (exps.size()> i+1)
            {
                Expression exp2 = exps.get(i+1);
                String relop = relops.get(i);
                if (relop.equals(">") && exp1.eval(env) > exp2.eval(env))
                    result +=1;
                else if (relop.equals("<") && (exp1.eval(env) < exp2.eval(env)))
                    result +=1;
                else if (relop.equals("=") && (exp1.eval(env) == exp2.eval(env)))
                    result +=1;
                else if (relop.equals(">=") && (exp1.eval(env) >= exp2.eval(env)))
                    result +=1;
                else if (relop.equals("<=") && (exp1.eval(env) <= exp2.eval(env)))
                    result +=1;
                else if (relop.equals("<>") && (exp1.eval(env) != exp2.eval(env)))
                    result +=1;
                else
                    test = true;

            }
        }
        if(test)
            return 0;
        return 1;
    }
}
