# Learn English: Grammar Games — Graphic Assets System & Master Manifest

```text
================================================================================
CRITICAL DIRECTIVE: MANDATORY ASSET WORKFLOW & ANTI-IMPROVISATION CONTRACT

DO NOT GENERATE A NEW VISUAL ASSET BEFORE CHECKING THIS ASSET MANIFEST.
Reuse approved assets whenever possible.

Feature implementation prompts are NOT allowed to invent new art styles,
new mascot designs, new chest designs, new XP symbols, new star designs,
or new game icon styles.

If a required asset is missing, specify and define it through this
Graphic Assets System first.

All new artwork must strictly comply with:
- ART_DIRECTION.md (Rendering style, lighting, outline policy, palette)
- CHARACTER_BIBLE.md (Anatomy, proportions, character palette, pose library)
================================================================================
DOCUMENT HIERARCHY:

DESIGN_SYSTEM.md    ─── defines UI components, tokens, interactions, and layouts.
ART_DIRECTION.md    ─── defines the visual world, lighting, rendering, and safe zones.
CHARACTER_BIBLE.md  ─── defines the mascot identity, anatomy, emotions, and poses.
GRAPHIC_ASSETS.md   ─── defines how visual assets are categorized, named, stored,
                        reviewed, requested, and integrated into Android.
================================================================================
```

---

## 1. Asset Categories & Architectural Responsibilities

Every visual asset in the application belongs to one strictly defined functional family:

```text
app/src/main/res/ (or core/designsystem/art)
│
├── character/     ─── Main dragon mascot, NPCs, future companions, creatures.
├── background/    ─── Full-screen background scenes (home, journey, results, onboarding).
├── environment/   ─── Modular nature & landscape props (clouds, trees, rocks, hills, bridges).
├── world/         ─── Journey map nodes (lesson, boss, locked, completed, portal landmarks).
├── props/         ─── Story & action items (grammar books, pencils, maps, shields, wands).
├── reward/        ─── Tangible celebration rewards (chests, coins, XP stars, gem bundles).
├── achievement/   ─── Badges and medals for grammar milestones and streaks.
├── badge/         ─── Compact status badges (level, streak count, difficulty, daily).
├── game/          ─── Illustrated icons and tiles for mini-games (crossword, memory, maze).
├── illustration/  ─── Non-interactive story/status scenes (empty states, errors, intros).
├── icon/          ─── Interactive utility & gameplay navigation glyphs.
├── effect/        ─── Particles, bursts, sparkles, confetti, rays, and glows.
└── decoration/    ─── Ambient non-critical accent foliage, paper bits, and floating dust.
```

### Categorical Boundary Rules:
- **Zero UI Bleed**: No category may contain baked-in Android UI (buttons, progress bars, cards, labels).
- **Zero Text Bleed**: No category may contain baked-in alphanumeric text or English words (for localization).
- **Zero Asset Duplication**: Global elements (e.g. `main_dragon_idle`, `coin_default`, `xp_star`, `chest_closed`) reside in shared scopes, never duplicated inside individual game or feature directories.

---

## 2. Android Resource Directory & Format Policy

Assets are stored and processed according to strict Android OS and Compose runtime guidelines:

| Asset Type | Target Resource Directory | File Format | Transparency | Recommended Master Dimensions |
|---|---|---|---|---|
| **Utility Icons & Glyphs** | `res/drawable/` | **VectorDrawable (`.xml`)** | Yes (Vector alpha) | 24×24 dp viewport |
| **Simple Badges / Shapes** | `res/drawable/` | **VectorDrawable (`.xml`)** | Yes | 48×48 dp to 96×96 dp |
| **Characters & Props** | `res/drawable-nodpi/` | **WebP (Lossless/Alpha)** or PNG | **Yes (100% Alpha BG)** | 512×512 to 1024×1024 px master |
| **Modular Environment** | `res/drawable-nodpi/` | **WebP / VectorDrawable** | **Yes (100% Alpha BG)** | 256×256 to 512×512 px |
| **Game Icons & Tiles** | `res/drawable-nodpi/` | **WebP / VectorDrawable** | **Yes (100% Alpha BG)** | 256×256 px master |
| **Full-Screen Backgrounds** | `res/drawable-nodpi/` | **WebP (Lossy 90%)** | No (Opaque RGB) | 1440×2560 px (Aspect 9:16 safe) |
| **App Launcher Icons** | `res/mipmap-*/` | Adaptive Vector + PNG | Manifest-dependent | System standard |

