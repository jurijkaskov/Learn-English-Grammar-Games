# Beginner Curriculum Mapping: A1–A2 Complete Course Map

**Course ID:** `course_beginner`  
**CEFR Band:** A1–A2  
**Companion Reference:** *Essential Grammar in Use*, Raymond Murphy (Fourth Edition, 2015, Cambridge University Press)  
**Verified Source Scope:** Units 1–115 (100% mapped, 0 gaps, 0 unmapped)  
**Architectural Status:** Clean Architecture • JSON-Driven Curriculum • Type-Safe Indexing  

---

## 1. Pedagogical Architecture & Reference Philosophy

### Standalone Independence vs. Companion Mapping
- **The App is a Primary Course**: *Learn English: Grammar Games* is an independent, self-contained educational product with its own syllabus, structured lessons, explanations, interactive mini-games, and progressive difficulty.
- **The Book is a Reference**: *Essential Grammar in Use, Fourth Edition* serves exclusively as an external benchmark and companion cross-reference.
- **Copyright & Plagiarism Strict Ban**: None of the explanations, practice sentences, examples, illustrations, or test questions in our course are copied from Raymond Murphy's book. All learning content blocks, rules, examples, and questions are 100% original authored material tailored for digital interactive engagement.

### CEFR Level Distribution
- **A1 (Breakthrough / Elementary)**: Foundational identity (*be*), routine habits (*present simple*), immediate actions (*present continuous*), past memories (*past simple was/were*), basic personal pronouns, singular/plural nouns, demonstratives, simple prepositions of place and time.
- **A2 (Waystage / Pre-Intermediate)**: Narrative coordination (*past continuous with when*), unfinished duration and experience (*present perfect with just/already/yet/for/since*), basic passive frames (*is done / was done*), modality (*must, should, have to, might*), conditional links (*if real / hypothetical*), relative pronouns (*who/which/that*), and high-frequency phrasal verbs.

### Three-Tier Concept Depth
1. **INTRODUCTION**: First acquaintance with core morphology and high-frequency syntax (e.g., *am/is/are*, basic *a/an*). Focus on comprehension and basic recognition.
2. **FOUNDATION**: Systematic practice of productive patterns, contrastive forms, questions, negatives, and inflections. Forms the structural core of A1–A2.
3. **CONTROL**: Nuanced differentiation (e.g., *Present Simple vs. Present Continuous*, *Present Perfect vs. Past Simple*, *Subject vs. Object Questions*, *Word Order Constraints*), and capstone synthesis.

---

## 2. Executive Coverage Audit

| Metric | Count | Compliance Status |
| :--- | :--- | :--- |
| **Verified Book Units** | 115 | 100% from Murphy 4th Edition TOC |
| **Units Mapped** | 115 | 115 / 115 Units (100.0%) |
| **Unmapped Units** | 0 | None |
| **Multi-Mapped / Overlapping Units** | 0 | Every unit assigned to exactly one primary pedagogical topic |
| **Course Sections** | 22 | 21 Grammar Sections + 1 Capstone Challenge |
| **Curriculum Topics** | 56 | Pedagogically sized (1–5 related units per topic) |
| **Lessons Authored** | 56 | Structured with clear learning objectives & estimated minutes |
| **Prerequisite Cycles** | 0 | Verified Directed Acyclic Graph (DAG) |

---

## 3. Curriculum Structure: Sections & Topics Directory

### Section 01: Present (`beginner_present`)
*Affirmative, negative, question forms of be, present simple, present continuous, and possession.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_am_is_are` | **Am, Is, Are: Affirmative and Negative** | 1 | A1 | INTRODUCTION | `concept_be` | None |
| `beginner_topic_be_questions` | **Am, Is, Are: Questions and Short Answers** | 2 | A1 | FOUNDATION | `concept_be` | beginner_topic_am_is_are |
| `beginner_topic_present_continuous_basics` | **Present Continuous: Actions in Progress** | 3, 4 | A1 | FOUNDATION | `concept_present_continuous` | beginner_topic_am_is_are |
| `beginner_topic_present_simple_basics` | **Present Simple: Habits, Negatives, and Questions** | 5, 6, 7 | A1 | FOUNDATION | `concept_present_simple` | beginner_topic_am_is_are |
| `beginner_topic_present_simple_vs_continuous` | **Present Simple vs. Present Continuous** | 8 | A2 | CONTROL | `concept_present_simple` | beginner_topic_present_continuous_basics, beginner_topic_present_simple_basics |
| `beginner_topic_have_and_have_got` | **Have and Have Got for Possession** | 9 | A1 | FOUNDATION | `concept_verb_patterns` | beginner_topic_present_simple_basics |

### Section 02: Past (`beginner_past`)
*Past time forms of be (was/were), regular and irregular past simple, and past continuous.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_past_simple_be` | **Was and Were: Past Form of Be** | 10 | A1 | FOUNDATION | `concept_be` | beginner_topic_am_is_are |
| `beginner_topic_past_simple_regular_irregular` | **Past Simple: Regular, Irregular, Negatives, and Questions** | 11, 12 | A1 | FOUNDATION | `concept_past_simple` | beginner_topic_past_simple_be, beginner_topic_present_simple_basics |
| `beginner_topic_past_continuous_intro` | **Past Continuous and Past Simple Narratives** | 13, 14 | A2 | FOUNDATION | `concept_past_continuous` | beginner_topic_past_simple_regular_irregular |

