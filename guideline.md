# Engineering Guidelines

## Kotlin Multiplatform (KMP) Strict Rules
1.  **No Platform Code in Common**: The `commonMain` directory must NEVER contain `android.*` or `ios.*` imports.
2.  **Hardware Sensors**: All hardware interactions (accelerometer, gyroscope) MUST use the `expect/actual` pattern or a shared interface injected from the platform side.
3.  **Coroutines**: 
    *   Never call a `suspend` function inside an `init {}` block without launching a scope.
    *   Use `callbackFlow` for streaming hardware sensor data, utilizing `trySend()`, not `emit()`.

## UI/UX Standards
1.  **Compose Multiplatform**: All UI is built using Compose. 
2.  **Physics Animations**: Use `Modifier.graphicsLayer` or `Modifier.offset` for performant, hardware-accelerated movements based on sensor data. Avoid expensive recompositions.