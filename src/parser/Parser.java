package parser;
import scanner.*;
import ast.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import environment.Environment;

/**
 * @author Gene Wang
 * @version 26 March 2018
 *
 * This class defines the methods and constructs for a parser that takes in input from a Scanner
 * and outputs the result of the program as found using an Abstract Syntax Tree.
 *
 *
 */
public class Parser
{

    private Scanner scan;
    private String curr;

    /**
     * Creates Parser that takes in the given Scanner and initializes its instance variables
     * @param scanner the scanner used by the parser to acquire tokens
     * @throws ScanErrorException if the current character is not as expected
     * @throws IOException if the input/output operations fail or are interrupted
     */
    public Parser(Scanner scanner) throws ScanErrorException, IOException
    {
        scan = scanner;
        curr = scan.nextToken();

    }

    /**
     * Eats the current token and moves to the next token provided by the scanner.
     * @param expected the expected token
     * @throws ScanErrorException if the token is not as expected
     * @throws IOException if the input/output operations fail or are interrupted
     * @throws IllegalArgumentException if the current character is not as expected
     */
    private void eat(String expected) throws ScanErrorException, IOException,
            IllegalArgumentException
    {
        if (expected.equals(curr))
            curr = scan.nextToken();
        else
            throw new IllegalArgumentException(
                    "Expected: " + expected + " but found: " + curr);
    }

    /**
     * Parses the current integer
     * @precondition: current token is an integer
     * @postcondition: number token has been eaten
     * @return the value of the parsed integer
     * @throws ScanErrorException if the current token is not as expected
     * @throws IOException if the input/output operations fail or are interrupted
     */
    private Expression parseNumber() throws ScanErrorException, IOException
    {
        int num = Integer.parseInt(curr);
        eat(curr);
        return new ast.Number(num);
    }

    /**
     * Parses all the statements in a block
     * @precondition: current token is either WRITELN, BEGIN, or a variable
     * @postcondition: a full statement has been eaten, and curr is the first token
     * after the statement
     * @throws ScanErrorException if the token is not as expected
     * @throws IOException if the input/output operations fail or are interrupted
     * @throws IllegalArgumentException if the current character in the scanner is not as expected
     * @return a Statement object that defines the parsed Statement
     */
    public Statement parseStatement() throws ScanErrorException, IOException
    {
        if (curr.equals("display"))
        {
            eat(curr);
            Expression exp = parseExpression();
            Read read = null;
            if (curr.equals("read"))
            {
                eat(curr);
                String id = curr;
                eat(curr);
                read = new Read(id);
            }
            Display d = new Display(exp, read);
            return d;
        }
        else if (curr.equals("assign"))
        {
            eat(curr);
            String varName = curr;
            eat(curr);
            eat("=");
            Expression num = parseExpression();
            return new Assign(varName, num);
        }
        else if (curr.equals("while"))
        {
            eat(curr);
            Expression exp = parseExpression();
            eat("do");
            Program p = parseProgram();
            eat("end");
            return new While(exp, p);
        }
        else
        {
            eat("if");
            Expression exp = parseExpression();
            eat("then");
            Program p = parseProgram();
            return parseIf(exp, p);
        }
    }

    /**
     * Parses the current expression, evaluating integer addition/multiplication.
     * @precondition: the current token is the start of a term
     * @postcondition: the expression has been parsed and curr is the first token
     *  after the expression.
     * @return the value of the expression in an Expression object
     * @throws ScanErrorException if the current token is not as expected
     * @throws IOException if the input/output operations fail or are interrupted
     */
    public Bool parseExpression() throws ScanErrorException, IOException
    {
        List<Expression> expressions = new ArrayList<Expression>();
        List<String> relops = new ArrayList<String>();
        Expression first = parseAddExpr();
        String relop = curr;
        expressions.add(first);
        if (curr!=null && (curr.equals("<") || curr.equals(">") || curr.equals("<=") ||
                curr.equals(">=") || curr.equals("<>")|| curr.equals("=")))
        {
            relops.add(relop);
            eat(curr);
            expressions.add(parseAddExpr());
            String rel = curr;
            while(curr.equals("<") || curr.equals(">") || curr.equals("<=") ||
                    curr.equals(">=") || curr.equals("<>")|| curr.equals("="))
            {
                relops.add(rel);
                eat(curr);
                expressions.add(parseAddExpr());
            }
        }
        return new Bool(expressions,relops);

    }


