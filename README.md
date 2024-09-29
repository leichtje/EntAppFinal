# Enterprise Application Final Project

Card Collection Application

## Introduction

The Card Collection Application will allow users to maintain an online database of their cards that will be searchable. It will allow the user to add cards not currently found to the collection to ensure all
cards in a collection can be maintained.The database will also support a funtionality of allowing the user to see which cards are among the most valuable and popular in the collection. The application will allow
users to maintain the database easily and ensure and carefree way to check a personal collection.

## Storyboard

[Storyboard In AdobeXD](https://xd.adobe.com/view/08642fe3-1586-4fa7-8a28-580b5d17e32f-5d37/)

## Functional Requirements

1. As a trading card collector, I want one central online catalog where I can query the current popularity and market price of any card, so that I can figure out how much I should sell my Pokemon cards for.

### Example

**Given**: The trading card catalog is available

**When**: The user/service searches for a trading card with an ID or name that exists in the database

**Then**: The trading card catalog API responds to the user/service with JSON data specifying the card name, ID, popularity, and market price

### Example

**Given**: The trading card catalog is available

**When**: The user/service searches for a trading card with an ID or name that _does not_ exist in the database

**Then**: The trading card catalog API responds to the user/service with a 400 Bad Request error

### Example

**Given**: The trading card catalog is available

**When**: The user/service searches for a trading card with invalid parameters/request body (such as not specifying an ID or name correctly)

**Then**: The trading card catalog API responds to the user/service with a 400 Bad Request error

2. As a Yu-Gi-Oh card expert and administrator of this online trading card catalog, I want to be able to upload information on a card's popularity and market price, so that user who wants to view that information can do so.

### Example

**Given**: The user is logged in (only admins have accounts, as the card information retrieval service is available to everyone)

**When**: The user uploads information about a trading card using valid fields (id, name, popularity, market price)

**Then**: The API saves the trading card to the database with the given information

### Example

**Given**: The user is logged in

**When**: The user uploads information about a trading card using _invalid_ fields

**Then**: The API responds with a 400 Bad Request error, and makes no changes to the database

## Class Diagram

![Card Collection Class Diagram](https://github.com/leichtje/EntAppFinal/blob/main/card_app_class_diagram.drawio.png)

### Class Diagram Description
**DTO**

Card: Used as a representation of a card and the data used for them in our application.

**DAO**

ICardDAO: Will be used as way to access database containing card info.

**Service**

ICardService: Used as contract to define what methods will be needed when developing the CardService.

CardService: Will be used as the actual implementation of business logic realted to cards.

CardServiceStub: Will be used to unit test card service.

**Controller**

CardController: Used to handle input that tries to access card data.

## JSON Schema
```
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer"
    },
    "cardName": {
      "type": "string"
    },
    "series": {
      "type": "string"
    },
    "favoritesNum": {
      "type": "integer"
    },
    "marketAvg": {
      "type": "string"
    },
    "rarity": {
      "type": "string"
    }
  }
}
```

## Github Project Link

[See our Github for the Project here](https://github.com/leichtje/EntAppFinal)

## Scrum Roles

Isabelle Kramer - UI Designer

Danny Murray - Business Logic / Persistence

Austin Schnee - Business Logic Development

Paul Sparks - Business Logic / Persistence

Brandon Robinson - UI Specialist

Jonathon Leicht - Scrum Maser / QA

## Milestones

[Milestone 1 - Create Project Scaffold For Card Collection Application](https://github.com/leichtje/EntAppFinal/milestone/1)

[Milestone 2 - Create the Business Logic of the Card Collection Program](https://github.com/leichtje/EntAppFinal/milestone/2)

[Milestone 3 - Finalize the Finished Card Collection Application](https://github.com/leichtje/EntAppFinal/milestone/3)

### Projects Dashboard

[See Projects related to this Application here](https://github.com/leichtje/EntAppFinal/projects?query=is%3Aopen)

## Standup

[We meet bi-weekly on Mondays at 8pm on Teams](https://teams.microsoft.com/l/meetup-join/19%3ameeting_YTU0YzA0MmUtMTgzNy00NDBmLThjZDMtYmRkYmE0NTVkNjUy%40thread.v2/0?context=%7b%22Tid%22%3a%22f5222e6c-5fc6-48eb-8f03-73db18203b63%22%2c%22Oid%22%3a%224de4eda0-156f-4b00-90dc-95a56e4674a2%22%7d)
