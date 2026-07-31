package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Endpoints (GET/PUT/POST/DELETE): Basic HTTP requests that represent different actions. */

// Tells Spring Boot that this class handles HTTP requests and automatically converts Java objects to JSON responses.
// For Java to communicate to JavaScript on the webpage
@RestController

//Sets base URL path for all endpoints to '/characters'
@RequestMapping("/characters")

@CrossOrigin
public class CharacterController {
    @Autowired
    private GenshinCharacterRepository characterRepository;

    // /characters/all
    // Get all characters/fetch data
    @GetMapping("/all")
    public List<GenshinCharacter> getAllCharacters() {
        // findAll() — get all characters
        return characterRepository.findAll();
    }

    // /characters/add
    // Create/save a new character
    @PostMapping("/add")
    public GenshinCharacter addCharacter(@RequestBody GenshinCharacter character) {
        // save(character) — insert or update a character
        return characterRepository.save(character);
    }

    // /characters/{id}
    // Delete a character by id.
    @DeleteMapping("/{id}")
    public void deleteAllCharacters(@PathVariable Integer id) {
        // deleteById(id) — delete an character
        characterRepository.deleteById(id);
    }

    // /characters/{id}
    // Update an existing character
    @PutMapping("/{id}")
    public GenshinCharacter updateCharacter(@PathVariable Integer id, @RequestBody GenshinCharacter character) {
        // save(character) — insert or update a character
        return addCharacter(character);
    }

}
