# MVC Architecture — What It Is, Its Flaws, and a Better Pattern

---

## Table of Contents

1. [What is MVC?](#what-is-mvc)
2. [How MVC Works — The Restaurant Analogy](#the-restaurant-analogy)
3. [MVC in Code Terms](#mvc-in-code-terms)
4. [Libraries vs Frameworks](#libraries-vs-frameworks)
5. [Problems with MVC](#problems-with-mvc)
6. [A Better, Customized Pattern](#a-better-customized-pattern)
7. [Layer-by-Layer Deep Dive](#layer-by-layer-deep-dive)
8. [DTOs — Why They Matter](#dtos--why-they-matter)
9. [Data Flow — Full Example](#data-flow--full-example)
10. [Summary & When to Use What](#summary--when-to-use-what)

---

## What is MVC?

**MVC (Model-View-Controller)** is an architectural pattern for **distributing code based on responsibility**. It is one of the earliest and most widely adopted patterns in software engineering, and it is a direct application of the **Single Responsibility Principle (SRP)** — the idea that every piece of code should have exactly one reason to change.

The key insight of MVC is that most applications do three fundamentally different things:
- **Store and manipulate data** (business logic)
- **Present data** to the user (user interface)
- **Coordinate** between the two (control flow)

Rather than mixing these three concerns into one blob of code, MVC says: separate them explicitly.

### Why it was created

Before MVC, it was common to write applications where one file or function was doing everything — reading user input, running business rules, querying the database, and rendering output. This made code:
- Impossible to test in isolation
- Hard to change without breaking something else
- Unmaintainable as the codebase grew

MVC solved this by introducing clear boundaries.

### Key Properties

- **Independent of tech stack** — you can apply MVC in Java, Python, JavaScript, Go, or any other language
- **Independent of framework** — it is a pattern, not a library. Frameworks like Spring, Rails, and Express may implement it for you, but the pattern itself belongs to no framework
- **Scalable** — adding a new feature touches a specific layer rather than the whole codebase
- **Extensible** — you can swap implementations (e.g. change the database) without touching business logic
- **Maintainable** — bugs are isolated to a layer, making them faster to find and fix

---

## The Restaurant Analogy

The best way to understand MVC is through a restaurant:

```
┌─────────────────────────────────────────────────┐
│                    RESTAURANT                   │
│                                                 │
│   ┌──────────┐    ┌──────────┐   ┌──────────┐  │
│   │ Customer │───▶│  Waiter  │──▶│   Chef   │  │
│   └──────────┘    └──────────┘   └──────────┘  │
│                        │               │        │
│                   Routes request   Cooks food   │
│                   (Controller)     (Model)      │
│                                                 │
│   ┌──────────────────────────────────────────┐  │
│   │               MENU (View)                │  │
│   │   What the customer sees and interacts   │  │
│   │   with. Presents options, shows results. │  │
│   └──────────────────────────────────────────┘  │
└─────────────────────────────────────────────────┘
```

| Restaurant Role | MVC Layer        | What It Does                                                    |
|-----------------|------------------|-----------------------------------------------------------------|
| **Customer**    | User / Client    | Makes a request (clicks a button, submits a form)               |
| **Waiter**      | Controller (C)   | Receives the request, figures out who handles it, returns result |
| **Chef**        | Model (M)        | Does the actual work — business logic, calculations, DB queries  |
| **Menu**        | View (V)         | What the user sees — the rendered HTML, JSON response, or UI     |

The waiter never cooks. The chef never talks to the customer directly. The menu doesn't know how the food is made. Each has **one job** and they communicate through defined interfaces.

---

## MVC in Code Terms

M, V, and C are not single files. They are **categories of code** — each category is a set of:
- Functions
- Classes
- Structures / Interfaces

organized around a single responsibility.

### Model (M)
The Model is where your **business logic lives**. It is the Chef of your application. Examples:
- Calculating an order total
- Checking if a user has permission to do something
- Fetching data from a database and transforming it

```
// Example Model: UserModel
class UserModel {
    findById(id)         // fetch user from DB
    hashPassword(plain)  // business logic
    isAdmin(user)        // business rule
}
```

### View (V)
The View is what the **user sees**. It is entirely concerned with presentation — no business logic belongs here. In traditional MVC (server-rendered), this is an HTML template. In modern apps, this is typically a React/Vue/Angular frontend running separately.

```
// Example View: a template or a JSON response shape
{
    "user": {
        "id": 1,
        "name": "Pavan",
        "email": "pavan@example.com"
    }
}
```

### Controller (C)
The Controller is the **Waiter**. It:
1. Receives the incoming request
2. Calls the appropriate Model methods
3. Passes the result to the View
4. Returns the response

```
// Example Controller: UserController
class UserController {
    getUser(request, response) {
        const user = UserModel.findById(request.params.id)  // calls model
        response.send(UserView.render(user))                 // calls view
    }
}
```

The Controller knows almost nothing about business logic. It just coordinates.

---

## Libraries vs Frameworks

Understanding this distinction matters because MVC is often confused with the framework that implements it.

| Concept       | Description                                                                                         | Examples                              |
|---------------|-----------------------------------------------------------------------------------------------------|---------------------------------------|
| **Library**   | A focused piece of code that solves **one specific problem**. You call it when you need it.         | React JS, Hibernate, Sequelize, Lodash |
| **Framework** | A **collection of libraries** working together to solve a bigger problem. It calls your code.       | Spring Boot, Ruby on Rails, Django, Express |

> **Inversion of Control**: The key difference between a library and a framework is who is in charge. When you use a library, *you* call it. When you use a framework, *it* calls you. This is sometimes called the "Hollywood Principle" — "Don't call us, we'll call you."

**The trade-off:**
- Frameworks give you **convention over configuration** — less boilerplate, faster to get started, but you work within the framework's rules.
- Libraries give you **full control** — you compose them exactly how you want, but you are responsible for all the wiring.

MVC implementations in frameworks (e.g. Rails MVC, Django MVT) work great for standard use cases. When your app grows beyond those standard cases, the framework's MVC becomes a constraint rather than a help.

---

## Problems with MVC

MVC was designed in the 1970s for desktop GUI applications. When applied to modern web applications, it shows serious cracks.

### Problem 1: Too Much Simplification

MVC gives you three buckets. Real applications have dozens of distinct concerns that do not fit neatly into Model, View, or Controller:

- Where does **input validation** go? (Controller? Model? Both?)
- Where does **authentication and authorization** live?
- Where does **email sending** happen?
- Where do **background jobs** go?
- Where does **caching** logic sit?
- Where do **third-party API calls** belong?

In practice, teams answer these questions inconsistently. One developer puts validation in the Controller, another puts it in the Model. The boundaries blur, the codebase becomes inconsistent, and the original promise of clear separation is broken.

### Problem 2: Controllers and Models Become God Objects

As the application grows, Controllers and Models accumulate responsibility because there is nowhere else to put things. A Controller that starts as 50 lines becomes 500 lines — handling validation, authorization, business logic, email triggering, logging, and response formatting all in one place.

This is sometimes called a **"Fat Controller"** or **"God Model"** anti-pattern. It directly violates the SRP that MVC was supposed to enforce.

```
// What a "Fat Controller" looks like in practice
class UserController {
    register(req, res) {
        // 1. validate input (should this be here?)
        if (!req.body.email.includes('@')) { ... }

        // 2. check if user already exists (business logic leaking in)
        const existing = db.query("SELECT * FROM users WHERE email = ?", ...)

        // 3. hash password (definitely not controller logic)
        const hashed = bcrypt.hash(req.body.password, 10)

        // 4. insert into DB (repository logic leaking in)
        db.query("INSERT INTO users ...", ...)

        // 5. send welcome email (side effect, belongs elsewhere)
        emailService.sendWelcome(req.body.email)

        // 6. return response
        res.json({ success: true })
    }
}
```

This controller is doing six different things. It is untestable, fragile, and impossible to change safely.

### Problem 3: Frontend/Backend Separation is Ignored

When MVC was invented, the server rendered and returned HTML. The View layer was a server-side template. The entire app — View, Controller, and Model — lived on the server.

Modern applications are built differently:

```
┌─────────────────────────┐          ┌─────────────────────────┐
│        FRONTEND         │          │         BACKEND         │
│                         │◀────────▶│                         │
│  React / Vue / Angular  │  HTTP /  │  Node / Go / Java       │
│  (its own codebase,     │  REST /  │  (its own codebase,     │
│   its own deployments,  │  GraphQL │   its own deployments,  │
│   its own team)         │          │   its own team)         │
└─────────────────────────┘          └─────────────────────────┘
```

The frontend is a completely separate application with its own build system, its own repository (sometimes), its own team, and its own deployment pipeline. MVC has no concept of this split. The "View" in MVC is a server-side concern, but in modern apps the View is a JavaScript application running in the browser.

This means applying MVC to a modern API server is already a category error — the server has no View. It only returns data. The three-layer model breaks down immediately.

---

## A Better, Customized Pattern

Instead of forcing three buckets, this pattern gives **each distinct concern its own explicit layer**. It is what most production-grade APIs and backend services actually look like in practice.

### The Client-Server Model

```
┌─────────────────────┐                    ┌─────────────────────┐
│                     │   HTTP Request      │                     │
│       CLIENT        │ ──────────────────▶ │       SERVER        │
│                     │                    │                     │
│  Browser / Mobile   │ ◀────────────────── │  API / Backend      │
│  App / CLI          │   HTTP Response     │                     │
└─────────────────────┘                    └─────────────────────┘
```

The server's job, at its most fundamental, is:
1. **Collect** the request
2. **Process** the request
3. **Return** a response

But "process the request" covers an enormous amount of work. The layered pattern breaks that work down explicitly.

### Full Server Architecture

```
                          HTTP Request
                               │
                               ▼
                  ┌────────────────────────┐
                  │      Routing Layer     │
                  │                        │
                  │  Matches URL + Method  │
                  │  to the right handler  │
                  └────────────┬───────────┘
                               │
                               ▼
                  ┌────────────────────────┐
                  │       Validation       │
                  │                        │
                  │  Is the request valid? │
                  │  Right shape? Required │
                  │  fields present?       │
                  └────────────┬───────────┘
                               │
                               ▼
                  ┌────────────────────────┐
                  │   Handler/Controller   │
                  │                        │
                  │  Thin coordinator.     │
                  │  Calls service layer.  │
                  │  Formats response.     │
                  └────────────┬───────────┘
                               │
                               ▼
                  ┌────────────────────────┐
                  │     Service Layer      │
                  │                        │
                  │  All business logic.   │
                  │  Algorithms, rules,    │
                  │  orchestration.        │
                  │  NO direct DB access.  │
                  └────────────┬───────────┘
                               │
                               ▼
                  ┌────────────────────────┐
                  │  Repository / DAO      │
                  │                        │
                  │  One job: talk to DB.  │
                  │  All queries live here │
                  └─────┬──────────────────┘
                        │
              ┌─────────┴──────────┐
              ▼                    ▼
         ┌─────────┐          ┌──────────┐
         │  MySQL  │          │ MongoDB  │
         └─────────┘          └──────────┘


  ┌──────────────────────────────────────────────────────┐
  │                  SUPPORTING LAYERS                   │
  │                                                      │
  │   Config   │  Schema/Models  │  Migrations  │  Utils │  DTO  │
  └──────────────────────────────────────────────────────┘
```

---

## Layer-by-Layer Deep Dive

### 1. Routing Layer

**What it does:** The routing layer is the entry point for every request. It examines the incoming HTTP method (GET, POST, PUT, DELETE) and the URL path, then maps that combination to the correct handler function or class.

**What it does NOT do:** It does not validate, does not run business logic, does not touch the database.

**Why it matters:** Without a routing layer, you end up with one giant switch statement or if-else chain scattered across your codebase. A dedicated routing layer makes it easy to see every endpoint your application exposes in one place.

```javascript
// Clean routing layer — just mapping URLs to handlers
router.get('/users/:id',    UserHandler.getById)
router.post('/users',       UserHandler.create)
router.put('/users/:id',    UserHandler.update)
router.delete('/users/:id', UserHandler.delete)
router.post('/tweets',      TweetHandler.create)
```

---

### 2. Validation Layer

**What it does:** Before any business logic runs, validation checks that the incoming request is well-formed. This includes:
- Are all required fields present?
- Are field types correct (string, number, date)?
- Are values within acceptable ranges?
- Does the email look like a real email?

**What it does NOT do:** It does not enforce business rules (e.g., "a user can only have 5 tweets"). Business rules belong in the Service layer. Validation only checks the *shape and format* of the data.

**Why separating validation matters:** If validation lives in the Controller, the Controller becomes cluttered. If it lives in the Service, the Service mixes structural concerns with business concerns. A dedicated validation layer keeps both clean.

```javascript
// Validation schema for creating a tweet
const createTweetSchema = {
    text: {
        type: 'string',
        required: true,
        maxLength: 280,
        minLength: 1
    }
}
// If validation fails → return 400 Bad Request immediately
// Never reaches the handler
```

---

### 3. Handler / Controller Layer

**What it does:** The Handler is the thin coordinator. Once a request has been validated, the Handler:
1. Extracts the relevant data from the request
2. Calls the appropriate Service method
3. Receives the result from the Service
4. Formats it into the correct HTTP response

**What it does NOT do:** It contains no business logic, no database queries, no validation. If your Handler is longer than ~30 lines, something has leaked in from another layer.

```javascript
// A clean handler — thin coordinator
class TweetHandler {
    async create(req, res) {
        const { text } = req.body          // extract from request
        const userId = req.user.id         // from auth middleware

        const tweet = await TweetService.create({ text, userId })  // delegate to service

        res.status(201).json(tweet)        // format response
    }
}
```

---

### 4. Service Layer

**What it does:** The Service layer is the heart of the application. All **business logic** lives here. This includes:
- Core algorithms
- Business rules (e.g. "a suspended user cannot post tweets")
- Orchestration across multiple repositories (e.g. creating a tweet also updates the user's tweet count)
- Calling external services (email, SMS, payment gateways)
- Computing derived values

**Critical rule:** The Service layer **never directly queries the database**. It calls Repository methods. This is important because it makes the Service layer testable in isolation — you can swap out the real repository for a mock without touching business logic.

```javascript
class TweetService {
    async create({ text, userId }) {
        // Business rule: check if user is suspended
        const user = await UserRepository.findById(userId)
        if (user.isSuspended) {
            throw new Error('Suspended users cannot post tweets')
        }

        // Business rule: generate server-side fields
        const tweet = {
            id: generateUUID(),
            text,
            userId,
            createdAt: new Date(),
            updatedAt: new Date()
        }

        // Delegate DB operation to repository
        return await TweetRepository.save(tweet)
    }
}
```

---

### 5. Repository / DAO Layer

**What it does:** The Repository (also called DAO — Data Access Object) has **one and only one responsibility**: interact with the database. It contains:
- All SQL queries or NoSQL operations
- All database-specific logic (transactions, joins, indexes)
- Mapping between raw database rows and your application objects

**What it does NOT do:** No business logic whatsoever. A Repository does not know *why* it is being called — only *how* to execute the operation against the database.

**Why this matters:** When you need to switch databases (e.g. from MySQL to PostgreSQL, or from SQL to MongoDB), you only change the Repository. The Service layer, Handler, and everything above it stays untouched.

```javascript
class TweetRepository {
    // MySQL implementation
    async save(tweet) {
        await db.query(
            'INSERT INTO tweets (id, text, userId, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)',
            [tweet.id, tweet.text, tweet.userId, tweet.createdAt, tweet.updatedAt]
        )
        return tweet
    }

    async findById(id) {
        const rows = await db.query('SELECT * FROM tweets WHERE id = ?', [id])
        return rows[0]
    }
}

// MongoDB implementation — same interface, different internals
class TweetRepository {
    async save(tweet) {
        await db.collection('tweets').insertOne(tweet)
        return tweet
    }

    async findById(id) {
        return await db.collection('tweets').findOne({ id })
    }
}
```

---

### 6. Schema / Models

**What it does:** The Schema (or Model definition) describes the **structure of your data as it lives in the database**. This includes:
- Field names and data types
- Constraints (NOT NULL, UNIQUE, foreign keys)
- Default values
- Relationships between tables/collections

This is distinct from your application objects and DTOs. The Schema represents what the database knows, not what the client sends or receives.

```sql
-- MySQL Schema for tweets
CREATE TABLE tweets (
    id          VARCHAR(36)  PRIMARY KEY,
    text        TEXT         NOT NULL,
    userId      VARCHAR(36)  NOT NULL REFERENCES users(id),
    createdAt   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

```javascript
// Or in an ORM (like Sequelize / Mongoose)
const TweetSchema = {
    id:        { type: DataTypes.UUID, primaryKey: true },
    text:      { type: DataTypes.TEXT, allowNull: false },
    userId:    { type: DataTypes.UUID, references: { model: 'users' } },
    createdAt: { type: DataTypes.DATE },
    updatedAt: { type: DataTypes.DATE }
}
```

---

### 7. Migrations

**What it does:** Migrations are versioned scripts that record every change made to the database schema over time. Think of them as **git commits for your database**.

Each migration is a file that contains:
- An **up** function: applies the change (add a column, create a table)
- A **down** function: reverts the change

```javascript
// Migration: 2024_01_15_add_likes_count_to_tweets
exports.up = async (db) => {
    await db.query('ALTER TABLE tweets ADD COLUMN likesCount INT NOT NULL DEFAULT 0')
}

exports.down = async (db) => {
    await db.query('ALTER TABLE tweets DROP COLUMN likesCount')
}
```

**Why migrations matter:**
- Every developer on the team runs the same migrations → identical database state
- Deployments are reproducible — you know exactly what state the database is in
- You can roll back a bad schema change in production
- Particularly important for **SQL databases** which have rigid schemas that require explicit changes. MongoDB (schema-less) still benefits from migrations for data consistency.

---

### 8. Utils

**What it does:** The Utils folder contains **pure helper functions** that are shared across multiple layers. They have no knowledge of the application's domain — they just perform generic operations.

Examples:
- Generating a UUID
- Formatting a date
- Capitalizing a string
- Parsing environment variables
- Calculating pagination offsets

```javascript
// utils/uuid.js
export const generateUUID = () => crypto.randomUUID()

// utils/date.js
export const formatDate = (date) => date.toISOString()

// utils/pagination.js
export const getOffset = (page, limit) => (page - 1) * limit
```

**The rule for Utils:** If a function is specific to one domain (e.g. "format a tweet for display"), it does not belong in Utils. Utils contains only generic, reusable operations that could theoretically be copied to any project.

---

### 9. Config

**What it does:** The Config layer centralizes all **application configuration** — values that change between environments (development, staging, production). This includes:
- Database connection strings
- API keys and secrets
- Port numbers
- Feature flags
- External service URLs

All configuration should be read from **environment variables** and accessed through the Config layer, not scattered across the codebase.

```javascript
// config/index.js
export const config = {
    db: {
        host:     process.env.DB_HOST     || 'localhost',
        port:     process.env.DB_PORT     || 5432,
        name:     process.env.DB_NAME     || 'myapp',
        user:     process.env.DB_USER,
        password: process.env.DB_PASSWORD,
    },
    server: {
        port: process.env.PORT || 3000,
    },
    jwt: {
        secret:    process.env.JWT_SECRET,
        expiresIn: process.env.JWT_EXPIRES_IN || '7d',
    }
}
```

---

## DTOs — Why They Matter

**DTO** stands for **Data Transfer Object**. A DTO is an object that defines the shape of data as it moves across a boundary — specifically, across the network between client and server.

### The Three Shapes of Data

Consider a simple tweet. There are three distinct shapes the data takes:

```
1. What the CLIENT SENDS (Request DTO)
   ────────────────────────────────────
   {
       "text": "Hello world"
   }
   Only one field. The client doesn't know about IDs, timestamps, or userId.


2. What the SERVER STORES in the DB (Schema / Model)
   ────────────────────────────────────────────────────
   {
       id:        "550e8400-e29b-41d4-a716-446655440000",
       text:      "Hello world",
       userId:    "123e4567-e89b-12d3-a456-426614174000",
       createdAt: "2024-01-15T10:30:00Z",
       updatedAt: "2024-01-15T10:30:00Z"
   }
   The server generates id, userId, createdAt, updatedAt — none of which came from the client.


3. What the SERVER RETURNS (Response DTO)
   ──────────────────────────────────────
   {
       "id":        "550e8400-e29b-41d4-a716-446655440000",
       "text":      "Hello world",
       "author":    { "id": "...", "username": "pavan" },
       "createdAt": "2024-01-15T10:30:00Z",
       "likedByMe": false,
       "likesCount": 0
   }
   The response may include joined data (author details), computed fields (likedByMe),
   and might exclude internal fields (updatedAt might not be relevant to the client).
```

These are **three different shapes**. Without DTOs, you end up sending your raw database schema to the client (a security risk — internal fields get exposed) or accepting data from the client that includes fields it should never set (like `isAdmin: true`).

### Why this matters

| Risk without DTOs | How DTOs prevent it |
|-------------------|---------------------|
| Client sets `isAdmin: true` in the request body and it gets saved | Request DTO only allows `text` — extra fields are stripped |
| Internal DB fields (`passwordHash`, `deletedAt`) leak to the client | Response DTO explicitly lists only what the client should see |
| Client receives a field that doesn't exist in next version, breaking their app | Response DTO is a contract you control independently of the schema |
| You change your DB schema and it silently changes your API | DTO decouples API shape from DB shape |

---

## Data Flow — Full Example

Let's trace exactly what happens when a user posts a tweet from start to finish.

```
User types "Hello world" and clicks "Tweet"
               │
               ▼
         CLIENT sends:
         POST /tweets
         Body: { "text": "Hello world" }
         Headers: { Authorization: "Bearer <jwt>" }
               │
               ▼
     ┌─────────────────────┐
     │    ROUTING LAYER    │
     │                     │
     │  POST /tweets  ───▶ TweetHandler.create
     └─────────────────────┘
               │
               ▼
     ┌─────────────────────┐
     │     VALIDATION      │
     │                     │
     │  Is "text" present? ✓
     │  Is length ≤ 280?   ✓
     │  Is it a string?    ✓
     └─────────────────────┘
               │
               ▼
     ┌─────────────────────┐
     │  HANDLER/CONTROLLER │
     │                     │
     │  Extract: text = "Hello world"
     │  Extract: userId from JWT token
     │  Call: TweetService.create(text, userId)
     └─────────────────────┘
               │
               ▼
     ┌─────────────────────┐
     │    SERVICE LAYER    │
     │                     │
     │  Check: is user suspended? → No ✓
     │  Generate: id, createdAt, updatedAt
     │  Call: TweetRepository.save(tweetData)
     └─────────────────────┘
               │
               ▼
     ┌─────────────────────┐
     │     REPOSITORY      │
     │                     │
     │  INSERT INTO tweets (id, text, userId, createdAt, updatedAt)
     │  VALUES ('uuid', 'Hello world', 'userId', now(), now())
     └─────────────────────┘
               │
               ▼
         DATABASE stores the row
               │
               ▼  (result bubbles back up)
     ┌─────────────────────┐
     │  HANDLER/CONTROLLER │
     │                     │
     │  Format response DTO:
     │  { id, text, author, createdAt, likesCount: 0 }
     └─────────────────────┘
               │
               ▼
         CLIENT receives:
         HTTP 201 Created
         Body: {
           "id": "550e8400...",
           "text": "Hello world",
           "author": { "username": "pavan" },
           "createdAt": "2024-01-15T10:30:00Z",
           "likesCount": 0
         }
```

At no point does any layer reach into another layer's territory. The routing layer never queries the DB. The service layer never formats an HTTP response. The repository never enforces business rules.

---

## Summary & When to Use What

### Quick Reference: Which layer does what?

| Layer              | Knows about HTTP? | Knows about DB? | Contains Business Logic? |
|--------------------|:-----------------:|:---------------:|:------------------------:|
| Routing            | ✓                 | ✗               | ✗                        |
| Validation         | Partially         | ✗               | ✗ (only shape checks)   |
| Handler/Controller | ✓                 | ✗               | ✗                        |
| Service            | ✗                 | ✗               | ✓                        |
| Repository         | ✗                 | ✓               | ✗                        |
| Schema/Models      | ✗                 | ✓               | ✗                        |
| DTO                | ✓ (network shape) | ✗               | ✗                        |
| Config             | Partially         | Partially       | ✗                        |
| Utils              | ✗                 | ✗               | ✗                        |
| Migrations         | ✗                 | ✓               | ✗                        |

### MVC vs Layered Pattern

| Dimension              | MVC                                          | Layered Pattern                                       |
|------------------------|----------------------------------------------|-------------------------------------------------------|
| **Number of layers**   | 3 (Model, View, Controller)                  | 5–9 explicit layers depending on complexity           |
| **Frontend handling**  | View is a server-side template               | Frontend is a completely separate application         |
| **Business logic**     | In the Model (often leaks to Controller)     | Exclusively in the Service layer                      |
| **DB access**          | In the Model (sometimes Controller)          | Exclusively in the Repository layer                   |
| **Validation**         | Undefined — usually pollutes Controller      | Dedicated Validation layer                            |
| **Testability**        | Controllers and Models are tightly coupled   | Each layer can be tested independently                |
| **When to use**        | Small apps, prototypes, server-rendered pages | Any modern API, microservice, or production backend   |
| **Breaks down when**   | App scales, frontend is separate, team grows | Overkill for tiny scripts or one-off utilities        |

### The Core Philosophy

The layered pattern is not a rejection of MVC — it is **MVC taken seriously**. MVC said "separate your concerns." The layered pattern asks: *what are the actual concerns?* And then it gives each one its own home.

When every layer has exactly one reason to change, you get:
- **Testability** — test business logic without a database; test the DB layer without HTTP
- **Replaceability** — swap MySQL for PostgreSQL by only touching the Repository
- **Clarity** — any developer can look at the folder structure and know exactly where to find any piece of logic
- **Safety** — a bug in the routing layer cannot cause a bad write to the database because the layers between them act as gates