### Format & Storage Directives:
1. **Never use `mipmap` for in-app illustrations**: `mipmap` is reserved exclusively for the Android OS launcher icon. All in-app graphics belong in `drawable` or `drawable-nodpi`.
2. **Never use JPG for transparent assets**: Any asset with cutouts or irregular edges must be WebP or PNG. JPG produces black/grey matte halos that ruin Compose dark/light surfaces.
3. **Use VectorDrawable whenever possible**: For icons, badges, and flat geometric shapes, VectorDrawable scales losslessly from 24dp to 600dp with zero memory penalty.
4. **Master Resolution Cap**: Avoid 4096×4096 px textures for small objects. Objects rendering at 96dp must not exceed 256–384 px master resolution.

---

## 3. Strict Naming Conventions

All assets must use clean, lowercase, machine-friendly snake_case:

```text
[category]_[subcategory/object]_[descriptor/state]_[variant].[ext]
```

### Canonical Approved Examples:
- `main_dragon_idle`
- `main_dragon_reading`
- `main_dragon_celebrating`
- `tree_round_01`
- `tree_pine_02`
- `cloud_small_01`
- `cloud_wide_02`
- `chest_closed`
- `chest_ready`
- `chest_open`
- `xp_star`
- `coin_default`
- `game_crossword_art`
- `game_memory_art`
- `achievement_grammar_master`

### Strictly FORBIDDEN File Names:
❌ `image1.png`, `pic_new.webp`, `final_dragon.png`, `sparky2.png`, `dragon_v2_final.xml`, `icon_test.webp`, `hero_art_good.png`.

---

## 4. Visual Execution & Art Direction Standards

Every asset must inherit the principles locked in `ART_DIRECTION.md`:

```text
┌─────────────────────────────────────────────────────────────┐
│ 1. Zero Black Outlines: No 1990s comic black inked strokes. │
│    Shapes are defined by clean color contrast or soft       │
│    harmonious tinted silhouettes.                           │
│ 2. Soft Upper-Left Lighting: Directional highlights hit top │
│    and left surfaces (10 o'clock sun angle).                │
│ 3. Subtle Tonal Gradients: Clean vector shapes enriched by  │
│    soft vertical or radial gradients (no gritty noise).     │
│ 4. Isolated Ground Shadows: Standalone assets must NOT bake │
│    harsh black ground shadows into their alpha bounds.      │
│    Ground contact shadows are rendered cleanly via Compose. │
│ 5. Distinct Color Roles:                                    │
│    - Dragon: Fresh green (#58C96B), cream horns (#F5DDA6).  │
│    - Brand: Royal Purple (#6C5CE7 / #8875FF).               │
│    - Rewards: Warm radiant gold (#F1C40F / #F39C12).        │
│    - Correct UI: Emerald teal (#00B894) ── distinct from art│
└─────────────────────────────────────────────────────────────┘
```

---

## 5. Core Asset Systems Specifications

### 5.1 Character Assets (`character/`)
- Must comply 100% with `CHARACTER_BIBLE.md`.
- Proportions: 8-unit construction grid (42% head, 28% torso, 18% legs, 12% feet).
- Eyes: Warm amber-brown iris (`#4E342E` / `#D35400`) with circular pupil.
- Signature Accessories: Royal purple travel backpack (`#6C5CE7`) and golden-yellow scarf (`#F1C40F`).
- Naming: `main_dragon_<pose_name>`.

### 5.2 Environment & World Assets (`environment/` & `world/`)
- Modular components for constructing the Grammar Journey map:
  - **Clouds**: `cloud_small_01`, `cloud_medium_01`, `cloud_wide_01` (puffy, soft white `#FFFFFF` with pale lavender underside `#EDE9FE`).
  - **Trees**: `tree_round_01`, `tree_round_02`, `tree_tall_01`, `tree_pine_01` (chunky cartoon canopy, warm timber trunk).
  - **Rocks & Foliage**: `rock_small_01`, `bush_round_01`, `flower_patch_01`.
  - **Grammar Journey Nodes**: `world_node_base`, `world_node_boss`, `world_node_checkpoint`.
    *Note*: Visual states (`locked`, `current`, `completed`, `mastered`) are composed dynamically in Compose via tinted badge/star overlays, avoiding five duplicate PNG files for a single rock node.

