package emsi.g2.Car.controllers;


import emsi.g2.Car.models.CarModel;
import emsi.g2.Car.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public List<CarModel> findAll(){
        return carService.findAll();
    }

    @GetMapping("/{id}")
    public CarModel findById(@PathVariable Long id) throws Exception{
        return carService.findById(id);
    }
}
