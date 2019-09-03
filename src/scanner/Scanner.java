package scanner;
import java.io.IOException;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2017-2018) lab exercise 1
 * @author Gene Wang
 *         with assistance from Ms. DATAR
 * @version 31 January 2018
 *
 * Usage:
 * The scanner object can be used to lexically analyze the text in a given .txt file.
 * It is responsible for reading an input stream, one character at a time, and separating
 * the input into lexemes. The object will return each lexeme as a String
 * using the nextToken() method.
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * Scanner constructor for construction of a scanner that
     * uses an InputStream object for input.
     * Usage:
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    /**
     * Scanner constructor for constructing a scanner that
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java String object.
     * Postcondition: The input stream is advanced one character if it is not at
     * end of file and the currentChar instance field is set to the String
     * representation of the character read from the input stream.  The flag
     * eof is set true if the input stream is exhausted.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1)
                eof = true;
            else
            {
                currentChar = (char) inp;
                if (currentChar == '.')
                    eof = true;


            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Eats a string char by comparing it to the currentChar; if they are equal,
     * move to the next char, otherwise, throw a ScanErrorException.
     * @param expected the character that you want to compare
     * @throws ScanErrorException if the current character is not as expected.
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == (currentChar))

            getNextChar();

        else

            throw new ScanErrorException("Illegal Character - expected " +
                    expected + "and found " + currentChar);
    }

    /**
     * Checks to see if there is a next token.
     * @return true if there is, otherwise,
     *         false.
     */
    public boolean hasNext()
    {
        return !eof && !Character.toString(currentChar).equals(".");
    }

    /**
     * Returns the next lexeme in the scanner as a String.
     * Checks each set of characters to determine if a
     * legitimate lexeme is present
     * @return the next lexeme in the scanner as a String.
     * @throws IOException if the input/output operations fail or are interrupted
     * @throws ScanErrorException if an unknown character is found
     */
    public String nextToken() throws IOException, ScanErrorException
    {
        String token = new String();
        while(hasNext() && isWhiteSpace(currentChar))

            eat(currentChar);


        if(currentChar == '.')
        {
            eof = true;
            return ".";
        }

        else if(!hasNext())

            return "end";

        if(currentChar == '<')
        {
            in.mark(1);
            eat(currentChar);
            if(currentChar == '=')
            {
                eat(currentChar);
                return "<=";
            }
            else
            {
                in.reset();
                currentChar = '<';
            }
        }

        if(currentChar == '>')
        {
            in.mark(1);
            eat(currentChar);
            if(currentChar == '=')
            {
                eat(currentChar);
                return ">=";
            }
            else
            {
                in.reset();
                currentChar = '>';
            }
        }

        if(currentChar == '<')
        {
            in.mark(1);
            eat(currentChar);
            if(currentChar == '>')
            {
                eat(currentChar);
                return "<>";
            }
            else
            {
                in.reset();
                currentChar = '<';
            }
        }



        if(currentChar == '/')
        {
            in.mark(1);
            eat(currentChar);
            if(currentChar == '/')
                while(!Character.toString(currentChar).equals("\n"))
                {
                    eat(currentChar);
                }
            else
            {
                in.reset();
                currentChar = '/';
            }
        }
        if(currentChar == '/')
        {
            in.mark(1);
            eat(currentChar);
            if(currentChar == '*')
            {
                eat(currentChar);
                boolean test = true;
                while (test)
                {
                    if(currentChar == '*')
                    {
                        in.mark(1);
                        eat(currentChar);
                        if(currentChar == '/')
                        {
                            test = false;
                        }
                        else
                        {
                            in.reset();
                            currentChar = '*';
                        }

                    }
                    else
                    {
                        eat(currentChar);
                    }
                }

            }
            else
            {
                in.reset();
                currentChar = '/';
            }
        }

        if(currentChar == ';')
        {
            eat(currentChar);
            return ";";
        }


        if(currentChar == ':')
        {
            token += currentChar;
            eat(currentChar);
            if(hasNext() && currentChar == '=')
            {
                token += currentChar;
                eat(currentChar);
                return(token);
            }
        }

        while (hasNext() && isWhiteSpace(currentChar))

            eat(currentChar);

        if(isLetter(currentChar))

            return scanIdentifier();

        if(isDigit(currentChar))

            return scanNumber();

        if(isOperand(currentChar))

            return scanOperand();

        else

            throw new ScanErrorException("Unknown character found: " + currentChar);
    }

    /**
     * Checks to see if the given string is a valid digit.
     * @param c the string that you want to check
     * @return true if it is a valid digit, otherwise,
     *         false.
     */
    public static boolean isDigit(char c)
    {
        return (Character.toString(c).compareTo("0") >= 0
                && Character.toString(c).compareTo("9") <= 0);
    }

    /**
     * Checks to see if the given string is a valid letter.
     * @param c the string that you want to check
     * @return true if it is a valid letter, otherwise,
     *         false.
     */
    public static boolean isLetter(char c)
    {
        return (Character.toString(c).toLowerCase().compareTo("a") >= 0 &&
                Character.toString(c).toLowerCase().compareTo("z") <=0);

    }

    /**
     * Checks to see if the given string is a valid whitespace.
     * @param c the string that you want to check
     * @return true if it is a valid whitespace, otherwise,
     *         false.
     */
    public static boolean isWhiteSpace(char c)
    {
        return Character.toString(c).equals(" ") || Character.toString(c).equals("\n") ||
                Character.toString(c).equals("\t") || Character.toString(c).equals("\r") ;
    }

    /**
     * Checks to see if the given string is a valid operand.
     * @param c the string that you want to check
     * @return true if it is a valid operand, otherwise,
     *         false.
     */
    public static boolean isOperand(char c)
    {
        return c == '=' || c == '+' || c == '-' || c == '*'
                || c == '/' || c == '%' || c == '(' || c == ')'
                || c== ';' || c == '>' || c == '<' || c == ',' || c == '.';
    }
    /**
     * Scans the current character and subsequent characters and returns a String
     * containing an Identifier, if present
     * @return a String containing the current Identifier
     * @throws ScanErrorException if there is no valid identifier recognized
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String identifier = "";

        while(hasNext() && (isLetter(currentChar) || isDigit(currentChar)))
        {
            identifier += currentChar;
            eat(currentChar);
        }
        if(!identifier.equals(""))
        {
            return identifier;
        }
        else
            throw new ScanErrorException("No lexeme recognized");

    }

    /**
     * Scans the current character and subsequent characters and returns a String
     * containing an Number, if present
     * @return a String containing the current Number
     * @throws ScanErrorException if there is no valid number recognized
     */
    private String scanNumber() throws ScanErrorException
    {
        String number = "";

        while(hasNext() && (isDigit(currentChar)))
        {
            number += currentChar;
            eat(currentChar);
        }
        if(!number.equals(""))
        {
            return number;
        }
        else
            throw new ScanErrorException("No lexeme recognized");
    }

    /**
     * Scans the current character and returns a String containing an Operand, if present
     * @return a String containing the current Operand
     * @throws ScanErrorException if there is no valid Operand recognized
     */
    private String scanOperand() throws ScanErrorException
    {
        String operand = "";

        if(hasNext() && (isOperand(currentChar)))
        {
            operand += currentChar;
            eat(currentChar);
        }

        if(!operand.equals(""))
        {
            return operand;
        }
        else
            throw new ScanErrorException("No lexeme recognized");
    }
}

