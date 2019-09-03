package environment;
import java.util.HashMap;
/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines the properties and methods for the Environment class
 */
public class Environment
{
    private HashMap<String, Integer> variables;
    private Environment env;


    /**
     * Creates an object of the Environment class (sets the instance variable)
     * @param e the parent environment
     */
    public Environment(Environment e)
    {
        variables = new HashMap<String, Integer>();
        env = e;
    }

    /**
     * Returns the parent Env of the environment
     * @return the parent Env of the environment
     */
    public Environment getParentEnv()
    {
        return env;
    }

    /**
     * Returns the variables in the environment
     * @return the HashMap containing the variables in the environment.
     */
    public HashMap<String, Integer> getVars()
    {
        return variables;
    }

    /**
     * Sets the value of the given variable to the given value
     * @param variable the variable name
     * @param value the value of the variable
     */
    public void setVariable(String variable, int value)
    {
        variables.put(variable, value);
    }

    /**
     * Gets the value of the variable
     * @param variable the variable to found
     * @return the value of the variable.
     */
    public Integer getVariable(String variable)
    {
        if(variables.containsKey(variable))
            return variables.get(variable);
        else
            return getParentEnv().getVariable(variable);

    }

}
