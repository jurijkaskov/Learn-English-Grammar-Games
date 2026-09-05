import json

unit_titles = {
    1: "Present continuous (I am doing)",
    2: "Present simple (I do)",
    3: "Present continuous and present simple 1 (I am doing and I do)",
    4: "Present continuous and present simple 2 (I am doing and I do)",
    5: "Past simple (I did)",
    6: "Past continuous (I was doing)",
    7: "Present perfect 1 (I have done)",
    8: "Present perfect 2 (I have done)",
    9: "Present perfect continuous (I have been doing)",
    10: "Present perfect continuous and simple (I have been doing and I have done)",
    11: "how long have you (been) ...?",
    12: "for and since / when ...? and how long ...?",
    13: "Present perfect and past 1 (I have done and I did)",
    14: "Present perfect and past 2 (I have done and I did)",
    15: "Past perfect (I had done)",
    16: "Past perfect continuous (I had been doing)",
    17: "have and have got",
    18: "used to (do)",
    19: "Present tenses (I am doing / I do) for the future",
    20: "I’m going to (do)",
    21: "will and shall 1",
    22: "will and shall 2",
    23: "I will and I’m going to",
    24: "will be doing and will have done",
    25: "when I do and when I’ve done / if and when",
    26: "can, could and (be) able to",
    27: "could (do) and could have (done)",
    28: "must and can’t",
    29: "may and might 1",
    30: "may and might 2",
    31: "have to and must",
    32: "must / mustn’t / needn’t",
    33: "should 1",
    34: "should 2",
    35: "I’d better ... / it’s time ...",
    36: "would",
    37: "can/could/would you ...? etc. (Requests, offers, permission and invitations)",
    38: "if I do ... and if I did ...",
    39: "if I knew ... / I wish I knew ...",
    40: "if I had known ... / I wish I had known ...",
    41: "wish",
    42: "Passive 1 (is done / was done)",
    43: "Passive 2 (be done / been done / being done)",
    44: "Passive 3",
    45: "it is said that ... / he is said to ... / he is supposed to ...",
    46: "have something done",
    47: "Reported speech 1 (he said that ...)",
    48: "Reported speech 2",
    49: "Questions 1",
    50: "Questions 2 (do you know where ...? / he asked me where ...)",
    51: "Auxiliary verbs (have/do/can etc.) / I think so / I hope so etc.",
    52: "Question tags (do you? isn’t it? etc.)",
    53: "Verb + -ing (enjoy doing / stop doing etc.)",
    54: "Verb + to ... (decide to ... / forget to ... etc.)",
    55: "Verb (+ object) + to ... (I want you to ...)",
    56: "Verb + -ing or to ... 1 (remember, regret etc.)",
    57: "Verb + -ing or to ... 2 (try, need, help)",
    58: "Verb + -ing or to ... 3 (like / would like etc.)",
    59: "prefer and would rather",
    60: "Preposition (in/for/about etc.) + -ing",
    61: "be/get used to ... (I’m used to ...)",
    62: "Verb + preposition + -ing (succeed in -ing / insist on -ing etc.)",
    63: "there’s no point in -ing, it’s worth -ing etc.",
    64: "to ..., for ... and so that ...",
    65: "Adjective + to ...",
    66: "to ... (afraid to do) and preposition + -ing (afraid of -ing)",
    67: "see somebody do and see somebody doing",
    68: "-ing clauses (He hurt his knee playing football.)",
    69: "Countable and uncountable 1",
    70: "Countable and uncountable 2",
    71: "Countable nouns with a/an and some",
    72: "a/an and the",
    73: "the 1",
    74: "the 2 (school / the school etc.)",
    75: "the 3 (children / the children)",
    76: "the 4 (the giraffe / the telephone / the old etc.)",
    77: "Names with and without the 1",
    78: "Names with and without the 2",
    79: "Singular and plural",
    80: "Noun + noun (a bus driver / a headache)",
    81: "-’s (your sister’s name) and of ... (the name of the book)",
    82: "myself/yourself/themselves etc.",
    83: "a friend of mine / my own house / on my own / by myself",
    84: "there ... and it ...",
    85: "some and any",
    86: "no/none/any / nothing/nobody etc.",
    87: "much, many, little, few, a lot, plenty",
    88: "all / all of / most / most of / no / none of etc.",
    89: "both / both of / neither / neither of / either / either of",
    90: "all / every / whole",
    91: "each and every",
    92: "Relative clauses 1: clauses with who/that/which",
    93: "Relative clauses 2: clauses with and without who/that/which",
    94: "Relative clauses 3: whose/whom/where",
    95: "Relative clauses 4: extra information clauses (1)",
    96: "Relative clauses 5: extra information clauses (2)",
    97: "-ing and -ed clauses (the woman talking to Tom, the boy injured in the accident)",
    98: "Adjectives ending in -ing and -ed (boring/bored etc.)",
    99: "Adjectives: a nice new house, you look tired",
    100: "Adjectives and adverbs 1 (quick/quickly)",
    101: "Adjectives and adverbs 2 (well, fast, late, hard/hardly)",
    102: "so and such",
    103: "enough and too",
    104: "quite, pretty, rather and fairly",
    105: "Comparative 1 (cheaper, more expensive etc.)",
    106: "Comparative 2 (much better / any better etc.)",
    107: "Comparative 3 (as ... as / than)",
    108: "Superlative (the longest / the most enjoyable etc.)",
    109: "Word order 1: verb + object; place and time",
    110: "Word order 2: adverbs with the verb",
    111: "still / any more / yet / already",
    112: "even",
    113: "although / though / even though / in spite of / despite",
    114: "in case",
    115: "unless / as long as / provided",
    116: "as (as I walked ... / as I was ... etc.)",
    117: "like and as",
    118: "like / as if",
    119: "during / for / while",
    120: "by and until / by the time ...",
    121: "at/on/in (time)",
    122: "on time and in time / at the end and in the end",
    123: "in/at/on (position) 1",
    124: "in/at/on (position) 2",
    125: "in/at/on (position) 3",
    126: "to, at, in and into",
    127: "in/on/at (other uses)",
    128: "by",
    129: "Noun + preposition (reason for, cause of etc.)",
    130: "Adjective + preposition 1",
    131: "Adjective + preposition 2",
    132: "Verb + preposition 1 / to and at",
    133: "Verb + preposition 2 / about/for/of/after",
    134: "Verb + preposition 3 / about and of",
    135: "Verb + preposition 4 / of/for/from/on",
    136: "Verb + preposition 5 / in/into/with/to/on",
    137: "Phrasal verbs 1 — Introduction",
    138: "Phrasal verbs 2 — in/out",
    139: "Phrasal verbs 3 — out",
    140: "Phrasal verbs 4 — on/off (1)",
    141: "Phrasal verbs 5 — on/off (2)",
    142: "Phrasal verbs 6 — up/down",
    143: "Phrasal verbs 7 — up (1)",
    144: "Phrasal verbs 8 — up (2)",
    145: "Phrasal verbs 9 — away/back"
}

# Book sections mapped to unit ranges
def get_book_section(u):
    if 1 <= u <= 6: return "Present and past"
    if 7 <= u <= 18: return "Present perfect and past"
    if 19 <= u <= 25: return "Future"
    if 26 <= u <= 37: return "Modals"
    if 38 <= u <= 41: return "if and wish"
    if 42 <= u <= 46: return "Passive"
    if 47 <= u <= 48: return "Reported speech"
    if 49 <= u <= 52: return "Questions and auxiliary verbs"
    if 53 <= u <= 68: return "-ing and to ..."
    if 69 <= u <= 81: return "Articles and nouns"
    if 82 <= u <= 91: return "Pronouns and determiners"
    if 92 <= u <= 97: return "Relative clauses"
    if 98 <= u <= 112: return "Adjectives and adverbs"
    if 113 <= u <= 120: return "Conjunctions and prepositions"
    if 121 <= u <= 136: return "Prepositions"
    if 137 <= u <= 145: return "Phrasal verbs"
    return "Other"

