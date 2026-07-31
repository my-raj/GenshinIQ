# GenshinIQ

## Project Overview
GenshinIQ is a full-stack web application based on the open-world RPG (role-play game) Genshin Impact, built to help players 
improve their progress and skills while playing the game. 

The application initially only served to contain a single probability calculator to help manage primogem spending
(a type of in-game currency) when wishing for a featured character in the game's gacha-system, but is currently evolving to 
potentially contain features to help curate and build 4-member teams to effectively fight bosses in the game, and to help
build existing characters with the appropriate weapons and artifacts to improve their statistics and their strength.

## Tech Stack

- **Backend**: Java, Spring Boot 4, JPA/Hibernate  
- **Frontend**: HTML, CSS, vanilla JavaScript  
- **Database**: H2 (file-based)  
- **External APIs**: Enka Network API, genshin-db-api  
- **Dev Tools**: Node.js (one-time parsing of raw game data files)  

## Current Features

### Probability calculator
This was the idea that first inspired the creation of this application. 

The very first prototype of this calculator was initially to take in the current pity count (how many wishes have been spent
on the current banner) of the player, calculate the probability of getting a 5-star at the current moment, and reveal that 
to the user with a wish animation (a firework with the appropriate colour- gold for success and purple for persistence).
However, since that ended up being too simple, and it only used obvious wishing logic and common sense, the calculator evolved
into using a monte carlo based simulation that ran 100,000 simulations of wishing until the featured 5-star character was gotten
and plotted a forecasted graph of probability against additional pulls/wishes spent from the current pity count to get the 
featured 5 star.

### Character Roster
This was a sub-step before adding in the character-builder and the team-recommender for the in-game bosses.

The beta-version of this feature initially included a form section where players had to manually enter in their character
details, but for a game with 100+ characters, it would become increasingly harder for players with a lot of characters to 
manually enter in their character details, which was why I had added a feature to allow players to import their characters
automatically using their user ID from the game. Currently, the feature requires for players to update their in-game character
showcase in batches of 12, and import these batches with 1-minute time intervals until all characters have been imported.

Currently, the character details the application stores are the character's name, level, weapon, weapon level, constellation 
level, element type, artifacts (5 individual pieces) and their levels. The application also stores the base attack level, 
defense level, talent levels, and bonuses of each artifact and weapon that the character has on.

The user additionally has the power to manually add, edit, and delete characters from the roster.

## Planned Features

### Team-recommender for Bosses
This feature will be able to study all the data that had been imported from the character roster, match this data with other
characters with elements and talents that would enhance this character's talents or spark reactions that will deal large
damage, and curate a 4-member team whose combined talents and elemental reactions will be able to counteract those of the
boss's and defeat it effectively.

Currently, the feature's mathematical algorithms to calculate character powers and talent multipliers is under progress, but
in the future, the feature may include an ML layer that can do the computing and crafting of the teams against the bosses.

### Character-builder
This feature will be able to look at a selected character, observe the character's current talents and stats, and recommend
artifact sets and weapons in order to help maximize the character's strengths in a particular role- whether that is in
healing, dealing damage, or shielding. Since a single character can potentially play multiple roles, the builder should also
be able to recommend sets in order to maximize each role if the player wishes.

## Long term endeavours

### AI/ML layer
As usage data accumulates, the plan is to introduce a lightweight ML model that learns from player feedback on team recommendations-
re-ranking suggestions based on what actually performs well in practice. This would allow the recommender to improve and adapt
over time.

I plan to also introduce a chatbot feature to the application for players to discuss other genshin-related topics: with regards
to the quests, the lore, and the storyline.

#**STATUS: In progress**

This project is currently still under development.