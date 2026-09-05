# Content Authoring Guide

## Purpose
This document provides guidelines for pedagogical content creators, curriculum authors, and translators adding grammar content to **Learn English: Grammar Games**.

---

## 1. Directory Conventions
When adding a new grammar topic or expanding a course:
1. Locate the target course under `app/src/main/assets/curriculum/[course_name]/`.
2. Add or update the topic in `topics.json`.
3. Add the lessons in `lessons.json`.
4. Add the activities in `activities.json`.
5. Create or append question JSON files in `questions/[topic_name].json`.

---

## 2. Best Practices for Question Creation

### Multiple Choice Questions
- Keep options concise (1–4 words).
- Provide plausible distractors that target real learner misconceptions (e.g. using `have` instead of `has`).
- Always write a clear, friendly `explanation` starting with the core grammar rule.
- Hints should guide the user without revealing the direct answer.

### Gap Fill Questions
- Mark gaps clearly using brackets `[answer]`.
- Keep the sentence natural and colloquial.
- Include distractor words in `optionsPool` when creating beginner / scaffolded exercises.

### Sentence Builder
- Break sentences into natural constituent chunks (e.g., `["She", "has lived", "here", "for ten years"]`).
- Avoid single-punctuation fragments unless specifically testing punctuation rules.

---

## 3. Lesson Content Blocks
When writing `LESSON_CONTENT` activities:
- Start with a clear, single-sentence **Rule** (`type: "rule"`).
- Provide a clear algebraic pattern in **Formula** (`type: "formula"`).
- Include high-frequency real-world **Examples** (`type: "example"`).
- Explicitly highlight **Common Mistakes** (`type: "mistake"`).

---

## 4. Verification Workflow
Before committing new curriculum content:
1. Launch the app and navigate to **Settings** > **Developer & QA** > **Open Curriculum Inspector**.
2. Tap the **Reload** icon to re-parse the assets.
3. Check the **VALIDATION** tab to ensure 0 errors and 0 warnings.
4. Run `./gradlew testDebugUnitTest` to ensure all automated unit tests and schema tests pass cleanly.
