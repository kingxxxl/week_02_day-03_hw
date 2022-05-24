package com.example.firstspringboot.comntroller;

import com.example.firstspringboot.Park;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/park")
public class ParkController {
    List<Park> parks = new ArrayList<>();

    public ParkController() {
        parks.addAll(
                List.of(
                        new Park("101","Abdullah", "water",0,12),
                        new Park("102","Ahmed", "rollercoaster",10,13),
                        new Park("103","Salah", "water",10,14),
                        new Park("104","Ali", "thriller",10,15)
                ));
    }
    @GetMapping()
    ResponseEntity<Object> getPark(){
        return new ResponseEntity<>(parks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<Object> getParkByID(@PathVariable String id){
        int checkForWork = -1;
        Park park = getById(parks, id);
        if (park != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? new ResponseEntity<>("Not Found, no ride with that id", HttpStatus.BAD_REQUEST) :   new ResponseEntity<>(park, HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<Object> addRide(@RequestBody @Valid Park p, Errors errors){
        if (errors.hasErrors()){
            String er = errors.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
        parks.add(p);
        return new ResponseEntity<>(parks, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putPark(@RequestBody @Valid Park p, Errors errors, @PathVariable String id){
        if (errors.hasErrors()) {
            String er = errors.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
        Park park = getById(parks, id);
        if (park != null){
                park.setPrice(p.getPrice());
                park.setRideName(p.getRideName());
                park.setRideType(p.getRideType());
                park.setTickets(p.getTickets());
                return new ResponseEntity<>(park, HttpStatus.OK);

        }else
            return addRide(p, errors);
    }

    @PutMapping("{id}/{amount}")
    public ResponseEntity<Object> putParkTicket(@PathVariable String id, @PathVariable String amount){

        Park park = getById(parks, id);
        if (park != null){
            if (Integer.parseInt(amount) >= park.getPrice()){
                park.setTickets(park.getTickets()-1);
                return new ResponseEntity<>(park, HttpStatus.OK);
            } return new ResponseEntity<>("No money", HttpStatus.OK);
        }else
            return new ResponseEntity<>("Not Found, no ride with that id", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("refile/{id}")
    public ResponseEntity<Object> putParkRefile(@PathVariable String id){
        Park park = getById(parks, id);
        if (park != null){
            if (park.getTickets() == 0){
                park.setTickets(10);
                return new ResponseEntity<>(park, HttpStatus.OK);
            } return new ResponseEntity<>("there are tickets still available!", HttpStatus.OK);
        }else
            return new ResponseEntity<>("Not Found, no ride with that id", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id){
        int checkForWork = -1;
        Park park = getById(parks, id);
        if (park != null){
            checkForWork = Integer.parseInt(id);
            parks.remove(park);
        }
        return (checkForWork == -1) ? new ResponseEntity<>("Not Found, no ride with that id", HttpStatus.BAD_REQUEST)
                :   new ResponseEntity<>(park, HttpStatus.OK);
    }
    public static Park getById(List<Park> p, String id){
        for (Park park: p) {
            if (park.getRideID().equals(id)){
                return park;
            }
        }
        return null;
    }
}
