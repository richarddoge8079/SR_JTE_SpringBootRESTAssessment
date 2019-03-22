package REST;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Car {
	private @Id @GeneratedValue Long id;
	private String manufacturer;
	private String name;
	
	Car(String manufacturer, String name) {
		this.manufacturer = manufacturer;
		this.name = name;
	}
}
