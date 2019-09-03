package ast;
import java.io.*;
import environment.*;

/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * Defines methods for the read class
 */
public class Read extends Statement
{
    private String id;

    /**
     * Creates an object of the read class
     * @param id the id of the variable
     */
    public Read(String id)
    {
        this.id = id;
    }

    /**
     * Executes the read, reading in an input value from the user
     * and setting the variable id to that value.
     * @param env the environment in which the statement occurs.
     */
    public void exec(Environment env)
    {
        System.out.println("To what value would you like to set " + id + " to? ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int val = 0;
        try
        {
            val = Integer.parseInt(reader.readLine());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        Number num = new Number(val);
        Assign as = new Assign(id, num);
        as.exec(env);

    }
}