### Section 03: Present Perfect (`beginner_present_perfect`)
*Present perfect for life experience, recent events with just/already/yet, duration, and past contrast.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_present_perfect_form` | **Present Perfect: Life Experience (have/has done)** | 15, 17 | A2 | FOUNDATION | `concept_present_perfect` | beginner_topic_past_simple_regular_irregular |
| `beginner_topic_present_perfect_just_already_yet` | **Present Perfect with Just, Already, and Yet** | 16 | A2 | FOUNDATION | `concept_present_perfect` | beginner_topic_present_perfect_form |
| `beginner_topic_present_perfect_duration` | **Duration: How Long, For, Since, and Ago** | 18, 19 | A2 | FOUNDATION | `concept_present_perfect` | beginner_topic_present_perfect_form |
| `beginner_topic_present_perfect_vs_past` | **Present Perfect vs. Past Simple Contrast** | 20 | A2 | CONTROL | `concept_present_perfect` | beginner_topic_present_perfect_just_already_yet, beginner_topic_past_simple_regular_irregular |

### Section 04: Passive (`beginner_passive`)
*Basic passive constructions in present simple, past simple, continuous, and perfect.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_passive_present_past` | **Passive Voice: Present and Past Simple** | 21 | A2 | FOUNDATION | `concept_passive` | beginner_topic_past_simple_regular_irregular, beginner_topic_am_is_are |
| `beginner_topic_passive_continuous_perfect` | **Passive Voice: Continuous and Perfect** | 22 | A2 | FOUNDATION | `concept_passive` | beginner_topic_passive_present_past |

### Section 05: Verb Forms (`beginner_verb_forms`)
*Conjugation of primary verbs be/have/do and high-frequency irregular verb families.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_be_have_do_verb_forms` | **Be, Have, and Do in Present and Past** | 23 | A1 | FOUNDATION | `concept_verb_forms` | beginner_topic_present_simple_basics, beginner_topic_past_simple_regular_irregular |
| `beginner_topic_regular_irregular_fundamentals` | **Regular and Irregular Verb Classes** | 24 | A1 | FOUNDATION | `concept_verb_forms` | beginner_topic_past_simple_regular_irregular |

### Section 06: Future (`beginner_future`)
*Expressing the future with present continuous arrangements, be going to plans, and will/shall.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_future_present_and_going_to` | **Future: Present Continuous Arrangements and Going To** | 25, 26 | A2 | FOUNDATION | `concept_future` | beginner_topic_present_continuous_basics |
| `beginner_topic_future_will_shall` | **Future: Will and Shall for Decisions, Predictions, and Offers** | 27, 28 | A2 | FOUNDATION | `concept_future` | beginner_topic_future_present_and_going_to |

### Section 07: Modals and Everyday Functions (`beginner_modals`)
*Ability, obligation, advice, polite requests, imperatives, and past habits (used to).*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_modals_might_can_could` | **Possibility and Ability: Might, Can, and Could** | 29, 30 | A1 | FOUNDATION | `concept_modals` | beginner_topic_present_simple_basics |
| `beginner_topic_modals_must_should_have_to` | **Obligation and Advice: Must, Should, and Have To** | 31, 32, 33 | A2 | FOUNDATION | `concept_modals` | beginner_topic_present_simple_basics |
| `beginner_topic_social_requests_habits` | **Polite Requests, Imperatives, and Past Habits** | 34, 35, 36 | A2 | FOUNDATION | `concept_modals` | beginner_topic_past_simple_regular_irregular |

### Section 08: There and It (`beginner_there_and_it`)
*Existential there is/are across tenses and placeholder dummy it for time, weather, and distance.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_there_is_there_are` | **There Is / There Are Across Tenses** | 37, 38 | A1 | FOUNDATION | `concept_there_it` | beginner_topic_am_is_are |
| `beginner_topic_it_is_vs_there_is` | **It Is for Time, Weather, and Impersonal Frames** | 39 | A1 | FOUNDATION | `concept_there_it` | beginner_topic_am_is_are |