# Define 18 Sections
sections_data = [
    {
        "id": "intermediate_present_past_review",
        "title": "Present and Past Review",
        "shortDescription": "Review and contrast dynamic vs stative present, and narrative past simple vs continuous.",
        "order": 1
    },
    {
        "id": "intermediate_present_perfect_past",
        "title": "Present Perfect and Past",
        "shortDescription": "Master aspectual contrasts: finished vs open time, duration vs result, and past narrative sequencing.",
        "order": 2
    },
    {
        "id": "intermediate_future",
        "title": "Future Forms and Time Clauses",
        "shortDescription": "Express arrangements, decisions, predictions, and future continuous/perfect aspect.",
        "order": 3
    },
    {
        "id": "intermediate_modals",
        "title": "Modals: Ability, Deduction, Obligation",
        "shortDescription": "Nuanced modal expressions for certainty, past deduction, obligation, and social interaction.",
        "order": 4
    },
    {
        "id": "intermediate_conditionals_wish",
        "title": "Conditionals and Wish",
        "shortDescription": "Hypothetical conditions in present and past, unreal situations, regrets, and wishes.",
        "order": 5
    },
    {
        "id": "intermediate_passive",
        "title": "Passive Voice and Causatives",
        "shortDescription": "Complex passives across aspects, impersonal reporting structures, and causative have/get.",
        "order": 6
    },
    {
        "id": "intermediate_reported_speech",
        "title": "Reported Speech",
        "shortDescription": "Tense backshift, reporting questions, orders, and varied reporting verb patterns.",
        "order": 7
    },
    {
        "id": "intermediate_questions_auxiliaries",
        "title": "Questions and Auxiliary Verbs",
        "shortDescription": "Embedded questions, preposition stranding, auxiliary ellipsis, and agreement tags.",
        "order": 8
    },
    {
        "id": "intermediate_verb_patterns",
        "title": "Gerund, Infinitive and Verb Patterns",
        "shortDescription": "Verb complementation (-ing vs to-inf), meaning shifts, sensory verbs, and participle clauses.",
        "order": 9
    },
    {
        "id": "intermediate_articles_nouns",
        "title": "Articles and Nouns",
        "shortDescription": "Countability shifts, generic vs specific reference, institutional zero article, and compounds.",
        "order": 10
    },
    {
        "id": "intermediate_pronouns_determiners",
        "title": "Pronouns and Determiners",
        "shortDescription": "Reflexives, quantifiers, binary distributives, and existential there vs dummy it.",
        "order": 11
    },
    {
        "id": "intermediate_relative_clauses",
        "title": "Relative Clauses",
        "shortDescription": "Defining vs non-defining clauses, relative pronouns, contact clauses, and reduced participles.",
        "order": 12
    },
    {
        "id": "intermediate_adjectives_adverbs",
        "title": "Adjectives and Adverbs",
        "shortDescription": "Participle adjectives, adjective order, gradability, and advanced comparative structures.",
        "order": 13
    },
    {
        "id": "intermediate_word_order_focus",
        "title": "Word Order and Focus",
        "shortDescription": "Clause constituent ordering, adverb mid-positions, aspectual adverbs, and focus particle even.",
        "order": 14
    },
    {
        "id": "intermediate_conjunctions_linking",
        "title": "Conjunctions and Linking Words",
        "shortDescription": "Concession, condition, manner, purpose, and time connectives in complex discourse.",
        "order": 15
    },
    {
        "id": "intermediate_prepositions",
        "title": "Prepositions and Collocations",
        "shortDescription": "Spatial, temporal, and dependent prepositions following nouns, adjectives, and verbs.",
        "order": 16
    },
    {
        "id": "intermediate_phrasal_verbs",
        "title": "Phrasal Verbs",
        "shortDescription": "Syntactic separability and particle semantics (in/out, on/off, up/down, away/back).",
        "order": 17
    },
    {
        "id": "intermediate_final_challenge",
        "title": "Intermediate Final Challenge",
        "shortDescription": "Comprehensive capstone diagnostic evaluating complete B1–B2 grammar mastery.",
        "order": 18
    }
]

