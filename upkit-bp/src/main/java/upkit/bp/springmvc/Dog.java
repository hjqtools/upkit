package upkit.bp.springmvc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.elasticsearch.cluster.metadata.AliasAction.Add;

public class Dog {

	@NotNull(message = "{Dog.id.non}", groups = { Update.class })
	@Min(value = 1, message = "{Dog.age.lt1}", groups = { Update.class })
	private Long id;

	@NotBlank(message = "{Dog.name.non}", groups = { Add.class, Update.class })
	private String name;

	@Min(value = 1, message = "{Dog.age.lt1}", groups = { Add.class, Update.class })
	private Integer age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