### Section 09: Auxiliary Verbs (`beginner_auxiliary_verbs`)
*Auxiliary verbs in short answers, agreement (too/either, so/neither), and negative contractions.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_auxiliary_verbs_usage` | **Auxiliary Verbs in Short Answers and Tag Echoes** | 40, 41 | A2 | FOUNDATION | `concept_auxiliary_verbs` | beginner_topic_present_simple_basics, beginner_topic_past_simple_regular_irregular |
| `beginner_topic_auxiliary_agreement_negatives` | **Agreement (Too/Either, So/Neither) and Negatives** | 42, 43 | A2 | FOUNDATION | `concept_auxiliary_verbs` | beginner_topic_auxiliary_verbs_usage |

### Section 10: Questions (`beginner_questions`)
*Question word order, question words, subject vs object questions, and indirect questions.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_question_formation_wh` | **Question Formation and Wh- Question Words** | 44, 47 | A1 | FOUNDATION | `concept_questions` | beginner_topic_present_simple_basics, beginner_topic_past_simple_regular_irregular |
| `beginner_topic_subject_questions_prepositions` | **Subject vs Object Questions and Prepositions at End** | 45, 46 | A2 | CONTROL | `concept_questions` | beginner_topic_question_formation_wh |
| `beginner_topic_indirect_duration_questions` | **Duration and Indirect Question Patterns** | 48, 49 | A2 | CONTROL | `concept_questions` | beginner_topic_question_formation_wh |

### Section 11: Reported Speech (`beginner_reported_speech`)
*Reporting statements using say and tell with basic tense adjustment.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_reported_speech_basics` | **Reported Statements: He Said That / She Told Me** | 50 | A2 | FOUNDATION | `concept_reported_speech` | beginner_topic_past_simple_regular_irregular |

### Section 12: -ing and to (`beginner_ing_and_to`)
*Verb complementation with gerunds (-ing) and to-infinitives, plus infinitive of purpose.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_verb_ing_to_forms` | **Verbs Followed by -ing and to-Infinitive** | 51, 52 | A2 | FOUNDATION | `concept_gerund_infinitive` | beginner_topic_present_simple_basics |
| `beginner_topic_verb_person_to_purpose` | **Verb + Person + to and Infinitive of Purpose** | 53, 54 | A2 | FOUNDATION | `concept_gerund_infinitive` | beginner_topic_verb_ing_to_forms |

### Section 13: Common Verb Patterns (`beginner_common_verbs`)
*Everyday usage and idiomatic combinations with go, get, do, make, and have.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_verbs_go_and_get` | **Everyday Usage of Go and Get** | 55, 56 | A1 | FOUNDATION | `concept_verb_patterns` | beginner_topic_present_simple_basics |
| `beginner_topic_verbs_do_make_have` | **Do vs Make and Have Collocations** | 57, 58 | A1 | FOUNDATION | `concept_verb_patterns` | beginner_topic_present_simple_basics |

### Section 14: Pronouns and Possessives (`beginner_pronouns_possessives`)
*Subject, object, and possessive pronouns, reflexive pronouns, and possessive 's.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_personal_possessive_pronouns` | **Subject, Object, and Possessive Pronouns** | 59, 60, 61, 62 | A1 | FOUNDATION | `concept_pronouns` | beginner_topic_am_is_are |
| `beginner_topic_reflexives_possessive_s` | **Reflexive Pronouns (-self) and Possessive 's** | 63, 64 | A1 | FOUNDATION | `concept_pronouns` | beginner_topic_personal_possessive_pronouns |

### Section 15: Articles and Nouns (`beginner_articles_nouns`)
*Indefinite and definite articles (a/an, the), countable/uncountable nouns, and zero article.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_articles_plurals` | **Indefinite Article (A/An) and Singular/Plural Nouns** | 65, 66 | A1 | FOUNDATION | `concept_articles` | beginner_topic_am_is_are |
| `beginner_topic_countable_uncountable_nouns` | **Countable vs Uncountable Nouns** | 67, 68 | A1 | FOUNDATION | `concept_articles` | beginner_topic_articles_plurals |
| `beginner_topic_definite_the_and_zero_article` | **Definite Article (The), Zero Article, and Place Names** | 69, 70, 71, 72, 73 | A2 | FOUNDATION | `concept_articles` | beginner_topic_articles_plurals |

### Section 16: Determiners and Pronouns (`beginner_determiners`)
*Demonstratives, indefinite pronouns, quantifiers (some, any, every, all, both, much, many, few).*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_demonstratives_and_ones` | **Demonstratives (This/That/These/Those) and One/Ones** | 74, 75 | A1 | FOUNDATION | `concept_determiners` | beginner_topic_articles_plurals |
| `beginner_topic_some_any_negatives_compounds` | **Some, Any, No, None, and Indefinite Compounds** | 76, 77, 78, 79 | A1 | FOUNDATION | `concept_determiners` | beginner_topic_demonstratives_and_ones |
| `beginner_topic_quantifiers_distributives` | **Every, All, Most, Both, Either, Neither** | 80, 81, 82 | A2 | FOUNDATION | `concept_determiners` | beginner_topic_some_any_negatives_compounds |
| `beginner_topic_quantifiers_much_many_few_little` | **Quantifiers: A Lot, Much, Many, Little, Few** | 83, 84 | A1 | FOUNDATION | `concept_determiners` | beginner_topic_countable_uncountable_nouns |

