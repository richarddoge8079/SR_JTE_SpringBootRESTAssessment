package REST;

class CarNotFoundException extends RuntimeException {
	CarNotFoundException(Long id) {
		super("Car " + id + " cannot be found, please try again.");
	}
}
