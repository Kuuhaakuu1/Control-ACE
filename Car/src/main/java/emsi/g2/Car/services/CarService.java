package emsi.g2.Car.services;

import emsi.g2.Car.entities.Car;
import emsi.g2.Car.entities.Client;
import emsi.g2.Car.models.CarModel;
import emsi.g2.Car.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RestTemplate restTemplate;
    private final String URL="http://localhost:8888/SERVICE-CLIENT";

    public List<CarModel> findAll(){
        List<Car> cars = carRepository.findAll();
        ResponseEntity<Client[]> response =
                restTemplate.getForEntity(this.URL+"/api/client", Client[].class);
        Client[] clients = response.getBody();
        return cars.stream().map((Car car) -> mapToCarResponse(car,clients)).toList();
    }

    private CarModel mapToCarResponse(Car car, Client[] clients){
        Client foundClient = Arrays.stream(clients)
                .filter(client -> client.getId().equals(car.getClient_id()))
                .findFirst()
                .orElse(null);

        return CarModel.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .client(foundClient)
                .matricule(car.getMatricule())
                .model(car.getModel())
                .build();
    }

    public CarModel findById(Long id) throws Exception {
        Car car = carRepository.findById(id).orElseThrow(()->
                new Exception("Car not found"));
        Client client = restTemplate.getForObject(this.URL+"/api/client/"+car.getClient_id(), Client.class);
        return CarModel.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .client(client)
                .matricule(car.getMatricule())
                .model(car.getModel())
                .build();
    }
}