### Section 17: Adjectives and Adverbs (`beginner_adjectives_adverbs`)
*Adjective positions, adverbs of manner (-ly), comparatives (-er/more), superlatives, too, and enough.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_adjectives_and_adverbs_basics` | **Adjectives and Adverbs of Manner (-ly)** | 85, 86 | A1 | FOUNDATION | `concept_adjectives_adverbs` | beginner_topic_am_is_are |
| `beginner_topic_comparatives_and_as_as` | **Comparatives (-er, more than) and Equal Comparisons (as...as)** | 87, 88, 89 | A2 | FOUNDATION | `concept_adjectives_adverbs` | beginner_topic_adjectives_and_adverbs_basics |
| `beginner_topic_superlatives_too_enough` | **Superlatives (the oldest/most) and Modifiers (Too and Enough)** | 90, 91, 92 | A2 | FOUNDATION | `concept_adjectives_adverbs` | beginner_topic_comparatives_and_as_as |

### Section 18: Word Order (`beginner_word_order`)
*Standard declarative order (SVO + Place + Time), adverb placement, and double objects.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_word_order_svo_frequency` | **Sentence Word Order: Verb + Object, Frequency Adverbs** | 93, 94 | A2 | FOUNDATION | `concept_word_order` | beginner_topic_present_simple_basics |
| `beginner_topic_adverb_order_double_objects` | **Still, Yet, Already, and Double Objects** | 95, 96 | A2 | CONTROL | `concept_word_order` | beginner_topic_word_order_svo_frequency, beginner_topic_personal_possessive_pronouns |

### Section 19: Conjunctions and Clauses (`beginner_clauses`)
*Coordinating connectives, time clauses (when), basic conditional sentences (if), and relative clauses.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_conjunctions_time_clauses` | **Conjunctions (And, But, Or, So, Because) and Time Clauses (When)** | 97, 98 | A2 | FOUNDATION | `concept_conjunctions` | beginner_topic_present_simple_basics |
| `beginner_topic_conditionals_intro` | **Conditionals: Real Present/Future (If we go) and Unreal (If I had)** | 99, 100 | A2 | FOUNDATION | `concept_conditionals` | beginner_topic_future_will_shall |
| `beginner_topic_relative_clauses_intro` | **Relative Clauses: Who, That, Which, and Pronoun Omission** | 101, 102 | A2 | FOUNDATION | `concept_relative_clauses` | beginner_topic_word_order_svo_frequency |

### Section 20: Prepositions (`beginner_prepositions`)
*Prepositions of time, location, movement, and dependent collocations with adjectives and verbs.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_prepositions_time` | **Prepositions of Time: At, On, In, From...To, Until, During, While** | 103, 104, 105 | A1 | FOUNDATION | `concept_prepositions` | beginner_topic_am_is_are |
| `beginner_topic_prepositions_place_movement` | **Prepositions of Place (In, At, On) and Movement (To, Through, Over)** | 106, 107, 108, 109, 110 | A1 | FOUNDATION | `concept_prepositions` | beginner_topic_am_is_are |
| `beginner_topic_prepositions_collocations` | **Preposition Expressions and Collocations** | 111, 112, 113 | A2 | FOUNDATION | `concept_prepositions` | beginner_topic_prepositions_place_movement |

