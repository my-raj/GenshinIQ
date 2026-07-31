package com.mytri.genshin_calculator;

import org.springframework.data.jpa.repository.JpaRepository;

/* Extending JpaRepository generates all basic database SQL query options
*
* save(artifact) — insert or update an artifact
* findById(id) — get one artifact by id
* findAll() — get all artifacts
* deleteById(id) — delete an artifact
*
*/

public interface ArtifactRepository extends JpaRepository<Artifact, Integer> {
}
