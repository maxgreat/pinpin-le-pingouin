package Network;

public class DisconnectException extends Exception 
{
    public static final long serialVersionUID = 1L;
    public DisconnectException() 
    { 
	super(); 
    }
    public DisconnectException(String message) 
    {
	super(message); 
    }
    public DisconnectException(String message, Throwable cause) 
    {
	super(message, cause); 
    }
    public DisconnectException(Throwable cause) 
    {
	super(cause); 
    }
}