# Define 92 Topics with exact units
raw_topics = [
    # Section 1: Present and Past Review (Units 1-6)
    {
        "id": "intermediate_present_simple_continuous_contrast",
        "sectionId": "intermediate_present_past_review",
        "title": "Present Continuous vs Present Simple: Dynamic & Stative",
        "desc": "Contrast actions in progress and temporary situations with permanent habits and stative verbs.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_present_simple",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [1, 2, 3, 4]
    },
    {
        "id": "intermediate_past_simple_continuous_narrative",
        "sectionId": "intermediate_present_past_review",
        "title": "Past Simple vs Past Continuous: Action & Background",
        "desc": "Differentiate sequential completed past events from interrupted ongoing background scenes.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_past_continuous",
        "diff": "NORMAL",
        "prereqs": ["intermediate_present_simple_continuous_contrast"],
        "units": [5, 6]
    },

    # Section 2: Present Perfect and Past (Units 7-18)
    {
        "id": "intermediate_present_perfect_simple",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Present Perfect Simple: Recent Results & Life Experiences",
        "desc": "Connect past actions to present consequences and discuss life experiences up to now.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_present_perfect",
        "diff": "NORMAL",
        "prereqs": ["intermediate_past_simple_continuous_narrative"],
        "units": [7, 8]
    },
    {
        "id": "intermediate_present_perfect_continuous",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Present Perfect Continuous: Duration & Ongoing Activity",
        "desc": "Highlight ongoing duration, unfinished processes, and visible present side-effects.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_present_perfect",
        "diff": "NORMAL",
        "prereqs": ["intermediate_present_perfect_simple"],
        "units": [9]
    },
    {
        "id": "intermediate_present_perfect_simple_vs_continuous",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Present Perfect Simple vs Continuous: Result vs Activity",
        "desc": "Distinguish between completed quantity/outcome and uninterrupted ongoing duration.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_present_perfect",
        "diff": "NORMAL",
        "prereqs": ["intermediate_present_perfect_continuous"],
        "units": [10]
    },
    {
        "id": "intermediate_present_perfect_how_long_for_since",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Duration Questions & Time Frames: How Long, For and Since",
        "desc": "Ask about continuing situations and specify duration starting points versus total periods.",
        "order": 4,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_present_perfect",
        "diff": "NORMAL",
        "prereqs": ["intermediate_present_perfect_simple"],
        "units": [11, 12]
    },
    {
        "id": "intermediate_present_perfect_vs_past_simple",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Present Perfect vs Past Simple: Open vs Finished Time",
        "desc": "Select correctly between past finished time markers and open, current time relevance.",
        "order": 5,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_present_perfect",
        "diff": "NORMAL",
        "prereqs": ["intermediate_present_perfect_how_long_for_since"],
        "units": [13, 14]
    },
    {
        "id": "intermediate_past_perfect_simple",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Past Perfect Simple: The Earlier Past in Narrative",
        "desc": "Establish chronological clarity when an action occurred before another past milestone.",
        "order": 6,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_past_perfect",
        "diff": "NORMAL",
        "prereqs": ["intermediate_present_perfect_vs_past_simple"],
        "units": [15]
    },
    {
        "id": "intermediate_past_perfect_continuous",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Past Perfect Continuous: Cause & Duration Before a Past Point",
        "desc": "Explain earlier continuous activities that produced visible past outcomes.",
        "order": 7,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_past_perfect",
        "diff": "HARD",
        "prereqs": ["intermediate_past_perfect_simple"],
        "units": [16]
    },
    {
        "id": "intermediate_have_and_have_got",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Possession & States: Have and Have Got",
        "desc": "Use have and have got for possession, relationships, illnesses, and habitual actions.",
        "order": 8,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_present_simple",
        "diff": "EASY",
        "prereqs": ["intermediate_present_simple_continuous_contrast"],
        "units": [17]
    },
    {
        "id": "intermediate_used_to_past_habits",
        "sectionId": "intermediate_present_perfect_past",
        "title": "Past Habits & States: Used to",
        "desc": "Contrast discontinued past habits and states with present realities.",
        "order": 9,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_past_simple",
        "diff": "NORMAL",
        "prereqs": ["intermediate_past_simple_continuous_narrative"],
        "units": [18]
    },

    # Section 3: Future (Units 19-25)
    {
        "id": "intermediate_future_present_tenses_going_to",
        "sectionId": "intermediate_future",
        "title": "Arrangements & Intentions: Present Tenses and Going to",
        "desc": "Distinguish fixed personal arrangements from planned prior intentions and physical evidence.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_future",
        "diff": "NORMAL",
        "prereqs": ["intermediate_present_simple_continuous_contrast"],
        "units": [19, 20]
    },
    {
        "id": "intermediate_future_will_shall_decisions",
        "sectionId": "intermediate_future",
        "title": "Instant Decisions & Offers: Will and Shall",
        "desc": "Employ will for spontaneous decisions, offers, promises, and shall for first-person suggestions.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_future",
        "diff": "NORMAL",
        "prereqs": ["intermediate_future_present_tenses_going_to"],
        "units": [21, 22]
    },
    {
        "id": "intermediate_future_will_vs_going_to",
        "sectionId": "intermediate_future",
        "title": "Predictions & Plans: Will vs Going to",
        "desc": "Contrast belief-based predictions with predictions grounded in visible present clues.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_future",
        "diff": "NORMAL",
        "prereqs": ["intermediate_future_will_shall_decisions"],
        "units": [23]
    },
    {
        "id": "intermediate_future_continuous_and_perfect",
        "sectionId": "intermediate_future",
        "title": "Future in Progress & Completion: Will Be Doing and Will Have Done",
        "desc": "Describe actions ongoing at a specific future moment and actions completed prior to a deadline.",
        "order": 4,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_future",
        "diff": "HARD",
        "prereqs": ["intermediate_future_will_vs_going_to"],
        "units": [24]
    },
    {
        "id": "intermediate_future_time_clauses",
        "sectionId": "intermediate_future",
        "title": "Future Time & Conditional Clauses: When, As Soon As, If",
        "desc": "Apply present forms in subordinate clauses of time and condition referring to the future.",
        "order": 5,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_future",
        "diff": "NORMAL",
        "prereqs": ["intermediate_future_will_vs_going_to"],
        "units": [25]
    },

    # Section 4: Modals (Units 26-37)
    {
        "id": "intermediate_modal_ability_can_could_able",
        "sectionId": "intermediate_modals",
        "title": "Ability & Possibility: Can, Could and Be Able To",
        "desc": "Express general ability versus specific past successful achievements with managed to / was able to.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_modals",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [26]
    },
    {
        "id": "intermediate_modal_past_ability_could_have",
        "sectionId": "intermediate_modals",
        "title": "Past Opportunity & Unrealised Action: Could Have Done",
        "desc": "Discuss actions that were possible in the past but were not actually carried out.",
        "order": 2,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_modals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_modal_ability_can_could_able"],
        "units": [27]
    },
    {
        "id": "intermediate_modal_deduction_present",
        "sectionId": "intermediate_modals",
        "title": "Logical Deduction: Must and Can't",
        "desc": "Formulate strong logical certainty and impossibility based on present evidence.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_modals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_modal_ability_can_could_able"],
        "units": [28]
    },
    {
        "id": "intermediate_modal_possibility_may_might",
        "sectionId": "intermediate_modals",
        "title": "Possibility & Speculation: May and Might",
        "desc": "Indicate tentative possibility in present and future contexts, and retrospective speculation.",
        "order": 4,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_modals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_modal_deduction_present"],
        "units": [29, 30]
    },
    {
        "id": "intermediate_modal_obligation_must_have_to",
        "sectionId": "intermediate_modals",
        "title": "Obligation & Necessity: Must and Have to",
        "desc": "Differentiate internal personal obligation from external authority, rules, or laws.",
        "order": 5,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_modals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_modal_deduction_present"],
        "units": [31]
    },
    {
        "id": "intermediate_modal_prohibition_lack_necessity",
        "sectionId": "intermediate_modals",
        "title": "Prohibition & Absence of Obligation: Mustn't vs Needn't",
        "desc": "Contrast strict prohibition with lack of necessity (needn't / don't have to / didn't need to).",
        "order": 6,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_modals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_modal_obligation_must_have_to"],
        "units": [32]
    },
    {
        "id": "intermediate_modal_advice_should_had_better",
        "sectionId": "intermediate_modals",
        "title": "Advice, Expectation & Warning: Should, Ought to, Had Better",
        "desc": "Give sensible advice, express past regret (should have done), and issue urgent warnings.",
        "order": 7,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_modals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_modal_obligation_must_have_to"],
        "units": [33, 34, 35]
    },
    {
        "id": "intermediate_modal_would_habits_hypothetical",
        "sectionId": "intermediate_modals",
        "title": "Hypothetical Actions & Past Habits: Would",
        "desc": "Employ would for imaginary situations, past recurring actions, and polite willingness.",
        "order": 8,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_modals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_modal_advice_should_had_better"],
        "units": [36]
    },
    {
        "id": "intermediate_modal_requests_permission_offers",
        "sectionId": "intermediate_modals",
        "title": "Social Formulae: Requests, Offers, Permission & Invitations",
        "desc": "Select appropriate degrees of politeness in everyday spoken communicative scenarios.",
        "order": 9,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_modals",
        "diff": "EASY",
        "prereqs": ["intermediate_modal_ability_can_could_able"],
        "units": [37]
    },

    # Section 5: Conditionals and Wish (Units 38-41)
    {
        "id": "intermediate_conditional_real_and_hypothetical",
        "sectionId": "intermediate_conditionals_wish",
        "title": "Real vs Hypothetical: If I do vs If I did",
        "desc": "Contrast open real possibilities with improbable hypothetical present or future scenarios.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_conditionals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_modal_would_habits_hypothetical"],
        "units": [38]
    },
    {
        "id": "intermediate_conditional_unreal_present_wish",
        "sectionId": "intermediate_conditionals_wish",
        "title": "Unreal Present & Regret: If I knew and I wish I knew",
        "desc": "Formulate counterfactual present states and express discontent about current circumstances.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_conditionals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_conditional_real_and_hypothetical"],
        "units": [39]
    },
    {
        "id": "intermediate_conditional_hypothetical_past_third",
        "sectionId": "intermediate_conditionals_wish",
        "title": "Past Regret & Third Conditional: If I had known",
        "desc": "Analyse unfulfilled past conditions and their impossible consequences in retrospective evaluation.",
        "order": 3,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_conditionals",
        "diff": "HARD",
        "prereqs": ["intermediate_conditional_unreal_present_wish"],
        "units": [40]
    },
    {
        "id": "intermediate_wish_patterns_would",
        "sectionId": "intermediate_conditionals_wish",
        "title": "Desire for Change & Annoyance: Wish + Would",
        "desc": "Express frustration about current annoying habits and desire for another agent to change behavior.",
        "order": 4,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_conditionals",
        "diff": "NORMAL",
        "prereqs": ["intermediate_conditional_unreal_present_wish"],
        "units": [41]
    },

    # Section 6: Passive (Units 42-46)
    {
        "id": "intermediate_passive_core_tenses",
        "sectionId": "intermediate_passive",
        "title": "Passive Formation: Present & Past Simple",
        "desc": "Shift discourse focus to the patient/recipient of action across basic tenses.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_passive",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [42]
    },
    {
        "id": "intermediate_passive_complex_aspects_modals",
        "sectionId": "intermediate_passive",
        "title": "Complex Passives: Continuous, Perfect and Modal Structures",
        "desc": "Construct passive sentences with being done, have been done, and modal auxiliaries.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_passive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_passive_core_tenses"],
        "units": [43, 44]
    },
    {
        "id": "intermediate_passive_reporting_structures",
        "sectionId": "intermediate_passive",
        "title": "Impersonal Reporting: It is said that / He is said to",
        "desc": "Use formal distance in news and reporting with introductory passive matrices and nominative infinitives.",
        "order": 3,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_passive",
        "diff": "HARD",
        "prereqs": ["intermediate_passive_complex_aspects_modals"],
        "units": [45]
    },
    {
        "id": "intermediate_passive_causative_have_done",
        "sectionId": "intermediate_passive",
        "title": "Causative Services: Have Something Done",
        "desc": "Express arranging for professional services or suffering misfortune with have/get + object + V3.",
        "order": 4,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_passive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_passive_core_tenses"],
        "units": [46]
    },

    # Section 7: Reported Speech (Units 47-48)
    {
        "id": "intermediate_reported_speech_statements_backshift",
        "sectionId": "intermediate_reported_speech",
        "title": "Reported Statements: Tense Backshift & Time References",
        "desc": "Accurately adjust verb tenses, pronouns, and time/place adverbs in indirect statements.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_reported_speech",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [47]
    },
    {
        "id": "intermediate_reported_speech_questions_reporting_verbs",
        "sectionId": "intermediate_reported_speech",
        "title": "Reported Questions, Orders & Reporting Verb Patterns",
        "desc": "Form indirect questions with whether/if and deploy advanced verbs (admit, deny, advise, warn).",
        "order": 2,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_reported_speech",
        "diff": "NORMAL",
        "prereqs": ["intermediate_reported_speech_statements_backshift"],
        "units": [48]
    },

    # Section 8: Questions and Auxiliary Verbs (Units 49-52)
    {
        "id": "intermediate_question_formation_prepositions",
        "sectionId": "intermediate_questions_auxiliaries",
        "title": "Direct Question Formation & Preposition Stranding",
        "desc": "Structure complex questions and leave dependent prepositions naturally at sentence ends.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_questions",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [49]
    },
    {
        "id": "intermediate_indirect_embedded_questions",
        "sectionId": "intermediate_questions_auxiliaries",
        "title": "Indirect Questions: Do you know where...? / He asked me where...",
        "desc": "Maintain affirmative word order inside polite embedded and indirect question clauses.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_questions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_question_formation_prepositions"],
        "units": [50]
    },
    {
        "id": "intermediate_auxiliary_substitution_agreement",
        "sectionId": "intermediate_questions_auxiliaries",
        "title": "Auxiliary Substitution: So do I, Neither do I, I think so",
        "desc": "Avoid clunky repetition using auxiliary echo responses, inverted agreement, and pro-form so/not.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_auxiliary_verbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_question_formation_prepositions"],
        "units": [51]
    },
    {
        "id": "intermediate_question_tags",
        "sectionId": "intermediate_questions_auxiliaries",
        "title": "Question Tags: Seeking Confirmation vs Real Questions",
        "desc": "Match polarity correctly and modulate intonation between genuine inquiries and casual checks.",
        "order": 4,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_questions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_auxiliary_substitution_agreement"],
        "units": [52]
    },

    # Section 9: Verb Patterns (Units 53-68)
    {
        "id": "intermediate_verb_plus_ing",
        "sectionId": "intermediate_verb_patterns",
        "title": "Verb + -ing: Enjoy, Mind, Suggest",
        "desc": "Identify verbs systematically taking gerund complements and practice idiomatic usage.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [53]
    },
    {
        "id": "intermediate_verb_plus_to_infinitive",
        "sectionId": "intermediate_verb_patterns",
        "title": "Verb + to-Infinitive: Decide, Forget, Promise",
        "desc": "Master verbs followed by to-infinitive including prospective decisions and commitments.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_verb_plus_ing"],
        "units": [54]
    },
    {
        "id": "intermediate_verb_object_to_infinitive",
        "sectionId": "intermediate_verb_patterns",
        "title": "Verb + Object + to-Infinitive: Want someone to do, Advise",
        "desc": "Structure manipulative and advisory verbs followed by noun object and infinitive clause.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_verb_patterns",
        "diff": "NORMAL",
        "prereqs": ["intermediate_verb_plus_to_infinitive"],
        "units": [55]
    },
    {
        "id": "intermediate_verb_ing_or_to_meaning_change",
        "sectionId": "intermediate_verb_patterns",
        "title": "Meaning Shifts: Remember, Regret, Stop doing vs to do",
        "desc": "Discriminate crucial semantic differences when complement changes from -ing to to-infinitive.",
        "order": 4,
        "cefr": "B2",
        "depth": "CONTRAST",
        "concept": "concept_gerund_infinitive",
        "diff": "HARD",
        "prereqs": ["intermediate_verb_plus_to_infinitive"],
        "units": [56]
    },
    {
        "id": "intermediate_verb_ing_or_to_try_need_help",
        "sectionId": "intermediate_verb_patterns",
        "title": "Subtle Pattern Differences: Try, Need, Help",
        "desc": "Grasp nuanced complementations: experiment vs attempt (try) and passive meaning (need doing).",
        "order": 5,
        "cefr": "B2",
        "depth": "CONTRAST",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_verb_ing_or_to_meaning_change"],
        "units": [57]
    },
    {
        "id": "intermediate_verb_like_would_like_preferences",
        "sectionId": "intermediate_verb_patterns",
        "title": "Preferences: Like doing vs Like to do, Would like",
        "desc": "Distinguish enjoyment of activity from prudent habitual choices and conditional desires.",
        "order": 6,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_verb_plus_ing"],
        "units": [58]
    },
    {
        "id": "intermediate_prefer_and_would_rather",
        "sectionId": "intermediate_verb_patterns",
        "title": "Expressing Preference: Prefer and Would Rather",
        "desc": "Compare options using prefer ... to ... and would rather ... than with base verb forms.",
        "order": 7,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_verb_like_would_like_preferences"],
        "units": [59]
    },
    {
        "id": "intermediate_preposition_plus_ing",
        "sectionId": "intermediate_verb_patterns",
        "title": "Preposition + -ing: Before leaving, Instead of waiting",
        "desc": "Apply the inviolable rule that prepositions must be followed by a gerund when taking verbal objects.",
        "order": 8,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_verb_plus_ing"],
        "units": [60]
    },
    {
        "id": "intermediate_be_get_used_to_doing",
        "sectionId": "intermediate_verb_patterns",
        "title": "Familiarity: Be Used to and Get Used to doing",
        "desc": "Separate psychological familiarity (be used to + -ing) from discontinued past habits (used to + inf).",
        "order": 9,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_preposition_plus_ing"],
        "units": [61]
    },
    {
        "id": "intermediate_verb_preposition_ing",
        "sectionId": "intermediate_verb_patterns",
        "title": "Verb + Preposition + -ing: Succeed in, Insist on",
        "desc": "Combine dependent verbal prepositions smoothly with downstream gerund phrases.",
        "order": 10,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_verb_patterns",
        "diff": "NORMAL",
        "prereqs": ["intermediate_preposition_plus_ing"],
        "units": [62]
    },
    {
        "id": "intermediate_fixed_ing_expressions",
        "sectionId": "intermediate_verb_patterns",
        "title": "Special -ing Idioms: There's no point in, Worth, Have difficulty",
        "desc": "Learn high-frequency colloquial formulas governing fixed gerund expressions.",
        "order": 11,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_preposition_plus_ing"],
        "units": [63]
    },
    {
        "id": "intermediate_infinitive_of_purpose_so_that",
        "sectionId": "intermediate_verb_patterns",
        "title": "Expressing Purpose: To do, For doing, and So that",
        "desc": "Articulate intention and function cleanly using infinitives of purpose or finite so that clauses.",
        "order": 12,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_gerund_infinitive",
        "diff": "NORMAL",
        "prereqs": ["intermediate_verb_plus_to_infinitive"],
        "units": [64]
    },
    {
        "id": "intermediate_adjective_plus_to_infinitive",
        "sectionId": "intermediate_verb_patterns",
        "title": "Adjective + to-Infinitive: Difficult to do, Glad to hear",
        "desc": "Link evaluation and emotional reaction adjectives to following explanatory infinitive phrases.",
        "order": 13,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_adjectives_adverbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_verb_plus_to_infinitive"],
        "units": [65]
    },
    {
        "id": "intermediate_afraid_to_vs_afraid_of_ing",
        "sectionId": "intermediate_verb_patterns",
        "title": "Specific Nuance: Afraid to do vs Afraid of doing",
        "desc": "Contrast deliberate unwillingness to act with apprehension of involuntary accidents.",
        "order": 14,
        "cefr": "B2",
        "depth": "CONTRAST",
        "concept": "concept_gerund_infinitive",
        "diff": "HARD",
        "prereqs": ["intermediate_adjective_plus_to_infinitive"],
        "units": [66]
    },
    {
        "id": "intermediate_sensory_verbs_bare_vs_participle",
        "sectionId": "intermediate_verb_patterns",
        "title": "Perception Verbs: See somebody do vs See somebody doing",
        "desc": "Distinguish between witnessing a complete event and perceiving an action in mid-progress.",
        "order": 15,
        "cefr": "B2",
        "depth": "CONTRAST",
        "concept": "concept_verb_patterns",
        "diff": "HARD",
        "prereqs": ["intermediate_verb_plus_ing"],
        "units": [67]
    },
    {
        "id": "intermediate_participle_ing_clauses",
        "sectionId": "intermediate_verb_patterns",
        "title": "-ing Participle Clauses: Feeling tired, he went to bed",
        "desc": "Condense complex background causes and simultaneous actions using adverbial participle clauses.",
        "order": 16,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_relative_clauses",
        "diff": "HARD",
        "prereqs": ["intermediate_preposition_plus_ing"],
        "units": [68]
    },

    # Section 10: Articles and Nouns (Units 69-81)
    {
        "id": "intermediate_countable_uncountable_contrasts",
        "sectionId": "intermediate_articles_nouns",
        "title": "Countable vs Uncountable Nouns & Category Shifts",
        "desc": "Navigate dual-membership nouns whose countability alters their meaning (coffee, experience, paper).",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_articles",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [69, 70]
    },
    {
        "id": "intermediate_nouns_a_an_and_some",
        "sectionId": "intermediate_articles_nouns",
        "title": "Singular & Plural Reference: A/An with Countable, Some with Uncountable",
        "desc": "Deploy indefinite determiners properly according to noun number and countability status.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_articles",
        "diff": "NORMAL",
        "prereqs": ["intermediate_countable_uncountable_contrasts"],
        "units": [71]
    },
    {
        "id": "intermediate_a_an_vs_the_core_contrast",
        "sectionId": "intermediate_articles_nouns",
        "title": "Specific vs Non-Specific Reference: A/An vs The",
        "desc": "Master hearer-shared knowledge and contextual uniqueness determining the definite article.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_articles",
        "diff": "NORMAL",
        "prereqs": ["intermediate_nouns_a_an_and_some"],
        "units": [72, 73]
    },
    {
        "id": "intermediate_the_with_institutions_and_general",
        "sectionId": "intermediate_articles_nouns",
        "title": "Institutional Zero Article: School vs The School, Bed vs The Bed",
        "desc": "Contrast primary institutional purpose (go to prison) with reference to physical buildings.",
        "order": 4,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_articles",
        "diff": "NORMAL",
        "prereqs": ["intermediate_a_an_vs_the_core_contrast"],
        "units": [74]
    },
    {
        "id": "intermediate_the_with_groups_and_generics",
        "sectionId": "intermediate_articles_nouns",
        "title": "General Classes vs Particular Groups: Children vs The Children, The Giraffe",
        "desc": "Use zero article for universal plural generic claims and definite article for representative species.",
        "order": 5,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_articles",
        "diff": "NORMAL",
        "prereqs": ["intermediate_the_with_institutions_and_general"],
        "units": [75, 76]
    },
    {
        "id": "intermediate_the_with_geographical_names",
        "sectionId": "intermediate_articles_nouns",
        "title": "Proper Names & Geography with and without The",
        "desc": "Command conventions governing oceans, mountain ranges, individual peaks, streets, and hotels.",
        "order": 6,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_articles",
        "diff": "NORMAL",
        "prereqs": ["intermediate_a_an_vs_the_core_contrast"],
        "units": [77, 78]
    },
    {
        "id": "intermediate_noun_plural_compounds_possessives",
        "sectionId": "intermediate_articles_nouns",
        "title": "Noun Forms: Plurals, Noun + Noun Compounds, and Possessive 's vs Of",
        "desc": "Determine when to use apostrophe possessive versus prepositional of phrases and noun premodifiers.",
        "order": 7,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_determiners",
        "diff": "NORMAL",
        "prereqs": ["intermediate_countable_uncountable_contrasts"],
        "units": [79, 80, 81]
    },

    # Section 11: Pronouns and Determiners (Units 82-91)
    {
        "id": "intermediate_reflexive_pronouns_own",
        "sectionId": "intermediate_pronouns_determiners",
        "title": "Reflexives & Independence: Myself, On my own, By myself",
        "desc": "Employ reflexive pronouns for co-referential actions and emphatic independence.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_pronouns",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [82, 83]
    },
    {
        "id": "intermediate_there_and_it_contrast",
        "sectionId": "intermediate_pronouns_determiners",
        "title": "Existential and Anticipatory: There vs It",
        "desc": "Distinguish new entity introduction with there from referential and dummy it expressions.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_there_it",
        "diff": "NORMAL",
        "prereqs": ["intermediate_reflexive_pronouns_own"],
        "units": [84]
    },
    {
        "id": "intermediate_some_and_any_compounds",
        "sectionId": "intermediate_pronouns_determiners",
        "title": "Assertive vs Non-Assertive: Some, Any, and Indefinite Compounds",
        "desc": "Select some in positive invitations and any in negative, interrogative, or arbitrary contexts.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_determiners",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [85, 86]
    },
    {
        "id": "intermediate_quantifiers_much_many_few_little",
        "sectionId": "intermediate_pronouns_determiners",
        "title": "Quantifiers: Much/Many vs Few/A few, Little/A little",
        "desc": "Differentiate positive perspective (a few/a little) from restrictive negative scarcity (few/little).",
        "order": 4,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_determiners",
        "diff": "NORMAL",
        "prereqs": ["intermediate_some_and_any_compounds"],
        "units": [87]
    },
    {
        "id": "intermediate_distributives_both_neither_either",
        "sectionId": "intermediate_pronouns_determiners",
        "title": "Binary Distributives: Both, Neither, and Either (of)",
        "desc": "Accurately coordinate two entities and ensure correct subject-verb agreement.",
        "order": 5,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_determiners",
        "diff": "NORMAL",
        "prereqs": ["intermediate_quantifiers_much_many_few_little"],
        "units": [89]
    },
    {
        "id": "intermediate_totality_all_most_every_whole_each",
        "sectionId": "intermediate_pronouns_determiners",
        "title": "Universal Quantifiers: All, Most, Whole, Every, and Each",
        "desc": "Distinguish collective totality from individual distributivity across varying noun patterns.",
        "order": 6,
        "cefr": "B2",
        "depth": "CONTRAST",
        "concept": "concept_determiners",
        "diff": "NORMAL",
        "prereqs": ["intermediate_distributives_both_neither_either"],
        "units": [88, 90, 91]
    },

    # Section 12: Relative Clauses (Units 92-97)
    {
        "id": "intermediate_relative_who_that_which",
        "sectionId": "intermediate_relative_clauses",
        "title": "Relative Pronouns: Who, That, and Which",
        "desc": "Introduce restrictive relative clauses identifying specific persons versus objects.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_relative_clauses",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [92]
    },
    {
        "id": "intermediate_relative_pronoun_omission",
        "sectionId": "intermediate_relative_clauses",
        "title": "Contact Clauses: Omission of Object Relative Pronouns",
        "desc": "Identify when relative pronouns function as grammatical objects and can be omitted naturally.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_relative_clauses",
        "diff": "NORMAL",
        "prereqs": ["intermediate_relative_who_that_which"],
        "units": [93]
    },
    {
        "id": "intermediate_relative_whose_whom_where",
        "sectionId": "intermediate_relative_clauses",
        "title": "Relative Adverbs & Possessives: Whose, Whom, and Where",
        "desc": "Link possession, formal prepositional objects, and spatial antecedents inside relative clauses.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_relative_clauses",
        "diff": "NORMAL",
        "prereqs": ["intermediate_relative_pronoun_omission"],
        "units": [94]
    },
    {
        "id": "intermediate_relative_defining_vs_non_defining",
        "sectionId": "intermediate_relative_clauses",
        "title": "Essential vs Extra Information: Defining and Non-Defining Relative Clauses",
        "desc": "Master punctuation, comma intonation, and pronoun restrictions (never that in extra-info).",
        "order": 4,
        "cefr": "B2",
        "depth": "CONTRAST",
        "concept": "concept_relative_clauses",
        "diff": "HARD",
        "prereqs": ["intermediate_relative_whose_whom_where"],
        "units": [95, 96]
    },
    {
        "id": "intermediate_relative_reduced_participle_clauses",
        "sectionId": "intermediate_relative_clauses",
        "title": "Reduced Relatives: -ing and -ed Participial Postmodifiers",
        "desc": "Streamline complex clauses into concise active (-ing) and passive (-ed) postmodifiers.",
        "order": 5,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_relative_clauses",
        "diff": "HARD",
        "prereqs": ["intermediate_relative_defining_vs_non_defining"],
        "units": [97]
    },

    # Section 13: Adjectives and Adverbs (Units 98-108)
    {
        "id": "intermediate_adjectives_ing_ed_order",
        "sectionId": "intermediate_adjectives_adverbs",
        "title": "Participle Adjectives (-ing/-ed) and Adjective Ordering",
        "desc": "Contrast causing feelings with experiencing them, and sequence multiple descriptive adjectives.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_adjectives_adverbs",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [98, 99]
    },
    {
        "id": "intermediate_adjectives_and_adverbs_formation",
        "sectionId": "intermediate_adjectives_adverbs",
        "title": "Adjectives vs Adverbs & Irregulars: Quick/Quickly, Well, Fast, Hard/Hardly",
        "desc": "Identify linking verb complements and avoid confusion between lookalikes like hard and hardly.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_adjectives_adverbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_adjectives_ing_ed_order"],
        "units": [100, 101]
    },
    {
        "id": "intermediate_degree_so_such_enough_too",
        "sectionId": "intermediate_adjectives_adverbs",
        "title": "Degree Modifiers: So, Such, Enough, and Too",
        "desc": "Construct consequential result clauses with so/such ... that and calibrate sufficiency/excess.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_adjectives_adverbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_adjectives_and_adverbs_formation"],
        "units": [102, 103]
    },
    {
        "id": "intermediate_grading_quite_rather_pretty_fairly",
        "sectionId": "intermediate_adjectives_adverbs",
        "title": "Grading Adverbs: Quite, Pretty, Rather, and Fairly",
        "desc": "Calibrate subjective evaluation and unexpectedness across moderate grading adverbs.",
        "order": 4,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_adjectives_adverbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_degree_so_such_enough_too"],
        "units": [104]
    },
    {
        "id": "intermediate_comparatives_modifiers",
        "sectionId": "intermediate_adjectives_adverbs",
        "title": "Comparative Forms & Modifiers: Much better, Far more, Any better",
        "desc": "Qualify differences using degree submodifiers like slightly, considerably, and nowhere near.",
        "order": 5,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_adjectives_adverbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_adjectives_and_adverbs_formation"],
        "units": [105, 106]
    },
    {
        "id": "intermediate_equality_and_superlatives",
        "sectionId": "intermediate_adjectives_adverbs",
        "title": "Comparisons of Equality & Superlatives: As...as, Than, The most",
        "desc": "Express equivalence, proportional changes (the more... the better), and superlative boundaries.",
        "order": 6,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_adjectives_adverbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_comparatives_modifiers"],
        "units": [107, 108]
    },

    # Section 14: Word Order and Focus (Units 109-112)
    {
        "id": "intermediate_word_order_verb_object_place_time",
        "sectionId": "intermediate_word_order_focus",
        "title": "Clause Order: Verb + Object, Sequencing Place and Time",
        "desc": "Prevent constituent splitting between transitive verb and object, and sequence place before time.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_word_order",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [109]
    },
    {
        "id": "intermediate_adverb_position_with_verbs",
        "sectionId": "intermediate_word_order_focus",
        "title": "Adverb Positions: Mid-Position with Auxiliaries and Main Verbs",
        "desc": "Place frequency and certainty adverbs accurately before lexical verbs and after auxiliaries/be.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_word_order",
        "diff": "NORMAL",
        "prereqs": ["intermediate_word_order_verb_object_place_time"],
        "units": [110]
    },
    {
        "id": "intermediate_aspectual_adverbs_still_yet_already",
        "sectionId": "intermediate_word_order_focus",
        "title": "Temporal Adverbs: Still, Yet, Already, and Any More",
        "desc": "Contribute aspectual expectations to discourse regarding persistence, completion, and cessation.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_word_order",
        "diff": "NORMAL",
        "prereqs": ["intermediate_adverb_position_with_verbs"],
        "units": [111]
    },
    {
        "id": "intermediate_focus_particle_even",
        "sectionId": "intermediate_word_order_focus",
        "title": "Focus & Surprise: Even and Even Though / Even If",
        "desc": "Highlight extreme cases and unexpected contrasts using the focusing particle even.",
        "order": 4,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_word_order",
        "diff": "NORMAL",
        "prereqs": ["intermediate_adverb_position_with_verbs"],
        "units": [112]
    },

    # Section 15: Conjunctions and Linking Words (Units 113-120)
    {
        "id": "intermediate_conjunctions_concession_contrast",
        "sectionId": "intermediate_conjunctions_linking",
        "title": "Concession & Contrast: Although, Though, Even though, Despite, In spite of",
        "desc": "Differentiate clausal connectives from prepositional phrases taking noun phrases or gerunds.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_conjunctions",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [113]
    },
    {
        "id": "intermediate_conjunctions_precaution_in_case",
        "sectionId": "intermediate_conjunctions_linking",
        "title": "Precaution & Anticipation: In case",
        "desc": "Distinguish precautionary actions taken in advance from conditional responses (in case vs if).",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_conjunctions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_conjunctions_concession_contrast"],
        "units": [114]
    },
    {
        "id": "intermediate_conjunctions_condition_unless_as_long_as",
        "sectionId": "intermediate_conjunctions_linking",
        "title": "Conditional Connectives: Unless, As long as, Provided that",
        "desc": "Formulate negative conditions (if not) and explicit stipulations across formal and informal discourse.",
        "order": 3,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_conjunctions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_conjunctions_precaution_in_case"],
        "units": [115]
    },
    {
        "id": "intermediate_conjunctions_as_like_as_if",
        "sectionId": "intermediate_conjunctions_linking",
        "title": "Manner & Comparison: As, Like, and As if / As though",
        "desc": "Contrast prepositional comparison (like + noun) with conjunctional clauses and unreal manner.",
        "order": 4,
        "cefr": "B2",
        "depth": "CONTRAST",
        "concept": "concept_conjunctions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_conjunctions_concession_contrast"],
        "units": [116, 117, 118]
    },
    {
        "id": "intermediate_conjunctions_time_during_while_by_until",
        "sectionId": "intermediate_conjunctions_linking",
        "title": "Time Relations: During, While, For, and By vs Until",
        "desc": "Disentangle prepositional spans (during/for), clausal overlap (while), and deadlines vs duration.",
        "order": 5,
        "cefr": "B1",
        "depth": "CONTRAST",
        "concept": "concept_conjunctions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_conjunctions_concession_contrast"],
        "units": [119, 120]
    },

    # Section 16: Prepositions and Collocations (Units 121-136)
    {
        "id": "intermediate_prepositions_time_at_on_in",
        "sectionId": "intermediate_prepositions",
        "title": "Time Prepositions: At, On, In, In time vs On time, At the end",
        "desc": "Master clock times, calendar dates, periods, punctuality vs margins, and endpoint expressions.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_prepositions",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [121, 122]
    },
    {
        "id": "intermediate_prepositions_place_position_in_at_on",
        "sectionId": "intermediate_prepositions",
        "title": "Position & Space: In, At, and On in Context",
        "desc": "Conceptualise 3D containment, 2D surfaces, and functional interaction points accurately.",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_prepositions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_prepositions_time_at_on_in"],
        "units": [123, 124, 125]
    },
    {
        "id": "intermediate_prepositions_movement_destination",
        "sectionId": "intermediate_prepositions",
        "title": "Direction & Movement: To, At, In, and Into",
        "desc": "Coordinate verbs of trajectory, arrival, and penetration across varied spatial contexts.",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_prepositions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_prepositions_place_position_in_at_on"],
        "units": [126]
    },
    {
        "id": "intermediate_prepositions_special_uses_by",
        "sectionId": "intermediate_prepositions",
        "title": "Idiomatic Spatial Uses & Means: Other uses of In/On/At and By",
        "desc": "Apply fixed idiomatic idioms (by car, by chance, on purpose, in a hurry, at risk).",
        "order": 4,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_prepositions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_prepositions_place_position_in_at_on"],
        "units": [127, 128]
    },
    {
        "id": "intermediate_noun_plus_preposition_collocations",
        "sectionId": "intermediate_prepositions",
        "title": "Dependent Nouns: Reason for, Cause of, Demand for",
        "desc": "Master noun-complement preposition pairings essential for academic and formal clarity.",
        "order": 5,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_prepositions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_prepositions_special_uses_by"],
        "units": [129]
    },
    {
        "id": "intermediate_adjective_plus_preposition_collocations",
        "sectionId": "intermediate_prepositions",
        "title": "Dependent Adjectives: Proud of, Interested in, Afraid of, Good at",
        "desc": "Internalise natural collocational bonds between evaluation adjectives and their governing prepositions.",
        "order": 6,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_prepositions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_noun_plus_preposition_collocations"],
        "units": [130, 131]
    },
    {
        "id": "intermediate_verb_plus_preposition_collocations",
        "sectionId": "intermediate_prepositions",
        "title": "Dependent Verbs: Listen to, Look at, Depend on, Suffer from",
        "desc": "Consolidate idiomatic prepositional verb bonds across everyday conversational verbs.",
        "order": 7,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_prepositions",
        "diff": "NORMAL",
        "prereqs": ["intermediate_adjective_plus_preposition_collocations"],
        "units": [132, 133, 134, 135, 136]
    },

    # Section 17: Phrasal Verbs (Units 137-145)
    {
        "id": "intermediate_phrasal_verbs_introduction_syntax",
        "sectionId": "intermediate_phrasal_verbs",
        "title": "Phrasal Verbs Anatomy: Separability & Pronoun Placement",
        "desc": "Grasp particle movement rules: object placement and obligatory split with object pronouns.",
        "order": 1,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_phrasal_verbs",
        "diff": "NORMAL",
        "prereqs": [],
        "units": [137]
    },
    {
        "id": "intermediate_phrasal_verbs_in_out",
        "sectionId": "intermediate_phrasal_verbs",
        "title": "Particles In and Out: Movement, Completion & Discovery",
        "desc": "Explore core and metaphorical dimensions of in and out (check in, drop in, find out, run out).",
        "order": 2,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_phrasal_verbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_phrasal_verbs_introduction_syntax"],
        "units": [138, 139]
    },
    {
        "id": "intermediate_phrasal_verbs_on_off",
        "sectionId": "intermediate_phrasal_verbs",
        "title": "Particles On and Off: Activation, Continuation & Departure",
        "desc": "Command phrasal meanings related to progress, cancellation, and departures (carry on, call off).",
        "order": 3,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_phrasal_verbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_phrasal_verbs_introduction_syntax"],
        "units": [140, 141]
    },
    {
        "id": "intermediate_phrasal_verbs_up_down",
        "sectionId": "intermediate_phrasal_verbs",
        "title": "Particles Up and Down: Increase, Decrease, Destruction & Completion",
        "desc": "Master vertical metaphors and telic completion with up/down (turn up, break down, give up).",
        "order": 4,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_phrasal_verbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_phrasal_verbs_on_off"],
        "units": [142, 143, 144]
    },
    {
        "id": "intermediate_phrasal_verbs_away_back",
        "sectionId": "intermediate_phrasal_verbs",
        "title": "Particles Away and Back: Distance, Return & Removal",
        "desc": "Deploy motion and reciprocal particles in dialogue (run away, give away, get back, pay back).",
        "order": 5,
        "cefr": "B1",
        "depth": "CONTROL",
        "concept": "concept_phrasal_verbs",
        "diff": "NORMAL",
        "prereqs": ["intermediate_phrasal_verbs_introduction_syntax"],
        "units": [145]
    },

    # Section 18: Intermediate Final Challenge
    {
        "id": "intermediate_final_challenge_topic",
        "sectionId": "intermediate_final_challenge",
        "title": "B1–B2 Master Diagnostic Review",
        "desc": "Comprehensive evaluation testing contrasting tenses, complex modals, passives, conditionals, and verb patterns.",
        "order": 1,
        "cefr": "B2",
        "depth": "CONTROL",
        "concept": "concept_verb_patterns",
        "diff": "HARD",
        "prereqs": [
            "intermediate_present_perfect_vs_past_simple",
            "intermediate_future_continuous_and_perfect",
            "intermediate_conditional_hypothetical_past_third",
            "intermediate_passive_reporting_structures",
            "intermediate_relative_defining_vs_non_defining",
            "intermediate_phrasal_verbs_up_down"
        ],
        "units": []
    }
]

