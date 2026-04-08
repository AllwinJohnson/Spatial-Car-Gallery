# Product Requirements: Spatial Car Gallery

## Core Concept
A KMP mobile app where supercar specification cards dynamically "float" around the screen reacting to the physical tilt of the user's device (Anti-Gravity UI).

## Phase 1: Data Layer & Foundation
*   Initialize KMP project.
*   Store `supercars.json` in common resources.
*   Implement `kotlinx.serialization` to parse the JSON into a Kotlin Data Class.
*   Create a simple Repository to expose the list of cars.

## Phase 2: The Physics Engine
*   Create an `expect/actual` interface for the `SensorDataProvider`.
*   Implement Android specific `SensorManager` logic to stream Pitch/Roll data via `callbackFlow`.
*   Create a `PhysicsEngine` class in `commonMain` to translate tilt data into X/Y offset vectors.

## Phase 3: The Anti-Gravity UI
*   Build a `FloatingCarCard` composable displaying Make, Model, 0-60 Time, and Price.
*   Connect the `PhysicsEngine` offset data to the card's `Modifier.offset`.
*   Implement collision bounds so the cards bounce off the edges of the screen instead of disappearing.