### 5.3 Rewards & Economy Assets (`reward/`)
- **Treasure Chest Family**:
  - `chest_closed`: Heavy fantasy wooden chest with gold trim, purple velvet lining hint, and gold padlock.
  - `chest_ready`: Bouncing/glowing variant with subtle golden rays.
  - `chest_open`: Lid swung open at a 55-degree angle, golden light spilling from inside.
  - `chest_claimed`: Emptied chest with open lid, resting peacefully.
- **Currency Metaphor System**:
  - **XP / Mastery**: `xp_star` — Radiant five-pointed golden star with rounded tips and circular glint.
  - **Coins / Soft Currency**: `coin_default` — Thick golden minted disc with an embossed book/quill symbol.
  - **Keys**: `key_grammar_silver`, `key_grammar_gold` — Whimsical fantasy keys for unlockable bonus levels.

### 5.4 Game Icons (`game/`)
Each game mode in the Games Arena hub receives an iconic, centered illustration displayed within a standardized Compose squircle tile:
- `game_crossword_art`: 3 clean white letter-tiles interlocking with a chunky golden pencil (zero text on tiles).
- `game_word_search_art`: Circular golden magnifying glass hovering over an abstract dot-grid matrix.
- `game_memory_art`: Two playfully flipped game cards revealing matching glowing golden stars.
- `game_battle_art`: Open royal purple grammar book projecting a soft golden energy shield with sparkles.
- `game_speed_art`: Rounded golden stopwatch with dynamic motion streaks.
- `game_sentence_art`: Three colorful interlocking puzzle blocks forming a clean bridge.

### 5.5 Achievements (`achievement/`)
- Unified achievement medal frame family (`achievement_frame_gold`, `achievement_frame_silver`).
- Central symbolic emblem representing the milestone (e.g. book, streak flame, star cluster, graduation cap).
- States (`locked`, `in_progress`, `completed`) are managed via Compose color filters and alpha modifiers on the base asset.

---

## 6. Composition & Layering Rules in Jetpack Compose

Rich game scenes (such as the Home Hero or Grammar Journey) must be assembled via Compose multi-layer stacking rather than giant flattened graphics:

```text
┌─────────────────────────────────────────────────────────────┐
│ LAYER 5: Interactive Compose UI (Buttons, Top Bar, HUD)     │
├─────────────────────────────────────────────────────────────┤
│ LAYER 4: Dynamic Overlay FX (Sparkles, Confetti, Star Bursts)│
├─────────────────────────────────────────────────────────────┤
│ LAYER 3: Interactive Dynamic Mascot (MainDragonCompanion)   │
├─────────────────────────────────────────────────────────────┤
│ LAYER 2: Foreground World & Nodes (Trees, Path, Chests)     │
├─────────────────────────────────────────────────────────────┤
│ LAYER 1: Midground Scenery (Rolling Hills, Distant Castle)  │
├─────────────────────────────────────────────────────────────┤
│ LAYER 0: Ambient Sky Background (Gradient Canvas)           │
└─────────────────────────────────────────────────────────────┘
```

### Benefits of Layered Assembly:
1. Reusable assets across different screens and game modes.
2. Direct integration with Android dynamic light/dark theming and screen densities.
3. Smooth Compose hardware-accelerated animations (parallax scrolling, breathing loops, bouncing chests).
4. Zero text localization friction.

---

## 7. AI Generation Master Prompts & Quality Control

### 7.1 Master Asset Generation Prompt Template
```text
Create a production-ready 2D vector asset for Learn English: Grammar Games.

Asset Category: [CATEGORY, e.g., reward, environment, game]
Asset ID: [EXACT_ASSET_ID, e.g., chest_closed, game_crossword_art]
Functional Purpose: [SHORT DESCRIPTION OF USAGE]

Art Direction Rules (Mandatory):
- Modern clean 2D cartoon-vector style.
- Soft rounded geometric forms with organic, friendly curves.
- Clean, bold silhouette instantly readable at 64–96 dp.
- Flat color shapes enriched with subtle, clean vertical gradients.
- Soft upper-left lighting (10 o'clock sunlight angle).
- Delicate pill-shaped top highlights and soft self-shading.
- ABSOLUTELY ZERO BLACK OUTLINES. Use soft tinted boundary edges.
- NO realistic textures, NO dirt, NO grunge, NO noisy reptilian scales.

Palette: [SPECIFY APPLICABLE TOKENS, e.g., Royal Purple #6C5CE7, Gold #F1C40F, Emerald #58C96B]
Composition: Centered single object, balanced margins.
Perspective: Slight elevated 3/4 front view.
Background: 100% pure transparent background (isolated asset).

Do NOT include:
- Any letters, words, numerals, or baked-in text.
- Any buttons, progress bars, UI cards, or frames.
- Realistic lighting, 3D claymorphism, or anime styling.
```

