# Git Strategy: Spatial Car Gallery

## Branching Model (Git Flow)
*   **`main`**: Production-ready code only. Tagged with release versions (e.g., `v1.0.0`).
*   **`develop`**: The active integration branch. All feature branches merge here first.
*   **`feature/<feature-name>`**: For new features (e.g., `feature/json-parser`, `feature/physics-engine`).
*   **`fix/<bug-name>`**: For bug fixes (e.g., `fix/coroutine-scope-crash`).

## Commit Convention
Agents and humans must use Conventional Commits:
*   `feat:` A new feature.
*   `fix:` A bug fix.
*   `refactor:` Rewriting code without changing behavior.
*   `chore:` Updates to build tools, dependencies, or markdown files.

## Release Strategy
When a phase is completed and merged into `main`, it must be tagged.
*   Example: `git tag -a v0.1.0-phase1 -m "Completed Data Layer"`