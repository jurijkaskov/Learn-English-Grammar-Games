# Mastery Model (0–100% Grammar Proficiency System)

## 1. Executive Summary & Core Philosophy

> **"Mastery is a measure of genuine competence and retained skill, not content consumption."**

In standard language learning applications, "progress" often measures merely whether a user tapped through a lesson or visited a screen. In contrast, the **Mastery Model** in *Learn English: Grammar Games* provides a deterministic, evidence-based assessment of a learner's actual grammatical ability on a granular **0–100% scale**.

### Core Tenets
1. **Evidence-Driven**: Mastery cannot increase without demonstrated performance on authentic assessment items.
2. **Confidence-Scaled**: A single lucky correct answer does not equal mastery. Confidence scales smoothly with sample size and consistency.
3. **Cognitive Weighting**: Items are weighted by cognitive depth and difficulty (`EASY`, `NORMAL`, `HARD`, `MASTERY`).
4. **Hint & Assistance Penalties**: Using hints or scaffolding reduces the proficiency credit earned on an attempt.
5. **Spaced Retention & Decay**: Grammar skills experience decay over time when inactive (modelled after Ebbinghaus' forgetting curve), transitioning from `MASTERED` or `PROFICIENT` to `NEEDS_REVIEW`.
6. **Hierarchical Aggregation**: Atomic question attempts feed into granular **Skills / Objectives**, which aggregate into **Topic Mastery**, which rolls up into **Section Mastery**, and finally **Course Mastery**.
7. **Transparent & Explainable**: Every mastery score can be explained to both developers and learners ("Why is my mastery 72%?").
8. **Offline-First & Deterministic**: The entire calculation is local, deterministic, versioned, and backed by Room persistence.

---

## 2. Mastery Levels & Status Lifecycle

Mastery score is a normalized integer from **0 to 100**. It maps to discrete status levels:

| Score Range | Status | Description | User Interface Badge |
|---|---|---|---|
| **0%** | `NOT_STARTED` | No evidence recorded yet. | Gray "Not Started" |
| **1% – 39%** | `INTRODUCED` | Initial exposure; basic pattern recognition emerging. | Blue "Introduced" |
| **40% – 69%** | `PRACTICING` | Consistent active practice; errors still occur on edge cases. | Amber "Practicing" |
| **70% – 84%** | `PROFICIENT` | Solid grammatical competence, reliable in standard contexts. | Teal "Proficient" |
| **85% – 100%** | `MASTERED` | Fluent, automatic application across varied difficulties with high consistency. | Purple / Gold "Mastered" |
| *Decayed < 70%* | `NEEDS_REVIEW` | Previously proficient/mastered, but inactive beyond retention threshold. | Orange "Needs Review" |

### State Transitions
```
                [Not Started (0%)]
                        │ (First Attempt)
                        ▼
               [Introduced (1–39%)]
                        │ (Accurate Practice)
                        ▼
               [Practicing (40–69%)]
                        │ (High Accuracy + Confidence)
                        ▼
               [Proficient (70–84%)]
                        │ (Mastery-Level Accuracy)
                        ▼
               [Mastered (85–100%)]
                        │
       (Inactive > 7 days without refresh)
                        ▼
              [Needs Review (< 70%)]
                        │ (Fresh Practice)
                        ▼
               [Proficient / Mastered]
```

---

## 3. Granular Dimensions (The Hierarchy)

```
Course (e.g., Intermediate English, B1-B2)
  └── Section (e.g., Present Perfect & Past)
        └── Topic (e.g., Present Perfect Simple)
              └── Skill / Objective (e.g., Formation, Questions, Since vs For)
                    └── Question Attempt (Atomic Evidence)
```

### 1. Atomic Evidence: `QuestionAttempt`
Every response to an activity question produces an immutable attempt record:
- `questionId`: Identifier of the question.
- `topicId`: Associated topic.
- `skillId`: Associated learning objective / skill dimension.
- `isCorrect`: Boolean outcome.
- `difficulty`: `EASY` (0.8x), `NORMAL` (1.0x), `HARD` (1.3x), `MASTERY` (1.5x).
- `hintsUsed`: Count of hints or assistance used (penalizes score).
- `timeSpentMs`: Duration taken.
- `timestamp`: Epoch milliseconds.

### 2. Dimension Level: `MasterySkill`
Each Topic contains 2 to 5 targeted skills derived directly from the curriculum's `LearningObjective` catalog:
- Avoids duplicate curriculum definitions.
- Has an explicit pedagogical weight (normalized to sum to 1.0).
- Maps question tags and objective IDs to discrete learner competencies.

### 3. Topic Level: `TopicMastery`
- Weighted sum of its constituent `MasterySkill` scores:
  $$\text{TopicScore} = \sum_{i=1}^{n} (\text{SkillScore}_i \times \text{NormalizedWeight}_i)$$
- Displays status badge, progress bar, and granular skill breakdown.

### 4. Section Level: `SectionMastery`
- Arithmetic mean of active topics within the section:
  $$\text{SectionScore} = \frac{1}{|T|} \sum_{t \in T} \text{TopicScore}_t$$

### 5. Course Level: `CourseMastery`
- Arithmetic mean of active sections within the course:
  $$\text{CourseScore} = \frac{1}{|S|} \sum_{s \in S} \text{SectionScore}_s$$

---

## 4. The Mastery Algorithm (`MasteryEngine v1`)

The algorithm is fully deterministic and versioned (`ALGORITHM_VERSION = 1`).

### Step 1: Effective Attempt Value ($V_k$)
For each attempt $k$:
$$V_k = \text{BaseCredit}_k \times \text{DifficultyWeight}_k \times \text{HintPenalty}_k$$

Where:
- $\text{BaseCredit}_k = 1.0$ if correct, $0.0$ if incorrect.
- $\text{DifficultyWeight}_k$:
  - `EASY`: $0.8$
  - `NORMAL`: $1.0$
  - `HARD`: $1.25$
  - `MASTERY`: $1.4$
- $\text{HintPenalty}_k$:
  - 0 hints: $1.0$ (no penalty)
  - 1 hint: $0.75$ (25% penalty)
  - 2+ hints: $0.50$ (50% penalty)

### Step 2: Recency Weighting ($W_k$)
Recent attempts provide stronger signal than older attempts. Using an exponential smoothing window over the last $N$ attempts (up to 20):
$$W_k = \gamma^{(N - k)}, \quad \text{with } \gamma = 0.92$$
The weighted performance ratio is:
$$\text{WeightedPerformance} = \frac{\sum_{k=1}^{N} V_k \times W_k}{\sum_{k=1}^{N} \text{MaxPossible}_k \times W_k}$$

### Step 3: Confidence Factor ($C$)
To prevent lucky guesses from inflating scores:
$$C(n) = \min\left(1.0, \sqrt{\frac{n}{K_{\text{threshold}}}}\right)$$
Where $K_{\text{threshold}} = 6$ attempts.
- At 1 attempt: $C \approx 0.408 \implies$ maximum achievable mastery $\approx 41\%$.
- At 3 attempts: $C \approx 0.707 \implies$ maximum achievable mastery $\approx 71\%$.
- At 6+ attempts: $C = 1.0 \implies$ full 100% scale unlocked.

### Step 4: Time-Based Retention Decay ($D$)
Grammar competence requires spaced practice.
- **Grace Period**: 7 days from the last practice timestamp.
- **Decay Rate**: Exponential decay with a half-life of 28 days beyond the grace period.
- **Retention Floor**: Minimum retention floor of $0.50$ (50% of earned proficiency retained permanently).

$$D(\Delta t) = \begin{cases}
1.0 & \text{if } \Delta t \le 7 \text{ days} \\
0.50 + 0.50 \times e^{-\lambda (\Delta t - 7)} & \text{if } \Delta t > 7 \text{ days}
\end{cases}$$
Where $\lambda = \frac{\ln(2)}{28} \approx 0.02475 \text{ day}^{-1}$.

### Step 5: Final Dimension Score
$$\text{RawScore} = \text{WeightedPerformance} \times C \times 100$$
$$\text{FinalScore} = \text{round}(\text{RawScore} \times D)$$
Clamped strictly to $[0, 100]$.

---

## 5. Storage Schema & Room Migration (v1 → v2)

### Tables
1. `user_progress` (Existing, preserved):
   - `id`, `totalXp`, `level`, `streakDays`, `lastActiveTimestamp`
2. `skill_mastery` (New):
   - `skillId` (PK), `topicId`, `score`, `rawAccuracy`, `totalAttempts`, `successfulAttempts`, `confidence`, `lastPracticedTimestamp`, `status`, `decayFactor`, `algorithmVersion`
3. `topic_mastery` (New):
   - `topicId` (PK), `score`, `status`, `skillsCount`, `masteredSkillsCount`, `lastPracticedTimestamp`
4. `question_attempts` (New):
   - `id` (PK), `questionId`, `topicId`, `skillId`, `isCorrect`, `difficulty`, `hintsUsed`, `timeSpentMs`, `timestamp`

### Indexes
- `index_skill_mastery_topicId` on `skill_mastery(topicId)`
- `index_question_attempts_topicId` on `question_attempts(topicId)`
- `index_question_attempts_skillId` on `question_attempts(skillId)`

---

## 6. Explainability Breakdown

For any computed score, the system exposes an explainable `MasteryExplanation`:
```kotlin
data class MasteryExplanation(
    val baseAccuracy: Float,        // e.g., 0.88 (88%)
    val confidenceFactor: Float,    // e.g., 0.91 (based on 5 attempts)
    val averageDifficultyWeight: Float, // e.g., 1.05
    val hintPenaltyFactor: Float,   // e.g., 0.95
    val decayFactor: Float,         // e.g., 0.94 (decay after 10 days inactive)
    val finalScore: Int,            // e.g., 74
    val summaryText: String         // "74% Mastery: 88% accuracy across 5 attempts with 91% confidence, -6% time decay applied."
)
```

---

## 7. Developer & Inspector Tooling

1. **Mastery Tab in Curriculum Inspector**:
   - Course, Section, and Topic selector.
   - Live Topic score and status badge.
   - Skill breakdown with progress bars and weights.
   - Diagnostic explainability modal / card.
2. **Interactive Simulation Controls**:
   - `[+ Correct Attempt]`: Records a simulated successful attempt.
   - `[- Incorrect Attempt]`: Records a simulated mistake.
   - `[Simulate Hint]`: Records an attempt with hint penalty.
   - `[Simulate 14-Day Inactivity]`: Advances decay time to verify decay to `NEEDS_REVIEW`.
   - `[Reset Topic]`: Clears attempts and resets topic mastery to 0%.
