# Assignment 4 - Photo Gallery with AJAX

This assignment is a continuation of Assignment 3, implementing a photo gallery that fetches images dynamically using AJAX requests.

## Features

- Fetches image URLs from remote server using AJAX
- Displays images in a responsive grid layout
- Modal presentation with navigation
- Slideshow functionality
- Responsive design with different grid sizes

## Requirements Fulfilled

✅ **Task 1**: AJAX GET request to fetch image URLs from `https://raw.githubusercontent.com/ubc-vsp25/ubc-vsp25.github.io/main/images`
✅ **Task 2**: Display images using the fetched URLs in the grid
✅ **No hardcoded image lists**: All images are fetched dynamically
✅ **No local image files**: Uses remote images from the server

## How to Run

1. Install dependencies:
   ```bash
   npm install
   ```

2. Start the server:
   ```bash
   node index.js
   ```

3. Open your browser and navigate to `http://localhost:3000`

## Implementation Details

- Uses Express.js server to serve static files
- Implements AJAX requests using the Fetch API
- Maintains the existing grid display functionality
- Includes proper error handling for network requests 