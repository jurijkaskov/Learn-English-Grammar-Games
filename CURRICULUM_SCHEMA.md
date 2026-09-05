# Curriculum Engine Schema & Reference

## Overview
The **Curriculum Engine** is the single source of truth for all grammar learning content in **Learn English: Grammar Games**. It provides a fully data-driven, schema-validated, offline-first content pipeline structured into clean architectural layers.

```
Assets (JSON)
      │
      ▼
CurriculumLoader (Parsing + Asset Resolution)
      │
      ▼
CurriculumValidator (Structural, Reference & Semantic Checks)
      │
      ▼
CurriculumMapper (DTO -> Pure Domain Entities)
      │
      ▼
CurriculumRepository & Use Cases
      │
      ▼
Jetpack Compose Features (Learn, Topic, Lesson, Games)
```

---

## Content Hierarchy
```
Course (e.g., intermediate_grammar)
  └── GrammarSection (e.g., sec_present_perfect)
        └── GrammarTopic (e.g., present_perfect_intro)
              └── Lesson (e.g., lesson_pp_form)
                    └── Activity (e.g., act_pp_form_mc_practice)
                          └── Question (e.g., q_pp_mc_001)
```

---

## Asset Directory Structure
All curriculum files live in `app/src/main/assets/curriculum/`:

```
assets/curriculum/
├── manifest.json
└── [course_id]/
    ├── course.json
    ├── sections.json
    ├── topics.json
    ├── lessons.json
    ├── activities.json
    └── questions/
        ├── [topic_group_1].json
        └── [topic_group_2].json
```

---

## Entities & JSON Schemas

### 1. `manifest.json`
Specifies curriculum version and active course paths:
```json
{
  "schemaVersion": 1,
  "contentVersion": 1,
  "courses": [
    "intermediate"
  ]
}
```

### 2. `course.json`
Describes the top-level course:
```json
{
  "id": "intermediate_grammar",
  "title": "Intermediate Grammar",
  "level": "INTERMEDIATE",
  "description": "Master essential complex tenses, modal nuances, conditionals, and perfect structures.",
  "order": 2,
  "sectionIds": [
    "sec_present_perfect"
  ],
  "isEnabled": true,
  "cefrLevel": "B1"
}
```

### 3. `sections.json`
List of sections within a course:
```json
[
  {
    "id": "sec_present_perfect",
    "courseId": "intermediate_grammar",
    "title": "Present Perfect & Past",
    "description": "Connecting past actions with present reality and mastering since, for, already, yet.",
    "order": 1,
    "topicIds": [
      "present_perfect_intro"
    ]
  }
]
```

### 4. `topics.json`
List of pedagogical topics:
```json
[
  {
    "id": "present_perfect_intro",
    "sectionId": "sec_present_perfect",
    "title": "Present Perfect: Form & Meaning",
    "shortDescription": "Connect past events to the present moment using have/has + past participle.",
    "order": 1,
    "lessonIds": [
      "lesson_pp_form",
      "lesson_pp_since_for"
    ],
    "prerequisites": [],
    "difficulty": "NORMAL",
    "cefrLevel": "B1",
    "conceptId": "concept_present_perfect",
    "bookReferences": [
      {
        "bookId": "murphy_in_use",
        "bookTitle": "English Grammar in Use (Murphy)",
        "edition": "5th Edition",
        "units": [7, 8, 11, 12]
      }
    ],
    "artworkId": "art_topic_valley",
    "status": "ACTIVE"
  }
]
```

### 5. `lessons.json`
Individual lessons within a topic:
```json
[
  {
    "id": "lesson_pp_form",
    "topicId": "present_perfect_intro",
    "title": "Form & Basic Meaning",
    "order": 1,
    "activityIds": [
      "act_pp_form_quick_lesson",
      "act_pp_form_mc_practice",
      "act_pp_form_gap_practice"
    ],
    "estimatedMinutes": 5,
    "difficulty": "NORMAL",
    "learningObjectives": [
      {
        "id": "obj_pp_identify_aux",
        "description": "Choose between have and has based on subject pronoun"
      }
    ]
  }
]
```

### 6. `activities.json`
Interactive and explanatory steps within a lesson:
```json
[
  {
    "id": "act_pp_form_quick_lesson",
    "lessonId": "lesson_pp_form",
    "type": "LESSON_CONTENT",
    "title": "Rule: Have/Has + Past Participle",
    "order": 1,
    "questionIds": [],
    "lessonContent": {
      "blocks": [
        {
          "type": "rule",
          "id": "blk_rule_1",
          "title": "Present Perfect Structure",
          "description": "We use the Present Perfect to talk about a past action that has a result in the present."
        },
        {
          "type": "formula",
          "id": "blk_form_1",
          "formulaPattern": "Subject + have/has + Past Participle (V3)",
          "formulaNote": "I/you/we/they have | he/she/it has"
        },
        {
          "type": "example",
          "id": "blk_ex_1",
          "sentence": "I have lost my key. (I can't open the door now)",
          "highlightedPart": "have lost",
          "translation": "Я потерял ключ."
        },
        {
          "type": "mistake",
          "id": "blk_mis_1",
          "incorrectSentence": "She have finished her project.",
          "correctSentence": "She has finished her project.",
          "mistakeExplanation": "With third-person singular (she/he/it), always use 'has', not 'have'."
        }
      ]
    }
  },
  {
    "id": "act_pp_form_mc_practice",
    "lessonId": "lesson_pp_form",
    "type": "MULTIPLE_CHOICE",
    "title": "Multiple Choice Practice",
    "order": 2,
    "questionIds": [
      "q_pp_mc_001",
      "q_pp_mc_002"
    ],
    "config": {
      "type": "practice",
      "shuffleQuestions": true,
      "allowRetry": true,
      "showInstantExplanation": true
    }
  }
]
```

---

## Question Types & Polymorphic Serialization
The engine supports 5 polymorphic question types under `QuestionDto` using `@SerialName`:

1. **`multiple_choice`**:
   - `options`: List of `{ "id": string, "text": string }`
   - `correctOptionId`: ID of the correct option
   - `shuffleOptions`: Boolean
2. **`gap_fill`**:
   - `sentenceWithGaps`: Sentence with `[gap]` markers
   - `correctAnswers`: List of acceptable strings for gaps
   - `optionsPool`: Distractor / option bank tokens
3. **`sentence_builder`**:
   - `segments`: Token fragments
   - `correctOrder`: Permutation indices `[0, 2, 1, 3]`
   - `distractors`: Unused distractors
4. **`find_mistake`**:
   - `sentenceParts`: Segments forming the sentence
   - `incorrectPartIndex`: Index of erroneous token
   - `correction`: Fixed replacement
5. **`true_false`**:
   - `statement`: String to evaluate
   - `isTrue`: Boolean

---

## Validation Engine
The static `CurriculumValidator` validates at build or load time:
1. **Uniqueness**: All IDs across all entities are globally unique.
2. **Referential Integrity**: Every foreign key (`courseId`, `sectionId`, `topicId`, `lessonId`, `questionId`, `prerequisites`) points to a valid entity.
3. **Acyclic Graphs**: Detects circular prerequisite dependencies.
4. **Pedagogical Integrity**: Questions have non-blank prompts, options, and valid correct answers.
5. **Type Compatibility**: Interactive activity types match their contained questions.
