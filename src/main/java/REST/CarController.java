package REST;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CarController {
	private final CarRepository repository;
	
	CarController(CarRepository repository) {
		this.repository = repository;
	}
	
	//Aggregate root
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	Resources<Resource<Car>> all() {
		List<Resource<Car>> cars = repository.findAll().stream()
				.map(car -> new Resource<>(car,
						linkTo(methodOn(CarController.class).one(car.getId())).withSelfRel(),
						linkTo(methodOn(CarController.class).all()).withRel("cars")))
				.collect(Collectors.toList());
		
		return new Resources<>(cars,
				linkTo(methodOn(CarController.class).all()).withSelfRel());
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	Car newCar(@RequestBody Car newCar) {
		return repository.save(newCar);
	}
	
	//Single item
	@RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
	Resource<Car> one(@PathVariable Long id) {
		Car car = repository.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
		
		return new Resource<>(car,
				linkTo(methodOn(CarController.class).one(id)).withSelfRel(),
				linkTo(methodOn(CarController.class).all()).withRel("cars"));
	}
	
	@RequestMapping(value = "/hello/{id}", method = RequestMethod.POST)
	Car replaceCar(@RequestBody Car newCar, @PathVariable Long id) {
		return repository.findById(id)
				.map(car -> {
					car.setManufacturer(newCar.getManufacturer());
					car.setName(newCar.getName());
					return repository.save(car);
				})
				.orElseGet(() -> {
					newCar.setId(id);
					return repository.save(newCar);
				});
	}
	
	@RequestMapping(value = "/hello/{id}", method = RequestMethod.DELETE)
	void deleteCar(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
