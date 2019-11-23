package workspan;

public class Entity {
	Long entityId;
	String name;
	String description;
	
	Entity(Long entityId, String name, String description) {
		this.entityId = entityId;
		this.name = name;
		this.description = description;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