print("Total raw topics:", len(raw_topics))

# Check unit coverage across topics
all_mapped_units = []
for t in raw_topics:
    all_mapped_units.extend(t["units"])

print("Total mapped unit entries:", len(all_mapped_units))
print("Contiguous 1..145 check:", sorted(all_mapped_units) == list(range(1, 146)))

# Build section topic mapping
section_topics = {}
for t in raw_topics:
    s_id = t["sectionId"]
    if s_id not in section_topics:
        section_topics[s_id] = []
    section_topics[s_id].append(t["id"])

# 1. Generate Course JSON
course_obj = {
    "id": "course_intermediate",
    "title": "Intermediate",
    "description": "Comprehensive B1–B2 course focusing on grammatical contrast, precision, and complex discourse.",
    "level": "INTERMEDIATE",
    "cefrMin": "B1",
    "cefrMax": "B2",
    "sectionIds": [s["id"] for s in sections_data]
}

# 2. Generate Sections JSON
sections_json = []
for s in sections_data:
    sections_json.append({
        "id": s["id"],
        "courseId": "course_intermediate",
        "title": s["title"],
        "shortDescription": s["shortDescription"],
        "order": s["order"],
        "topicIds": section_topics.get(s["id"], [])
    })

# 3. Generate Topics JSON
topics_json = []
lessons_json = []
activities_json = []
unit_to_topic_map = {}

