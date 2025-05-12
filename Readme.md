# SOS Backend

## Overview
The **SOS Backend** is the server-side implementation of the [SOS application](https://github.com/dev-xero/sos-client).
 

## Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/Anuolu-2020/sos-backend.git

2. Navigate into the project directory: 
   ```sh
    cd sosbackend 
   ```
3. Start the development server:
   ```sh
    ./gradlew bootRun
   ```

## Test Home Endpoint
Visit http://localhost:9090/api

### Using curl: 
```sh
curl -X GET http://localhost:9090/api 
```
API Endpoint
-------------
- `GET /api/fire-stations` - Fetch nearby fire stations within a specified coordinate.
  - Parameters: `longitude`, `latitude`, `radius`, `page`, `limit`.
  - Example: `curl -X GET "http://localhost:9090/api/fire-stations?longitude=3.3430&latitude=6.5530&radius=5000&page=1&limit=10"
  
## Contributors

- [Anuoluwapo Emmanuel](https://github.com/Anuolu-2020)
- [Sherif Sani](https://github.com/sherifsani)
- [Emmanuel Felix](https://github.com/hemahnuhel)

