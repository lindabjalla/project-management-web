package se.grouprich.projectmanagement.exception;

public final class IssueException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public IssueException(String message) 
	{
		super(message);
	}
}