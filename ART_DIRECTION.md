# Learn English: Grammar Games — Art Direction Specification

```text
================================================================================
IMPORTANT MANDATORY DIRECTIVE FOR ALL AI AGENTS & CONTRIBUTORS:

All newly generated or manually created visual assets for
Learn English: Grammar Games must follow this Art Direction.

Do not invent a new visual style for an individual feature.

If a feature requires an asset type that is not defined here,
extend this document while preserving the existing visual language.
================================================================================
STYLE DRIFT IS NOT ALLOWED.

Do not progressively change:
- rendering style,
- proportions,
- outline treatment,
- lighting,
- saturation,
- perspective,
- shadow treatment

between newly created assets. All artwork across all screens must belong
to the same cohesive visual world.
================================================================================
```

---

## 1. Visual Reference & Benchmark

The approved visual north-star for the project is the established **6-Screen Reference Concept**:
1. **Home**: Personal headquarters, welcoming hero mascot, daily streak, clean progress cards, and quick learning jump-in.
2. **Grammar Journey**: Rolling green landscape, winding stone path, game-like lesson nodes, distant fairytale castle, and modular nature assets.
3. **Topic Overview**: Clean syllabus overview framed with thematic region banners.
4. **Games Arena**: Vibrant game mode cards featuring consistent fantasy-playful iconography.
5. **Exercise / Drill**: High-contrast, hyper-legible learning surface with clean answer cards and supportive companion feedback.
6. **Results & Celebration**: Festive victory panel, golden XP stars, celebratory confetti, and unlocked treasure chests.

### Benchmark Characteristics:
- Bright, sunny, optimistic cartoon landscape.
- Playful fantasy adventure world with an educational soul.
- Signature royal educational purple brand UI (`#6C5CE7` light, `#8875FF` dark).
- Soft rounded cards and tactile interactive feedback.
- Clean vector rendering without visual noise or clashing styles.

---

## 2. Character Reference & Character Bible Integration

> **CANONICAL REFERENCE**: The visual identity, silhouette, proportions, facial anatomy,
> palette tokens, pose library, and prompt specifications for the main green dragon companion
> are formally locked in `/CHARACTER_BIBLE.md`.
>
> All character representations must adhere to both documents:
> - **CHARACTER_BIBLE.md** defines **WHO** the character is (anatomy, palette, proportions, poses).
> - **ART_DIRECTION.md** defines **HOW** the character is rendered (2D vector, lighting, edge separation).
> - Production screens use `MainDragonCompanion` and `CharacterPose` for type-safe mascot rendering.

---

## 3. Core Art Direction

### Style Name: **Bright Modern Cartoon Vector Adventure**

A modern, polished 2D cartoon aesthetic characterized by clean vector-like geometry, organic curves, vibrant optimism, and premium mobile-game craft.

- **Age Tone**: **Ages 8+**. Friendly and inviting for children, but clean, structured, and modern enough that adult learners never feel infantilized.
- **Atmosphere**: Whimsical fantasy adventure meets modern educational clarity.

### The 70 / 20 / 10 Visual Formula

Every screen must strictly balance three aesthetic layers:

```text
┌─────────────────────────────────────────────────────────────┐
│  70%  Clean Modern Educational UI                           │
│       - Legible typography, M3 surface cards, answer choices │
│       - Clear grammar rules, high-contrast text              │
├─────────────────────────────────────────────────────────────┤
│  20%  Cartoon Fantasy Adventure Environment                 │
│       - Soft rolling hills, sunny skies, castles, paths     │
│       - Companion hero reactions and world landmarks        │
├─────────────────────────────────────────────────────────────┤
│  10%  Playful Game Decoration & FX                          │
│       - Floating sparkles, gold stars, treasure chests      │
│       - Celebration confetti and reward badges              │
└─────────────────────────────────────────────────────────────┘
```

**Educational Primacy**: Art must **never** compete with or impede reading grammar formulas, test questions, explanation cards, or action buttons.

---

## 4. Forbidden Art Styles (Strictly Enforced)

The following styles and treatments are **strictly prohibited** in any part of the application:

