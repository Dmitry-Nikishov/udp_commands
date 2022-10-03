package exceptions;

public class ObjectExceedSerializableBufferException extends RuntimeException{
    public ObjectExceedSerializableBufferException( String message )
    {
        super(message);
    }
}