### Section 21: Phrasal Verbs (`beginner_phrasal_verbs`)
*Essential two-part verbs, movement particles, and separable pronoun placement.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_phrasal_verbs_basics` | **Everyday Phrasal Verbs and Pronoun Placement** | 114, 115 | A2 | FOUNDATION | `concept_phrasal_verbs` | beginner_topic_word_order_svo_frequency |

### Section 22: Beginner Final Challenge (`beginner_final_challenge`)
*Comprehensive diagnostic review and mastery assessment synthesizing all A1–A2 grammar concepts.*

| Topic ID | Topic Title | Units | CEFR | Depth | Concept | Prerequisites |
| :--- | :--- | :---: | :---: | :---: | :--- | :--- |
| `beginner_topic_final_challenge` | **Beginner Capstone: A1–A2 Comprehensive Assessment** | Capstone | A2 | CONTROL | `concept_conjunctions` | beginner_topic_present_simple_vs_continuous, beginner_topic_present_perfect_vs_past, beginner_topic_prepositions_collocations, beginner_topic_phrasal_verbs_basics |

---

## 4. Complete 115-Unit Cross-Reference Matrix

Source of Truth: *Essential Grammar in Use Fourth Edition (2015)* Table of Contents.

| Unit | Murphy Chapter Title | Murphy Category | Mapped Topic ID | App Section | Status |
| :---: | :--- | :--- | :--- | :--- | :---: |
| 1 | am/is/are | Present | `beginner_topic_am_is_are` | `beginner_present` | **MAPPED** |
| 2 | am/is/are (questions) | Present | `beginner_topic_be_questions` | `beginner_present` | **MAPPED** |
| 3 | I am doing (present continuous) | Present | `beginner_topic_present_continuous_basics` | `beginner_present` | **MAPPED** |
| 4 | are you doing? (present continuous questions) | Present | `beginner_topic_present_continuous_basics` | `beginner_present` | **MAPPED** |
| 5 | I do/work/like etc. (present simple) | Present | `beginner_topic_present_simple_basics` | `beginner_present` | **MAPPED** |
| 6 | I don’t ... (present simple negative) | Present | `beginner_topic_present_simple_basics` | `beginner_present` | **MAPPED** |
| 7 | Do you ...? (present simple questions) | Present | `beginner_topic_present_simple_basics` | `beginner_present` | **MAPPED** |
| 8 | I am doing (present continuous) and I do (present simple) | Present | `beginner_topic_present_simple_vs_continuous` | `beginner_present` | **MAPPED** |
| 9 | I have ... and I’ve got ... | Present | `beginner_topic_have_and_have_got` | `beginner_present` | **MAPPED** |
| 10 | was/were | Past | `beginner_topic_past_simple_be` | `beginner_past` | **MAPPED** |
| 11 | worked/got/went etc. (past simple) | Past | `beginner_topic_past_simple_regular_irregular` | `beginner_past` | **MAPPED** |
| 12 | I didn’t ... Did you ...? (past simple negative and questions) | Past | `beginner_topic_past_simple_regular_irregular` | `beginner_past` | **MAPPED** |
| 13 | I was doing (past continuous) | Past | `beginner_topic_past_continuous_intro` | `beginner_past` | **MAPPED** |
| 14 | I was doing (past continuous) and I did (past simple) | Past | `beginner_topic_past_continuous_intro` | `beginner_past` | **MAPPED** |
| 15 | I have done (present perfect 1) | Present perfect | `beginner_topic_present_perfect_form` | `beginner_present_perfect` | **MAPPED** |
| 16 | I’ve just ... I’ve already ... I haven’t ... yet (present perfect 2) | Present perfect | `beginner_topic_present_perfect_just_already_yet` | `beginner_present_perfect` | **MAPPED** |
| 17 | Have you ever ...? (present perfect 3) | Present perfect | `beginner_topic_present_perfect_form` | `beginner_present_perfect` | **MAPPED** |
| 18 | How long have you ...? (present perfect 4) | Present perfect | `beginner_topic_present_perfect_duration` | `beginner_present_perfect` | **MAPPED** |
| 19 | for / since / ago | Present perfect | `beginner_topic_present_perfect_duration` | `beginner_present_perfect` | **MAPPED** |
| 20 | I have done (present perfect) and I did (past) | Present perfect | `beginner_topic_present_perfect_vs_past` | `beginner_present_perfect` | **MAPPED** |
| 21 | is done / was done (passive 1) | Passive | `beginner_topic_passive_present_past` | `beginner_passive` | **MAPPED** |
| 22 | is being done / has been done (passive 2) | Passive | `beginner_topic_passive_continuous_perfect` | `beginner_passive` | **MAPPED** |
| 23 | be/have/do in present and past tenses | Verb forms | `beginner_topic_be_have_do_verb_forms` | `beginner_verb_forms` | **MAPPED** |
| 24 | Regular and irregular verbs | Verb forms | `beginner_topic_regular_irregular_fundamentals` | `beginner_verb_forms` | **MAPPED** |
| 25 | What are you doing tomorrow? | Future | `beginner_topic_future_present_and_going_to` | `beginner_future` | **MAPPED** |
| 26 | I’m going to ... | Future | `beginner_topic_future_present_and_going_to` | `beginner_future` | **MAPPED** |
| 27 | will/shall 1 | Future | `beginner_topic_future_will_shall` | `beginner_future` | **MAPPED** |
| 28 | will/shall 2 | Future | `beginner_topic_future_will_shall` | `beginner_future` | **MAPPED** |
| 29 | might | Modals, imperative etc. | `beginner_topic_modals_might_can_could` | `beginner_modals` | **MAPPED** |
| 30 | can and could | Modals, imperative etc. | `beginner_topic_modals_might_can_could` | `beginner_modals` | **MAPPED** |
| 31 | must / mustn’t / don’t need to | Modals, imperative etc. | `beginner_topic_modals_must_should_have_to` | `beginner_modals` | **MAPPED** |
| 32 | should | Modals, imperative etc. | `beginner_topic_modals_must_should_have_to` | `beginner_modals` | **MAPPED** |
| 33 | I have to ... | Modals, imperative etc. | `beginner_topic_modals_must_should_have_to` | `beginner_modals` | **MAPPED** |
| 34 | Would you like ...? / I’d like ... | Modals, imperative etc. | `beginner_topic_social_requests_habits` | `beginner_modals` | **MAPPED** |
| 35 | Do this! Don’t do that! Let’s do this! | Modals, imperative etc. | `beginner_topic_social_requests_habits` | `beginner_modals` | **MAPPED** |
| 36 | I used to ... | Modals, imperative etc. | `beginner_topic_social_requests_habits` | `beginner_modals` | **MAPPED** |
| 37 | there is / there are | There and it | `beginner_topic_there_is_there_are` | `beginner_there_and_it` | **MAPPED** |
| 38 | there was/were / there has/have been / there will be | There and it | `beginner_topic_there_is_there_are` | `beginner_there_and_it` | **MAPPED** |
| 39 | It ... | There and it | `beginner_topic_it_is_vs_there_is` | `beginner_there_and_it` | **MAPPED** |
| 40 | I am, I don’t etc. | Auxiliary verbs | `beginner_topic_auxiliary_verbs_usage` | `beginner_auxiliary_verbs` | **MAPPED** |
| 41 | Have you? Are you? Don’t you? etc. | Auxiliary verbs | `beginner_topic_auxiliary_verbs_usage` | `beginner_auxiliary_verbs` | **MAPPED** |
| 42 | too/either / so am I / neither do I etc. | Auxiliary verbs | `beginner_topic_auxiliary_agreement_negatives` | `beginner_auxiliary_verbs` | **MAPPED** |
| 43 | isn’t, haven’t, don’t etc. (negatives) | Auxiliary verbs | `beginner_topic_auxiliary_agreement_negatives` | `beginner_auxiliary_verbs` | **MAPPED** |
| 44 | is it ...? / have you ...? / do they ...? etc. (questions 1) | Questions | `beginner_topic_question_formation_wh` | `beginner_questions` | **MAPPED** |
| 45 | Who saw you? / Who did you see? (questions 2) | Questions | `beginner_topic_subject_questions_prepositions` | `beginner_questions` | **MAPPED** |
| 46 | Who is she talking to? / What is it like? (questions 3) | Questions | `beginner_topic_subject_questions_prepositions` | `beginner_questions` | **MAPPED** |
| 47 | What ...? / Which ...? / How ...? (questions 4) | Questions | `beginner_topic_question_formation_wh` | `beginner_questions` | **MAPPED** |
| 48 | How long does it take ...? | Questions | `beginner_topic_indirect_duration_questions` | `beginner_questions` | **MAPPED** |
| 49 | Do you know where ...? / I don’t know what ... etc. | Questions | `beginner_topic_indirect_duration_questions` | `beginner_questions` | **MAPPED** |
| 50 | She said that ... / He told me that ... | Reported speech | `beginner_topic_reported_speech_basics` | `beginner_reported_speech` | **MAPPED** |
| 51 | work/working / go/going / do/doing | -ing and to ... | `beginner_topic_verb_ing_to_forms` | `beginner_ing_and_to` | **MAPPED** |
| 52 | to ... (I want to do) and -ing (I enjoy doing) | -ing and to ... | `beginner_topic_verb_ing_to_forms` | `beginner_ing_and_to` | **MAPPED** |
| 53 | I want you to ... / I told you to ... | -ing and to ... | `beginner_topic_verb_person_to_purpose` | `beginner_ing_and_to` | **MAPPED** |
| 54 | I went to the shop to ... | -ing and to ... | `beginner_topic_verb_person_to_purpose` | `beginner_ing_and_to` | **MAPPED** |
| 55 | go to ... / go on ... / go for ... / go -ing | Go, get, do, make and have | `beginner_topic_verbs_go_and_get` | `beginner_common_verbs` | **MAPPED** |
| 56 | get | Go, get, do, make and have | `beginner_topic_verbs_go_and_get` | `beginner_common_verbs` | **MAPPED** |
| 57 | do and make | Go, get, do, make and have | `beginner_topic_verbs_do_make_have` | `beginner_common_verbs` | **MAPPED** |
| 58 | have | Go, get, do, make and have | `beginner_topic_verbs_do_make_have` | `beginner_common_verbs` | **MAPPED** |
| 59 | I/me / he/him / they/them etc. | Pronouns and possessives | `beginner_topic_personal_possessive_pronouns` | `beginner_pronouns_possessives` | **MAPPED** |
| 60 | my/his/their etc. | Pronouns and possessives | `beginner_topic_personal_possessive_pronouns` | `beginner_pronouns_possessives` | **MAPPED** |
| 61 | Whose is this? It’s mine/yours/hers etc. | Pronouns and possessives | `beginner_topic_personal_possessive_pronouns` | `beginner_pronouns_possessives` | **MAPPED** |
| 62 | I/me/my/mine | Pronouns and possessives | `beginner_topic_personal_possessive_pronouns` | `beginner_pronouns_possessives` | **MAPPED** |
| 63 | myself/yourself/themselves etc. | Pronouns and possessives | `beginner_topic_reflexives_possessive_s` | `beginner_pronouns_possessives` | **MAPPED** |
| 64 | -’s (Kate’s camera / my brother’s car etc.) | Pronouns and possessives | `beginner_topic_reflexives_possessive_s` | `beginner_pronouns_possessives` | **MAPPED** |
| 65 | a/an ... | A and the | `beginner_topic_articles_plurals` | `beginner_articles_nouns` | **MAPPED** |
| 66 | train(s) / bus(es) (singular and plural) | A and the | `beginner_topic_articles_plurals` | `beginner_articles_nouns` | **MAPPED** |
| 67 | a bottle / some water (countable/uncountable 1) | A and the | `beginner_topic_countable_uncountable_nouns` | `beginner_articles_nouns` | **MAPPED** |
| 68 | a cake / some cake / some cakes (countable/uncountable 2) | A and the | `beginner_topic_countable_uncountable_nouns` | `beginner_articles_nouns` | **MAPPED** |
| 69 | a/an and the | A and the | `beginner_topic_definite_the_and_zero_article` | `beginner_articles_nouns` | **MAPPED** |
| 70 | the ... | A and the | `beginner_topic_definite_the_and_zero_article` | `beginner_articles_nouns` | **MAPPED** |
| 71 | go to work / go home / go to the cinema | A and the | `beginner_topic_definite_the_and_zero_article` | `beginner_articles_nouns` | **MAPPED** |
| 72 | I like music / I hate exams | A and the | `beginner_topic_definite_the_and_zero_article` | `beginner_articles_nouns` | **MAPPED** |
| 73 | the ... (names of places) | A and the | `beginner_topic_definite_the_and_zero_article` | `beginner_articles_nouns` | **MAPPED** |
| 74 | this/that/these/those | Determiners and pronouns | `beginner_topic_demonstratives_and_ones` | `beginner_determiners` | **MAPPED** |
| 75 | one/ones | Determiners and pronouns | `beginner_topic_demonstratives_and_ones` | `beginner_determiners` | **MAPPED** |
| 76 | some and any | Determiners and pronouns | `beginner_topic_some_any_negatives_compounds` | `beginner_determiners` | **MAPPED** |
| 77 | not + any / no / none | Determiners and pronouns | `beginner_topic_some_any_negatives_compounds` | `beginner_determiners` | **MAPPED** |
| 78 | not + anybody/anyone/anything / nobody/no-one/nothing | Determiners and pronouns | `beginner_topic_some_any_negatives_compounds` | `beginner_determiners` | **MAPPED** |
| 79 | somebody/anything/nowhere etc. | Determiners and pronouns | `beginner_topic_some_any_negatives_compounds` | `beginner_determiners` | **MAPPED** |
| 80 | every and all | Determiners and pronouns | `beginner_topic_quantifiers_distributives` | `beginner_determiners` | **MAPPED** |
| 81 | all / most / some / any / no/none | Determiners and pronouns | `beginner_topic_quantifiers_distributives` | `beginner_determiners` | **MAPPED** |
| 82 | both / either / neither | Determiners and pronouns | `beginner_topic_quantifiers_distributives` | `beginner_determiners` | **MAPPED** |
| 83 | a lot / much / many | Determiners and pronouns | `beginner_topic_quantifiers_much_many_few_little` | `beginner_determiners` | **MAPPED** |
| 84 | (a) little / (a) few | Determiners and pronouns | `beginner_topic_quantifiers_much_many_few_little` | `beginner_determiners` | **MAPPED** |
| 85 | old/nice/interesting etc. (adjectives) | Adjectives and adverbs | `beginner_topic_adjectives_and_adverbs_basics` | `beginner_adjectives_adverbs` | **MAPPED** |
| 86 | quickly/badly/suddenly etc. (adverbs) | Adjectives and adverbs | `beginner_topic_adjectives_and_adverbs_basics` | `beginner_adjectives_adverbs` | **MAPPED** |
| 87 | old/older / expensive/more expensive | Adjectives and adverbs | `beginner_topic_comparatives_and_as_as` | `beginner_adjectives_adverbs` | **MAPPED** |
| 88 | older than ... / more expensive than ... | Adjectives and adverbs | `beginner_topic_comparatives_and_as_as` | `beginner_adjectives_adverbs` | **MAPPED** |
| 89 | not as ... as | Adjectives and adverbs | `beginner_topic_comparatives_and_as_as` | `beginner_adjectives_adverbs` | **MAPPED** |
| 90 | the oldest / the most expensive | Adjectives and adverbs | `beginner_topic_superlatives_too_enough` | `beginner_adjectives_adverbs` | **MAPPED** |
| 91 | enough | Adjectives and adverbs | `beginner_topic_superlatives_too_enough` | `beginner_adjectives_adverbs` | **MAPPED** |
| 92 | too | Adjectives and adverbs | `beginner_topic_superlatives_too_enough` | `beginner_adjectives_adverbs` | **MAPPED** |
| 93 | He speaks English very well. (word order 1) | Word order | `beginner_topic_word_order_svo_frequency` | `beginner_word_order` | **MAPPED** |
| 94 | always/usually/often etc. (word order 2) | Word order | `beginner_topic_word_order_svo_frequency` | `beginner_word_order` | **MAPPED** |
| 95 | still / yet / already | Word order | `beginner_topic_adverb_order_double_objects` | `beginner_word_order` | **MAPPED** |
| 96 | Give me that book! / Give it to me! | Word order | `beginner_topic_adverb_order_double_objects` | `beginner_word_order` | **MAPPED** |
| 97 | and / but / or / so / because | Conjunctions and clauses | `beginner_topic_conjunctions_time_clauses` | `beginner_clauses` | **MAPPED** |
| 98 | When ... | Conjunctions and clauses | `beginner_topic_conjunctions_time_clauses` | `beginner_clauses` | **MAPPED** |
| 99 | If we go ... / If you see ... etc. | Conjunctions and clauses | `beginner_topic_conditionals_intro` | `beginner_clauses` | **MAPPED** |
| 100 | If I had ... / If we went ... etc. | Conjunctions and clauses | `beginner_topic_conditionals_intro` | `beginner_clauses` | **MAPPED** |
| 101 | a person who ... / a thing that/which ... (relative clauses 1) | Conjunctions and clauses | `beginner_topic_relative_clauses_intro` | `beginner_clauses` | **MAPPED** |
| 102 | the people we met / the hotel you stayed at (relative clauses 2) | Conjunctions and clauses | `beginner_topic_relative_clauses_intro` | `beginner_clauses` | **MAPPED** |
| 103 | at 8 o’clock / on Monday / in April | Prepositions | `beginner_topic_prepositions_time` | `beginner_prepositions` | **MAPPED** |
| 104 | from ... to / until / since / for | Prepositions | `beginner_topic_prepositions_time` | `beginner_prepositions` | **MAPPED** |
| 105 | before / after / during / while | Prepositions | `beginner_topic_prepositions_time` | `beginner_prepositions` | **MAPPED** |
| 106 | in / at / on (places 1) | Prepositions | `beginner_topic_prepositions_place_movement` | `beginner_prepositions` | **MAPPED** |
| 107 | in / at / on (places 2) | Prepositions | `beginner_topic_prepositions_place_movement` | `beginner_prepositions` | **MAPPED** |
| 108 | to / in / at (places 3) | Prepositions | `beginner_topic_prepositions_place_movement` | `beginner_prepositions` | **MAPPED** |
| 109 | under, behind, opposite etc. | Prepositions | `beginner_topic_prepositions_place_movement` | `beginner_prepositions` | **MAPPED** |
| 110 | up, over, through etc. | Prepositions | `beginner_topic_prepositions_place_movement` | `beginner_prepositions` | **MAPPED** |
| 111 | on / at / by / with / about | Prepositions | `beginner_topic_prepositions_collocations` | `beginner_prepositions` | **MAPPED** |
| 112 | good at ..., interested in ... etc. / of/at/for etc. (prepositions) + -ing | Prepositions | `beginner_topic_prepositions_collocations` | `beginner_prepositions` | **MAPPED** |
| 113 | listen to ..., look at ... etc. (verb + preposition) | Prepositions | `beginner_topic_prepositions_collocations` | `beginner_prepositions` | **MAPPED** |
| 114 | go in, fall off, run away etc. (phrasal verbs 1) | Phrasal verbs | `beginner_topic_phrasal_verbs_basics` | `beginner_phrasal_verbs` | **MAPPED** |
| 115 | put on your shoes / put your shoes on (phrasal verbs 2) | Phrasal verbs | `beginner_topic_phrasal_verbs_basics` | `beginner_phrasal_verbs` | **MAPPED** |

---

## 5. Non-Core Book Elements & Pedagogical Handling

| Book Element | Murphy Scope | Applet Treatment |
| :--- | :--- | :--- |
| **Appendices 1–4** | Active/passive summary, irregular verb list, spelling rules, contracted short forms | Integrated into `concept_verb_forms` reference catalog and interactive in-lesson reference sheets. |
| **Appendices 5–7** | Phrasal verbs reference, preposition collocations, British vs American English notes | Integrated into Sections 20 (Prepositions) and 21 (Phrasal Verbs), plus locale preference notes in settings. |
| **Additional Exercises** | 35 cumulative mixed drills at the back of the book | Replaced by Section 22: *Beginner Final Challenge* dynamic diagnostic sprints and mixed quiz engines. |
| **Study Guide** | Diagnostic multiple-choice assessment for self-placement | Implemented as the Course Placement Diagnostic in onboarding. |
| **Answer Keys** | Static solutions key at back of book | Replaced by real-time interactive evaluation engine with immediate contextual pedagogical explanations. |

---

## 6. Verification and Curriculum Inspector

Developers and QA engineers can inspect and verify curriculum integrity at runtime via the **Curriculum Inspector** (Developer Options -> Curriculum Inspector):
- **Coverage Matrix**: Validates 115/115 units mapped with 0 missing units.
- **Unit Lookup**: Query any unit number (1–115) to view its mapped topic, section, CEFR target, and learning activities.
- **Integrity Validation**: Automated check guarantees 0 schema errors, valid prerequisites, and strict topological ordering.