### 7.2 Mandatory Negative Constraints (Negative Prompt)
```text
photorealistic, 3D CGI render, Blender, Maya, Pixar style, anime, manga, comic book ink,
heavy black outlines, sketch, pencil drawing, watercolor, grunge, dark fantasy, horror,
sharp edges, scary teeth, fangs, realistic textures, reptile scales, noisy gradients,
text, letters, words, alphabet, numbers, watermark, logo, UI buttons, solid background.
```

---

## 8. Asset Quality Assurance & Consistency Checklist

Before any newly generated or edited graphic asset is approved for check-in:

```text
┌───┬─────────────────────────────────────────────────────────┬─────────┐
│ # │ Quality Verification Check                              │ Status  │
├───┼─────────────────────────────────────────────────────────┼─────────┤
│ 1 │ Category & Naming: Matches lowercase snake_case standard│ [ ] PAS │
│ 2 │ Style Consistency: Strictly adheres to ART_DIRECTION.md │ [ ] PAS │
│ 3 │ Mascot Anatomy: Checked against CHARACTER_BIBLE.md      │ [ ] PAS │
│ 4 │ Palette Harmony: Uses authorized design tokens          │ [ ] PAS │
│ 5 │ Outline Policy: Zero black outlines                     │ [ ] PAS │
│ 6 │ Lighting Direction: Consistent soft upper-left light    │ [ ] PAS │
│ 7 │ Transparency: Clean alpha edges (no white/black halos)  │ [ ] PAS │
│ 8 │ Text Policy: ZERO baked-in English words or letters     │ [ ] PAS │
│ 9 │ UI Policy: ZERO baked-in buttons, cards, or checkboxes  │ [ ] PAS │
│ 10│ Small-Size Legibility: Clear silhouette at 48 / 64 dp   │ [ ] PAS │
│ 11│ Anti-Duplication: Verified that no identical asset exists│ [ ] PAS │
│ 12│ Format & Compression: Correct density and file size     │ [ ] PAS │
│ 13│ Android Runtime: Loads safely with fallback in Compose  │ [ ] PAS │
│ 14│ Registered: Added with complete metadata to Manifest    │ [ ] PAS │
└───┴─────────────────────────────────────────────────────────┴─────────┘
```

---

## 9. Master Asset Registry & Manifest Table

The following table tracks every canonical asset in the project. Any asset referenced in code must exist here:

