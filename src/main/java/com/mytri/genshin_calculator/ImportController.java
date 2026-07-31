package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/import")
@CrossOrigin
public class ImportController {

    @Autowired
    private EnkaImportService enkaImportService;

    @Autowired
    private EnkaConversionService enkaConversionService;

    @Autowired
    private GenshinCharacterRepository characterRepository;

    @GetMapping("/{uid}")
    public ResponseEntity<?> importByUid(@PathVariable String uid) {
        EnkaResponse enkaResponse = enkaImportService.fetchEnkaResponse(uid);

        if (enkaResponse.getTtl() != null && enkaResponse.getTtl() > 5) {
            return ResponseEntity.status(429).body("Cache not expired yet. Please wait " + enkaResponse.getTtl() + " seconds before re-importing.");
        }

        List<GenshinCharacter> characterList = enkaConversionService.convertAllCharacters(enkaResponse);
        for (GenshinCharacter character : characterList) {
            if (characterRepository.findByAvatarId(character.getAvatarId()).isPresent()) {
                GenshinCharacter existingCharacter = characterRepository.findByAvatarId(character.getAvatarId()).get();
                character.setId(existingCharacter.getId());
                characterRepository.save(character);
            } else {
                characterRepository.save(character);
            }
        }
        return ResponseEntity.ok(characterList);
    }
}