- ❌ **Photorealism & Stock Photos**: No realistic textures, stock photography, or real-life imagery.
- ❌ **3D Renders & Claymorphism**: No Blender/Maya 3D models, clay 3D, or Pixar-style raytracing.
- ❌ **Anime & Manga**: No Japanese animation styles, large spiked hair, or anime faces.
- ❌ **Painterly Styles**: No watercolor, oil painting, chalk, pastel, or brush-stroke splatters.
- ❌ **Pixel Art & Retro Sprites**: No 8-bit, 16-bit, or pixelated retro aesthetics.
- ❌ **Sketch & Halftone**: No pencil sketches, ink cross-hatching, or comic-book Ben-Day dots.
- ❌ **Dark / Gothic / Cyberpunk**: No dark fantasy, neon sci-fi, horror, grunge, or dystopian themes.
- ❌ **Hyper-Detailed Environments**: No cluttered, multi-layered foliage with hundreds of leaves.
- ❌ **Raw Emojis as Core Artwork**: Never use system emojis as primary game illustrations.
- ❌ **Style Mixing**: Never mix 2D vector characters with 3D props or photo backgrounds.

---

## 5. World Geometry & Silhouette Language

All physical forms in the world follow a **chunky, rounded, and soft** design vocabulary:

| Element | Geometry Rule | Prohibited Treatment |
|---|---|---|
| **Trees** | 2–3 stacked rounded foliage spheres; short visible tapered trunk; minimal canopy cuts | Spindly branches, thousands of individual leaves |
| **Clouds** | Puffy silhouettes formed by 3–6 overlapping round lobes with a flat base | Wispy cirrus lines, realistic weather clouds |
| **Hills** | Continuous smooth curves with gentle parabolic rolling crests | Jagged rocks, steep razor-edged cliffs |
| **Rocks** | Smooth river-pebble stones with rounded corners | Sharp polygonal boulders with cracks |
| **Mountains** | Broad triangular silhouettes with softened, rounded summits | Harsh crags, hyper-detailed crevices |
| **Castles** | Cylindrical towers, conical or domed roofs, chunky stone masonry | Spiky gothic spires, intimidating fortresses |
| **Paths** | Organic ribbon curves with soft edges, guiding vertical eye motion | Rigid straight lines, asphalt roads |

---

## 6. Rendering Style & Lighting Model

The rendering is **simplified 2D with soft gentle volume**:

```text
Flat Base Color
      +
Very Soft Vertical Gradient (Lighter top, deeper base)
      +
Subtle Curved Edge Shadow (Self-shading)
      +
Occasional Pill-Shaped Highlight Reflection
```