for t in raw_topics:
    topic_id = t["id"]
    lesson_id = f"lesson_{topic_id}"
    activity_id = f"act_{topic_id}_content"

    topic_entry = {
        "id": topic_id,
        "sectionId": t["sectionId"],
        "title": t["title"],
        "shortDescription": t["desc"],
        "order": t["order"],
        "lessonIds": [lesson_id],
        "prerequisites": t["prereqs"],
        "difficulty": t["diff"],
        "cefrLevel": t["cefr"],
        "conceptId": t["concept"],
        "conceptDepth": t["depth"],
        "bookReferences": []
    }
    if t["units"]:
        topic_entry["bookReferences"].append({
            "bookId": "english_grammar_in_use",
            "bookTitle": "English Grammar in Use",
            "edition": "Fifth Edition",
            "editionId": "english_grammar_in_use_5",
            "units": t["units"]
        })
        for u in t["units"]:
            unit_to_topic_map[u] = {
                "topicId": topic_id,
                "topicTitle": t["title"],
                "sectionId": t["sectionId"]
            }

    topics_json.append(topic_entry)

    # Lesson entry
    lessons_json.append({
        "id": lesson_id,
        "topicId": topic_id,
        "title": f"{t['title']} — Guided Practice",
        "order": 1,
        "activityIds": [activity_id],
        "estimatedMinutes": 8,
        "difficulty": t["diff"],
        "learningObjectives": [
            {
                "id": f"obj_{topic_id}_1",
                "description": f"Master core grammatical contrasts and rules of {t['title']}."
            },
            {
                "id": f"obj_{topic_id}_2",
                "description": f"Demonstrate accurate communicative usage in B1–B2 written and spoken contexts."
            }
        ]
    })

    # Activity entry
    activities_json.append({
        "id": activity_id,
        "lessonId": lesson_id,
        "type": "LESSON_CONTENT",
        "title": f"Rules & Practice: {t['title']}",
        "order": 1,
        "questionIds": [],
        "lessonContent": {
            "blocks": [
                {
                    "type": "rule",
                    "id": f"blk_{topic_id}_rule",
                    "title": f"Core Principle: {t['title']}",
                    "description": t["desc"]
                },
                {
                    "type": "formula",
                    "id": f"blk_{topic_id}_formula",
                    "formulaPattern": "Target Pattern in Authentic Context",
                    "formulaNote": f"Target CEFR level: {t['cefr']} | Pedagogical depth: {t['depth']}"
                },
                {
                    "type": "example",
                    "id": f"blk_{topic_id}_ex",
                    "sentence": f"Notice how native speakers employ {t['title']} in natural discourse.",
                    "highlightedPart": "natural discourse",
                    "translation": "Обратите внимание на естественное употребление данной конструкции в речи."
                }
            ]
        }
    })

