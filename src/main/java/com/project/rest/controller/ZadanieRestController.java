package com.project.rest.controller;

import com.project.model.Student;
import com.project.model.Zadanie;
import com.project.service.ZadanieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api")
public class ZadanieRestController {
    private static ZadanieService zadanieService = null; // dodane, ponieważ metoda z pageable nie działała prawidłowo

    @Autowired
    public ZadanieRestController(ZadanieService zadanieService) {
        this.zadanieService = zadanieService;
    }
    @GetMapping("/zadania/{zadanieId}")
    public ResponseEntity<Zadanie> getZadanie(@PathVariable Integer zadanieId) {// @PathVariable oznacza, że wartość
        return ResponseEntity.of(zadanieService.getZadanie(zadanieId)); // parametru przekazywana jest w ścieżce
    }
    @PostMapping(path = "/zadania")
    public ResponseEntity<Void> createZadanie(@Valid @RequestBody Zadanie zadanie) {// @RequestBody oznacza, że dane
        // projektu (w formacie JSON) są
        Zadanie createdZadanie = zadanieService.setZadanie(zadanie); // przekazywane w ciele żądania
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // link wskazujący utworzony projekt
                .path("/{zadanieId}").buildAndExpand(createdZadanie.getZadanieId()).toUri();
        return ResponseEntity.created(location).build(); // zwracany jest kod odpowiedzi 201 - Created
    } // z linkiem location w nagłówku

    @PutMapping("/zadania/{zadaniaId}")
    public ResponseEntity<Void> updateZadanie(@Valid @RequestBody Zadanie zadanie,
                                              @PathVariable Integer zadanieId) {
        return zadanieService.getZadanie(zadanieId)
                .map(p -> {
                    zadanieService.setZadanie(zadanie);
                    return new ResponseEntity<Void>(HttpStatus.OK); // 200 (można też zwracać 204 - No content)
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }
    @DeleteMapping("/zadania/{zadanieId}")
    public ResponseEntity<Void> deleteZadanie(@PathVariable Integer zadanieId) {
        return zadanieService.getZadanie(zadanieId).map(p -> {
            zadanieService.deleteZadanie(zadanieId);
            return new ResponseEntity<Void>(HttpStatus.OK); // 200
        }).orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }
    //Przykład żądania wywołującego metodę: http://localhost:8080/api/projekty?page=0&size=10&sort=nazwa,desc
    @GetMapping(value = "/zadania")
    public static Page<Zadanie> getZadania(Pageable pageable) { // @RequestHeader HttpHeaders headers – jeżeli potrzebny
        return zadanieService.getZadania(pageable); // byłby nagłówek, wystarczy dodać drugą zmienną z adnotacją
    }
}
