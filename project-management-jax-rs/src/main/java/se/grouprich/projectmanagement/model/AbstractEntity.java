package se.grouprich.projectmanagement.model;

public abstract class AbstractEntity
{
	private Long id;
	private String controlNumber;

	public AbstractEntity(){}

	public AbstractEntity(Long id, String controlNumber)
	{
		this.id = id;
		this.controlNumber = controlNumber;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public String getControlNumber()
	{
		return controlNumber;
	}
}
