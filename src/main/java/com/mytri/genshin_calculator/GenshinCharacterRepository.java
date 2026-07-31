package com.mytri.genshin_calculator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/* Extending JpaRepository generates all basic database SQL query options
 *
 * save(artifact) — insert or update a character
 * findById(id) — get one character by id
 * findAll() — get all characters
 * deleteById(id) — delete a character
 *
 */

public interface GenshinCharacterRepository extends JpaRepository<GenshinCharacter, Integer> {
    Optional<GenshinCharacter> findByAvatarId(Integer avatarId);
}