    /**
     * Parses the given if statement
     * @param exp the expression to evaluate for the boolean
     * @param prog the program to be executed if true
     * @return new IfThen statement with the corresponding values
     * @throws ScanErrorException if a scanning error occurs
     * @throws IOException if an input/output error occurs
     */
    public Statement parseIf(Expression exp, Program prog) throws ScanErrorException, IOException
    {
        if(curr.equals("else"))
        {
            eat("else");
            IfThen ifthen = new IfThen(exp, prog, parseProgram());
            eat("end");
            return ifthen;
        }
        else
        {
            eat("end");
            return new IfThen(exp,prog,null);
        }

    }


    /**
     * Parses the current addition expression
     * @return the parsed add expression
     * @throws ScanErrorException if a scanning error occurs
     * @throws IOException if an input/output error occurs
     */
    public Expression parseAddExpr() throws ScanErrorException, IOException
    {
        Expression sum = parseMultExpr();
        while (curr!=null && (curr.equals("+") || curr.equals("-")))
        {
            String op = curr;
            eat(curr);
            sum = new BinOp(op, sum, parseMultExpr());
        }
        return sum;

    }

    /**
     * parses the current multiplication expression
     * @return the parsed multiplication expression
     * @throws ScanErrorException if a scanning error occurs
     * @throws IOException if an input/output error occurs
     */
    public Expression parseMultExpr() throws ScanErrorException, IOException
    {
        Expression sum = parseNegExpr();
        while(curr!=null && (curr.equals("*") || curr.equals("/")))
        {
            String op = curr;
            eat(curr);
            sum = new BinOp(op, sum, parseNegExpr());
        }
        return sum;
    }

    /**
     * Parses the current negative expression
     * @return the parsed negative expression
     * @throws ScanErrorException if a scanning error occurs
     * @throws IOException if an input/output error occurs
     */
    public Expression parseNegExpr() throws ScanErrorException, IOException
    {
        if (curr.equals("-"))
        {
            eat(curr);
            return new BinOp("-", new ast.Number(0), parseValue());
        }
        return parseValue();
    }

    /**
     * parses the current value
     * @return the parsed value
     * @throws ScanErrorException if a scanning error occurs
     * @throws IOException if an input/output error occurs
     */
    public Expression parseValue() throws ScanErrorException, IOException
    {
        if (curr.equals("("))
        {
            eat(curr);
            Expression e = parseExpression();
            eat(")");
            return e;
        }
        else if (curr.toLowerCase().substring(0,1).compareTo("a") >= 0 &&
                curr.toLowerCase().substring(0,1).compareTo("z") <= 0)
        {
            String var = curr;
            eat(curr);
            return new Variable(var);
        }
        else
            return parseNumber();
    }
    /**
     * Parses the elements that make up the program. This includes any procedures
     * followed by executable statements.
     * @return a Program variable that holds all the procedures and the final statement
     * @throws ScanErrorException if the current token is not as expected
     * @throws IOException if the input/output operations fail or are interrupted
     */
    public Program parseProgram() throws ScanErrorException, IOException
    {
        List<Statement> statements = new ArrayList<Statement>();
        while(curr !=null && !curr.equals("end") && !curr.equals("else"))
        {
            statements.add(parseStatement());
        }
        return new Program(statements);

    }
    /**
     * Tests the functions of the parser using a given input file
     * @param args the string array of arguments.
     */
    public static void main(String[] args)
    {
        try
        {
            FileInputStream inStream = new FileInputStream(
                    new File("/Users/GeneWang/Downloads/simpleTest.txt"));
            Environment env = new Environment(null);
            Scanner lex = new Scanner(inStream);
            Parser parse = new Parser(lex);
            Program program = parse.parseProgram();
            program.exec(env);
        }
        catch (FileNotFoundException e)
        {
            System.out.print("File Not Found");
            e.printStackTrace();
        }

        catch (ScanErrorException e)
        {
            System.out.print(e);
            e.printStackTrace();
        }

        catch (IOException e)
        {
            System.out.print(e);
        }
    }


}