| Asset ID | Category | Lifecycle Status | Reusable Scope | File Format | Transparent | Description & Specification |
|---|---|---|---|---|---|---|
| `main_dragon_idle` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot neutral 3/4 standing pose (Character Bible) |
| `main_dragon_happy` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot cheerful forward lean with wagging tail |
| `main_dragon_very_happy`| `character` | **approved** | Global | WebP / Compose | Yes | Mascot celebratory bounce with open arms |
| `main_dragon_thinking` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot touching chin, looking up-left thoughtfully |
| `main_dragon_celebrating`| `character`| **approved** | Global | WebP / Compose | Yes | Mascot dynamic victory jump, wings spread |
| `main_dragon_disappointed`|`character`| **approved** | Global | WebP / Compose | Yes | Mascot mild thoughtful pout for wrong answers |
| `main_dragon_encouraging`| `character`| **approved** | Global | WebP / Compose | Yes | Mascot with raised fist and confident smile |
| `main_dragon_reading` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot holding open purple grammar book |
| `main_dragon_writing` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot writing with chunky yellow pencil |
| `main_dragon_listening`| `character` | **approved** | Global | WebP / Compose | Yes | Mascot hand near ear, attentive expression |
| `main_dragon_walking` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot side-step walk cycle for Journey map |
| `main_dragon_running` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot dynamic running pose for Speed games |
| `main_dragon_jumping` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot jumping with tucked knees |
| `main_dragon_sleeping`| `character` | **approved** | Global | WebP / Compose | Yes | Mascot curled into peaceful sleep ball |
| `main_dragon_surprised`|`character` | **approved** | Global | WebP / Compose | Yes | Mascot wide eyes, hands on cheeks |
| `main_dragon_confused` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot cocked head and raised eyebrow |
| `main_dragon_proud` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot upright, hands on hips with smug smile |
| `main_dragon_waving` | `character` | **approved** | Global | WebP / Compose | Yes | Mascot waving open hand for Onboarding |
| `main_dragon_game_ready`|`character` | **approved** | Global | WebP / Compose | Yes | Mascot athletic stance ready for challenge |
| `bg_home_meadow` | `background`| **approved** | Screen | WebP / Canvas | No (Opaque)| Rolling sunny green hills with soft clouds |
| `bg_journey_valley` | `background`| **approved** | Screen | WebP / Canvas | No (Opaque)| Winding valley path toward distant fairytale castle |
| `bg_results_celebration`|`background`| **approved**| Screen | WebP / Canvas | No (Opaque)| Royal purple festive gradient with rays |
| `cloud_small_01` | `environment`| **approved** | Global | Vector / WebP | Yes | Small rounded fluffy white cloud with lavender base |
| `cloud_medium_01` | `environment`| **approved** | Global | Vector / WebP | Yes | Two-tiered puffy cloud |
| `cloud_wide_01` | `environment`| **approved** | Global | Vector / WebP | Yes | Elongated atmospheric background cloud |
| `tree_round_01` | `environment`| **approved** | Global | Vector / WebP | Yes | Large spherical green canopy, warm wood trunk |
| `tree_pine_01` | `environment`| **approved** | Global | Vector / WebP | Yes | Stylized triangular alpine evergreen |
| `bush_round_01` | `environment`| **approved** | Global | Vector / WebP | Yes | Compact emerald garden shrub |
| `castle_fairytale` | `environment`| **approved** | Global | Vector / WebP | Yes | Distant lavender fairytale castle landmark |
| `chest_closed` | `reward` | **approved** | Global | Vector / WebP | Yes | Fantasy timber chest with gold bands & lock |
| `chest_ready` | `reward` | **approved** | Global | Vector / WebP | Yes | Chest with golden glow, ready to tap |
| `chest_open` | `reward` | **approved** | Global | Vector / WebP | Yes | Open chest with radiant light & floating stars |
| `chest_claimed` | `reward` | **approved** | Global | Vector / WebP | Yes | Open empty chest resting peacefully |
| `xp_star` | `reward` | **approved** | Global | Vector (`.xml`)| Yes | Radiant five-pointed golden star of mastery |
| `coin_default` | `reward` | **approved** | Global | Vector (`.xml`)| Yes | Chunky golden game coin with embossed crest |
| `game_crossword_art` | `game` | **approved** | Games Hub | Vector / WebP | Yes | 3 interlocking tiles with pencil (no text) |
| `game_word_search_art`| `game` | **approved** | Games Hub | Vector / WebP | Yes | Golden magnifying glass over letter grid |
| `game_memory_art` | `game` | **approved** | Games Hub | Vector / WebP | Yes | Two flipping cards revealing matching stars |
| `game_battle_art` | `game` | **approved** | Games Hub | Vector / WebP | Yes | Magic grammar book with glowing shield |
| `game_speed_art` | `game` | **approved** | Games Hub | Vector / WebP | Yes | Golden stopwatch with speed trails |
| `game_sentence_art` | `game` | **approved** | Games Hub | Vector / WebP | Yes | Three interlocking colorful puzzle blocks |
| `fx_sparkle_gold` | `effect` | **approved** | Global | Vector (`.xml`)| Yes | 4-point diamond star sparkle in warm yellow |
| `fx_confetti_burst` | `effect` | **approved** | Global | Vector / Canvas| Yes | Floating festive diamond and ribbon particles |

---

## 10. Developer Architecture & Asset Resolver

To prevent runtime crashes and guarantee decoupling from string-based resource lookups, all graphic assets are accessible via strongly-typed resolvers:

```kotlin
// Type-safe asset references
val idleAsset = GraphicAssetResolver.resolveMascotPose(CharacterPose.IDLE)
val chestArt = GraphicAssetResolver.resolveRewardChest(ChestState.READY)
val gameArt = GraphicAssetResolver.resolveGameIcon(GameType.CROSSWORD)
```

- **Graceful Fallback**: If an asset is still being drawn, the resolver supplies a clean design-system placeholder (`ArtPlaceholder`), ensuring the application never crashes and TalkBack receives proper semantic descriptions.