- **Lighting**: Imaginary warm sun positioned top-left (10 o'clock).
- **Highlights**: Soft, rounded white/cream pill reflections with 20%–40% opacity on shiny surfaces (coins, stars, bubbles, gems).
- **Shadows**: Soft, slightly desaturated cool tones (never muddy black or harsh drop shadows).
- **Textures**: Absolutely **zero** noisy bitmap textures (no bark grain, no rough stone noise, no canvas weave).

---

## 7. Outline Policy

- **Primary Rule**: **No black outlines**.
- **Edge Separation**:
  - Prefer **zero outline** using clean contrast between adjacent color masses.
  - When visual separation is needed (e.g., green foliage against green hills), use a **soft, tinted outline** (1.5dp–2.5dp) matching the element's shadow tone (e.g., darker emerald green around mint foliage).
  - Heavy black comic-book outlines are strictly forbidden.

---

## 8. Color Harmony & Palette Architecture

The art palette derives directly from the core **Design System** (`MaterialTheme.grammarGamesColors`):

| World Domain | Primary Hue | Shadow / Depth Hue | Highlight Accent |
|---|---|---|---|
| **Sky (Day)** | Light Sky Blue `#70C1FF` to `#BFE3FF` | Cyan Mist `#EBF5FB` | Pure White `#FFFFFF` |
| **Grass & Plains** | Soft Emerald `#2ECC71` / `#00B894` | Forest Teal `#13382E` | Pale Lime `#7DDF9C` |
| **Trees & Foliage** | Rich Leaf Green `#27AE60` | Deep Pine `#1B4D3E` | Spring Mint `#A3E4D7` |
| **Distant Mountains**| Soft Lavender `#A29BFE` / `#8875FF` | Slate Violet `#5C527F` | Snow Cap Cream `#F8F9FA` |
| **Fairytale Castle** | Warm Cream Stone `#F7F1E3` | Lavender Slate `#D1D8E0` | Royal Purple Roofs `#6C5CE7` |
| **Paths & Earth** | Warm Biscuit Tan `#F5CD79` | Soft Terracotta `#E67E22` | Sand Pebble `#F8EFBA` |
| **Wood & Props** | Warm Milk-Chocolate `#8D6E63` | Dark Walnut `#4E342E` | Golden Timber `#D7CCC8` |
| **Rewards & XP** | Warm Gold `#F1C40F` / `#FFD700` | Deep Amber `#F39C12` | Sparkling Star `#FFF9C4` |

---

## 9. Depth Layers & Z-Order Architecture

Large landscape and narrative screens (such as **Grammar Journey** and **Home**) are constructed using a strict 5-layer hierarchy:

```text
Z-Index 5: [Interactive UI Layer]      Touch buttons, lesson nodes, HUD, dialogs, TopAppBar
Z-Index 4: [Character & Actors]        Hero companion mascot, animated reactions
Z-Index 3: [Foreground Decoration]    Grass tufts, flowers, signposts, small pebbles, chests
Z-Index 2: [Midground Environment]    Winding path, rolling hills, primary trees, castle
Z-Index 1: [Far Background]           Distant mountains, silhouette castles, tree lines
Z-Index 0: [Sky Canvas]               Sky gradient, soft multi-lobe clouds, warm sun
```

### Contrast & Saturation Rule:
- **Foreground & UI**: Highest contrast, sharpest outlines, vibrant saturation.
- **Midground**: Moderate saturation and balanced detail.
- **Background**: Low saturation, softened contrast, subtle atmospheric lavender/blue haze.

---

## 10. Environment Asset Catalog

### 10.1. Clouds
- **Shapes**: 4 standard modular silhouettes (`cloud_small_01`, `cloud_medium_01`, `cloud_wide_01`, `cloud_long_01`).
- **Styling**: Pure white upper puffs with gentle soft-blue underside shading (`#E0EBF5`).
- **Composition**: 3–6 overlapping circles with a flat bottom shelf.

### 10.2. Trees & Vegetation
- **Tree Families**:
  1. `tree_round_01..03`: Standard three-bubble crown.
  2. `tree_tall_01..02`: Upright pill-shaped canopy.
  3. `tree_pine_01..02`: 3 stacked rounded cones (evergreen).
  4. `tree_fantasy_01`: Giant lavender/purple canopy milestone tree.
  5. `bush_small_01..02`: Low two-lobe rounded shrub.
- **Vegetation Accents**: Small 3-blade grass tufts, simple 5-petal daisies (yellow center, white petals), small red forest mushrooms with rounded white spots.

### 10.3. Mountains & Peaks
- Broad pastel triangles with rounded vertices.
- Shaded using a soft vertical gradient transitioning from midground green into background lavender.
- Minimal snow-caps rendered as rounded frosting caps.

### 10.4. Castles & Landmarks
- **Concept**: Friendly medieval fairytale castle acting as a unit/world milestone.
- **Features**: Round towers with brick pattern accents (2–3 stones drawn, not full masonry), conical royal purple roofs, golden triangular pennants, and arched portal doors.
- **Mood**: Welcoming and inspiring, never dark or ominous.

### 10.5. Paths & Trails
- Smooth continuous ribbons winding between lesson nodes.
- Subtle stone tile stepping stones or sandy dirt trail with soft rounded edge stones.
- Clear visual hierarchy guiding the learner's upward journey.

---

## 11. Interactive World Elements & Lesson Nodes

Lesson nodes in Grammar Journey are physical objects embedded into the landscape:

```text
┌─────────────────┬────────────────────────────────────────┬──────────────────────┐
│ State           │ Visual Appearance                      │ Auxiliary Semantic   │
├─────────────────┼────────────────────────────────────────┼──────────────────────┤
│ LOCKED          │ Soft slate gray stone, muted lock icon │ Lock symbol          │
│ AVAILABLE       │ Ocean blue container, white unit number│ Number + bounce hint │
│ CURRENT         │ Royal purple glow, enlarged node, gold │ Mascot stands nearby │
│                 │ ring border, pulsing indicator        │                      │
│ COMPLETED       │ Emerald green stone, crisp white check │ 1–3 golden stars     │
│ MASTERED        │ Golden ring, crown accent, 3 stars     │ Sparkle particles    │
└─────────────────┴────────────────────────────────────────┴──────────────────────┘
```

---

## 12. Gamification & Reward Assets

### 12.1. Treasure Chests
- **Structure**: Chunky arched-top chest crafted from warm milk-chocolate wood planks with golden metal corner brackets and an oversized rounded lock.
- **States**:
  - `chest_closed`: Locked with keyhole.
  - `chest_ready`: Pulsing golden aura and soft bounce animation.
  - `chest_opening`: Lid tilting upward, releasing golden light beams.
  - `chest_opened`: Fully open lid, empty interior with sparkles.

### 12.2. Coins vs. XP Stars
- **XP**: Stylized 5-pointed rounded gold star with a central circular sparkle (`xp_star`). Associated with study progress and level advancement.
- **Coins**: Round gold medallion with an embossed star or book crest and a bevel edge (`currency_coin`). Associated with shop/wardrobe unlockables.
- **Visual Separation**: XP is star-shaped; currency is round.

### 12.3. Confetti & Sparkles
- **Confetti**: Clean geometric rectangles and diamond flakes in 5 theme colors (purple, mint, gold, sky blue, coral). Triggered **only** on genuine milestones (unit completion, level up, test passed).
- **Sparkles**: 4-pointed diamond glints and small circular particles decorating rare achievements and golden chests.

---

## 13. Game Arena Art Styles

Every mini-game shares the same world art direction while adapting to specific mechanic boards:

| Mini-Game | Visual Board Environment | Decor Elements |
|---|---|---|
| **Speed Tenses** | Circular countdown timer with dynamic color arc, stadium banner | Speed wings, cheering mascot |
| **Sentence Architect** | Floating word tiles with rounded pill shapes, snapped magnet slots | Construction flags, path bridge |
| **Detective: Spot Error** | Clean magnifying glass motif, subtle notepad parchment card | Detective hat accessory, footprints |
| **Grammar Battle** | Friendly magical arena, guardian creature reacting to answers | Shield shields, magic sparks (zero violence) |
| **Grammar Maze** | Grassy pathway maze with stones, flower bushes, opened gates | Signposts, bridge crossings |
| **Word Search** | High-contrast pastel letter grid, rounded highlight strokes | Exploration compass, field map |
| **Crossword** | Clean cream grid, rounded cells with purple selection indicator | Quill pen, floating alphabet cloud |
| **Memory Match** | Chunky purple card backs with embossed golden book insignia | Smooth 3D-feel card flip |
| **Balloon Pop** | Glossy pastel cartoon balloons drifting across sunny sky | Floating basket, soft wind gusts |

---

## 14. Screen-by-Screen Art Specification

### 14.1. Screen Density Matrix
To protect educational focus, illustration density is strictly calibrated:

```text
HIGH DENSITY (60%–80% art coverage):
- Grammar Journey Map
- Onboarding Welcome & Completion
- Results & Celebration Screens
- Chapter / Milestone Events

MEDIUM DENSITY (20%–40% art coverage):
- Home Dashboard (Header landscape & hero companion)
- Games Arena Hub (Illustrative game cards)
- Achievements Gallery (Collector badge book)
- Learner Profile & Companion Wardrobe

LOW DENSITY (0%–15% art coverage):
- Grammar Lesson / Rule Reading (Clean white/cream paper cards)
- Practice Drills & Exercises (Focus on options and sentence)
- Topic Evaluation Tests (Strict focus, distraction-free)
- Settings & App Preferences (Pure utility)
```

---

## 15. Composition Diagrams (ASCII Schematics)

### Home Screen Composition
```text
┌────────────────────────────────────────────────────────┐
│ [TopBar: Level 5 Badge       Streak 🔥 7    Hearts ❤️ 5]│
├────────────────────────────────────────────────────────┤
│ [Illustrated Scenic Header]                            │
│  ☁️   Sunny Sky Canvas                  ☁️             │
│        🏔️ Distant Lavender Mountain                    │
│      [Hero Companion Silhouette] 👈 Friendly greeting  │
│      "Ready to master Present Perfect, Alex?"          │
├────────────────────────────────────────────────────────┤
│ [Continue Learning Primary Card]                       │
│  Present Perfect: Experiences                          │
│  Progress: [██████████░░░] 70%     [ Continue CTA ]    │
├────────────────────────────────────────────────────────┤
│ [Daily Quest Card]                                     │
│  ⭐ Complete 2 Lessons  (1/2)                          │
├────────────────────────────────────────────────────────┤
│ [Games & Review Shortcuts]                             │
│  [ Speed Arena ]      [ Mistake Notebook (3) ]         │
├────────────────────────────────────────────────────────┤
│ [Bottom Navigation Bar: Home, Learn, Games, Rev, Prof] │
└────────────────────────────────────────────────────────┘
```

### Grammar Journey Composition
```text
┌────────────────────────────────────────────────────────┐
│ [TopBar: Unit 2 — Past & Present Tenses       ⚙️ Back] │
├────────────────────────────────────────────────────────┤
│  ☁️                      🏰 Castle Landmark (Boss Test)│
│                            │                           │
│                       (5) [Locked Node]                │
│                            │                           │
│   🌳 Tree             (4) [Locked Node]      🎁 Chest  │
│        \                   /                           │
│         (3) [Current Node ⭐] 👈 [Hero Stands Here]   │
│              \                                         │
│         (2) [Completed Node ✅]     🌸 Flowers         │
│              /                                         │
│   (1) [Completed Node ✅]                              │
│        /                                               │
│  [Winding Sand Path]               🌲 Pine Tree        │
├────────────────────────────────────────────────────────┤
│ [Bottom Navigation Bar]                                │
└────────────────────────────────────────────────────────┘
```

### Results & Celebration Composition
```text
┌────────────────────────────────────────────────────────┐
│  ✨ 🎊                CELEBRATION                🎊 ✨ │
│                                                        │
│               [Celebratory Hero Mascot]                │
│                     ⭐⭐⭐                             │
│                  "Outstanding!"                        │
│                                                        │
│  ┌──────────────────────────────────────────────────┐  │
│  │ Score: 10 / 10 Perfect                           │  │
│  │ +60 XP Earned          🎁 Bonus Chest Unlocked   │  │
│  │ Topic Mastery: 65% ➔ 82%                         │  │
│  └──────────────────────────────────────────────────┘  │
│                                                        │
│  [ GrammarPrimaryButton: "Continue to Next Lesson" ]   │
│  [ GrammarSecondaryButton: "Review Mistakes" ]         │
└────────────────────────────────────────────────────────┘
```

---

## 16. Asset Organization & Naming Conventions

### 16.1. Directory Structure (`res/drawable/` or asset pipeline)
```text
res/drawable/
├── art_env_cloud_small_01.xml
├── art_env_cloud_wide_01.xml
├── art_env_tree_round_01.xml
├── art_env_tree_tall_01.xml
├── art_env_mountain_distant_01.xml
├── art_env_castle_landmark_01.xml
├── art_env_path_curve_01.xml
├── art_prop_chest_closed.xml
├── art_prop_chest_ready.xml
├── art_prop_chest_opened.xml
├── art_prop_coin_gold.xml
├── art_prop_star_xp.xml
├── art_game_speed_tenses.xml
├── art_game_sentence_builder.xml
└── art_game_error_spotter.xml
```

### 16.2. Strict File Naming Grammar
Format: `art_<category>_<name>_<state/variant>.<ext>`
- `art_env_` : Environment elements (clouds, trees, hills, castle).
- `art_prop_`: Physical game world items (chests, signposts, coins, stars).
- `art_game_`: Mini-game promotional tile art and banners.
- `art_badge_`: Milestone and achievement medals.

❌ Prohibited names: `image1.png`, `new_tree.webp`, `pic_final.png`, `asset.xml`.

---

## 17. AI Generation Rules & Prompt Engineering

When using generative AI tools (`generate_image`) to produce future assets, every prompt **MUST** follow this structured template:

### AI Prompt Template:
```text
Clean modern 2D cartoon vector illustration of [OBJECT/SCENE DESCRIPTION]
for the Learn English: Grammar Games mobile educational application.
Style constraints:
- Soft rounded silhouettes, chunky organic curves, simplified forms.
- Flat color fields with very soft vertical gradients and subtle self-shading.
- Lighter top surface with a delicate highlight glint, darker lower edge.
- Absolutely NO black outlines; subtle tinted edge shading only.
- Bright, cheerful, and optimistic palette (royal purple, emerald green, sky blue, warm gold).
- Friendly 2D fantasy adventure atmosphere, educational quality, mobile game asset.
- Isolated object on a pure transparent background.
- High visual legibility at small mobile screen sizes.
```

### Mandatory Negative Constraints (Negative Prompt):
```text
photorealistic, 3D render, realistic lighting, raytracing, anime, manga,
pixel art, watercolor, sketch, pencil, halftone, comic book, dark fantasy,
gothic, cyberpunk, neon, realistic wood grain, rough textures, messy details,
words, letters, text, numbers, logo, watermark, UI buttons, borders, frames.
```

---

## 18. Critical Technical & Production Rules

1. **NO TEXT Baked into Illustrations**:
   - Never generate or bake English words, labels, titles, or scores into artwork.
   - All text must be rendered by Jetpack Compose typography for accessibility, dynamic font scaling, and international localization.

2. **NO UI Controls Baked into Artwork**:
   - Never bake buttons, checkboxes, progress bars, or card frames into image assets.
   - Artwork serves strictly as backdrop or illustration; UI components remain native Compose components.

3. **Safe Zones & Device Scalability**:
   - Key focal elements must maintain a **16dp–24dp safety margin** from screen edges to avoid obstruction by camera cutouts, Android system bars, or navigation bars.
   - Backgrounds must gracefully scale using `ContentScale.Crop` or modular tiling without clipping critical landmarks.

4. **Performance & 60 FPS Target**:
   - Prefer `VectorDrawable` (XML) for icons, nodes, clouds, and recurring props.
   - Use compressed WebP for complex painted backgrounds.
   - Maximum texture dimension for mobile assets: 1080x1920 for full-bleed backgrounds, 512x512 for large props, 256x256 for cards.
   - Zero heavy runtime blur shaders; all soft shadows pre-baked into vectors or rendered via standard Compose shadows.

5. **Accessibility Integration**:
   - Purely decorative illustrations must set `contentDescription = null`.
   - Interactive elements with art backdrops (e.g., lesson nodes) must expose proper Compose semantics (`testTag`, state labels, TalkBack roles).

6. **Dark Theme Integration**:
   - UI surfaces automatically invert to dark container roles (`grammarGamesColors`).
   - Illustrated landscape backgrounds receive a subtle cool midnight overlay (`Color(0xFF0F1424).copy(alpha = 0.55f)`) or load a dedicated night sky variant (`art_bg_journey_night`).

---

## 19. Relationship Between Design System and Art Direction

```text
┌────────────────────────────────────────────────────────┐
│                   DESIGN_SYSTEM.md                     │
│  - UI Components: Buttons, Cards, Dialogs, HUD         │
│  - Color Tokens & M3 Schemes (Light / Dark)           │
│  - Typography Scale, Line Heights, Letter Spacing      │
│  - Spacing (AppSpacing), Shapes (AppShapes), Dimens    │
│  - Pedagogical & Interactive States                    │
└──────────────────────────┬─────────────────────────────┘
                           │ Complements & Frames
                           ▼
┌────────────────────────────────────────────────────────┐
│                   ART_DIRECTION.md                     │
│  - Illustrative Style & Visual World Definition        │
│  - Environment Assets: Clouds, Trees, Hills, Castles   │
│  - Game World Geometry, Shading & Outline Rules        │
│  - Map Nodes, Path Guides, Treasure Chests, Rewards    │
│  - Screen Art Density, Layering, AI Generation Rules   │
└────────────────────────────────────────────────────────┘
```

Both documents operate together in complete harmony: `DESIGN_SYSTEM.md` governs **how the user interacts**, while `ART_DIRECTION.md` governs **the magical world they explore**.
