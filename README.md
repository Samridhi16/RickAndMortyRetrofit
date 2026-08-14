# RickAndMortyRetrofit
https://github.com/user-attachments/assets/3acac4ce-3733-4273-86de-d7dae179e5fc



# RickAndMortyRetrofit

An Android app that fetches a page of characters from the [Rick and Morty API](https://rickandmortyapi.com/) and displays them in a scrollable list. It uses the MVVM pattern, Retrofit, coroutines, LiveData, and RecyclerView.

## Architecture at a glance

```mermaid
flowchart LR
    UI["MainActivity\nView + lifecycle owner"]
    VM["RickAndMortyViewModel\nUI state + coroutine scope"]
    REPO["RickAndMortyRepository\nData access boundary"]
    RETROFIT["RetrofitHelper\nRetrofit + OkHttp logging"]
    SERVICE["RickAndMortyService\nGET character/?page={page}"]
    API[("Rick and Morty API")]
    MODELS["CharactersResponse\nInfo + List<Result>"]
    ADAPTER["CharacterAdapter\nAsyncListDiffer + Picasso"]
    LIST["RecyclerView\nCharacter rows"]
    ERROR["Toast\nError message"]

    UI -->|"calls getCharacters(23)"| VM
    VM -->|"suspend getCharacters(page)"| REPO
    REPO --> RETROFIT
    RETROFIT --> SERVICE
    SERVICE -->|"HTTPS request"| API
    API -->|"JSON response"| MODELS
    MODELS -->|"Response<CharactersResponse>"| VM
    VM -->|"characters: LiveData<List<Result>>"| UI
    UI -->|"updateList(characters)"| ADAPTER
    ADAPTER --> LIST
    VM -->|"error: LiveData<String>"| UI
    UI --> ERROR
    ADAPTER -->|"loads image URLs"| LIST
```

### Layer responsibilities

| Layer | Current class(es) | Responsibility |
| --- | --- | --- |
| View | `MainActivity`, `activity_main.xml` | Creates the screen, observes `LiveData`, and configures the list. |
| Presentation | `RickAndMortyViewModel` | Starts the network coroutine, exposes characters and error states. |
| Data | `RickAndMortyRepository` | Keeps the ViewModel independent from the Retrofit service. |
| Network | `RetrofitHelper`, `RickAndMortyService` | Configures HTTP/Gson and declares the API endpoint. |
| Model | `CharactersResponse`, `Result`, `Info`, `Location`, `Origin` | Maps API JSON into Kotlin data classes. |
| List rendering | `CharacterAdapter`, `activity_item.xml` | Efficiently updates rows with `AsyncListDiffer` and loads character images with Picasso. |

## Character-loading flow

```mermaid
sequenceDiagram
    autonumber
    participant A as MainActivity
    participant V as RickAndMortyViewModel
    participant R as RickAndMortyRepository
    participant S as RickAndMortyService / Retrofit
    participant API as Rick and Morty API
    participant C as CharacterAdapter

    A->>A: Create binding, RecyclerView, and adapter
    A->>V: Observe characters and error LiveData
    A->>V: getCharacters(23)
    V->>R: getCharacters(page) in viewModelScope
    R->>S: getCharacters(page)
    S->>API: GET /api/character/?page=23
    API-->>S: JSON characters response
    S-->>R: Response<CharactersResponse>
    R-->>V: Response<CharactersResponse>

    alt HTTP response is successful
        V->>V: Extract body.results
        V-->>A: Publish characters LiveData
        A->>C: updateList(characters)
        C->>C: Diff items and bind changed rows
        C-->>A: RecyclerView shows name, status, type, location, image
    else HTTP response is unsuccessful
        V-->>A: Publish error LiveData
        A->>A: Show Toast
    end
```

## Project map

```text
app/src/main/java/com/projects/rickandmorty/
├── view/
│   └── MainActivity.kt          # Screen setup and LiveData observers
├── viewmodel/
│   └── RickAndMortyViewModel.kt # Presentation state and coroutine request
├── repository/
│   └── RickAndMortyRepository.kt# Data-access boundary
├── remote/
│   ├── RetrofitHelper.kt        # Retrofit, Gson, OkHttp configuration
│   └── RickAndMortyService.kt   # API contract
├── model/                       # API response data classes
└── util/
    └── CharacterAdapter.kt      # RecyclerView binding and image loading
```

## Request reference

The app currently loads the hard-coded page `23` when `MainActivity` is created:

```kotlin
viewModel.getCharacters(23)
```

This results in:

```text
GET https://rickandmortyapi.com/api/character/?page=23
```

To load another page, pass a different page number to `getCharacters(page)`. A later pagination feature can keep the same flow and update only the page source in the ViewModel.
