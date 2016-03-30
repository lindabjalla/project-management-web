package se.grouprich.projectmanagement.model;

public abstract class AbstractEntity
{
	private Long id;
	private String controlId;

	public AbstractEntity(){}

	public AbstractEntity(Long id, String controlId)
	{
		this.id = id;
		this.controlId = controlId;
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getControlId()
	{
		return controlId;
	}

	public void setControlId(String controlId)
	{
		this.controlId = controlId;
	}
}