# 4. Generate Mapping JSON: mappings/english_grammar_in_use_5_intermediate.json
mapping_units = []
for u in range(1, 146):
    mapped_info = unit_to_topic_map.get(u)
    mapping_units.append({
        "unit": u,
        "unitTitle": unit_titles[u],
        "bookSection": get_book_section(u),
        "mappedTopicId": mapped_info["topicId"],
        "mappedTopicTitle": mapped_info["topicTitle"],
        "mappedSectionId": mapped_info["sectionId"],
        "status": "MAPPED"
    })

mapping_obj = {
    "book": {
        "id": "english_grammar_in_use",
        "title": "English Grammar in Use",
        "author": "Raymond Murphy",
        "edition": "Fifth Edition",
        "editionId": "english_grammar_in_use_5",
        "publicationYear": 2019,
        "totalUnits": 145
    },
    "targetCourseId": "course_intermediate",
    "targetCourseLevel": "INTERMEDIATE",
    "cefrRange": "B1-B2",
    "coverage": {
        "totalUnits": 145,
        "mappedUnits": 145,
        "unmappedUnits": 0,
        "coveragePercentage": 100.0
    },
    "units": mapping_units
}

# Write out all files
with open("app/src/main/assets/curriculum/intermediate/course.json", "w") as f:
    json.dump(course_obj, f, indent=2)

with open("app/src/main/assets/curriculum/intermediate/sections.json", "w") as f:
    json.dump(sections_json, f, indent=2)

with open("app/src/main/assets/curriculum/intermediate/topics.json", "w") as f:
    json.dump(topics_json, f, indent=2)

with open("app/src/main/assets/curriculum/intermediate/lessons.json", "w") as f:
    json.dump(lessons_json, f, indent=2)

with open("app/src/main/assets/curriculum/intermediate/activities.json", "w") as f:
    json.dump(activities_json, f, indent=2)

with open("app/src/main/assets/curriculum/mappings/english_grammar_in_use_5_intermediate.json", "w") as f:
    json.dump(mapping_obj, f, indent=2)

print("Generated intermediate curriculum and mapping files successfully!")
